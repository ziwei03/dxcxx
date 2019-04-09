package com.vincent.framework.pool;

import com.vincent.framework.processor.CheckJobProcessor;
import com.vincent.framework.processor.ITaskProcessor;
import com.vincent.framework.vo.JobInfo;
import com.vincent.framework.vo.TaskResult;
import com.vincent.framework.vo.TaskResultType;

import java.util.List;
import java.util.concurrent.*;

public class PendingJobPool {
    private static final int THREAD_HOLD = Runtime.getRuntime().availableProcessors();
    private static ConcurrentHashMap<String, JobInfo> jobMap = new ConcurrentHashMap<>();
    private static BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<>(5000);
    private static ExecutorService taskExecutor = new ThreadPoolExecutor(THREAD_HOLD, THREAD_HOLD, 60, TimeUnit.SECONDS, taskQueue);
    private static CheckJobProcessor checkJobProcessor = CheckJobProcessor.getInstance();

    private PendingJobPool() {}

    private static class InstanceHolder {
        private static PendingJobPool pendingJobPool = new PendingJobPool();
    }

    public static PendingJobPool getInstance() {
        return InstanceHolder.pendingJobPool;
    }

    public void registerJob(String jobName, int taskLength, ITaskProcessor<?, ?> taskProcessor, long expireTime) {
        JobInfo jobInfo = new JobInfo(jobName, taskLength, taskProcessor, expireTime);
        if(jobMap.putIfAbsent(jobName, jobInfo) != null) {
            throw new RuntimeException("工作【"+ jobName +"】已经被注册");
        }
    }

    private JobInfo getJob(String jobName) {
        JobInfo jobInfo = jobMap.get(jobName);
        if (jobInfo == null) {
            throw new RuntimeException("工作【"+ jobName +"】没有被注册");
        }
        return jobInfo;
    }

    public <T, U> void putTask(String jobName, U parameter) {
        JobInfo job = getJob(jobName);
        PendingTask<T, U> pendingTask = new PendingTask<>(job, parameter);
        taskExecutor.execute(pendingTask);
    }

    public static ConcurrentHashMap<String, JobInfo> getMap() {
        return jobMap;
    }

    public String getProgress(String jobName) {
        JobInfo job = getJob(jobName);
        return job.getTotalProgress();
    }

    public <T> List<TaskResult<T>> getTaskResultDetails(String jobName) {
        JobInfo job = getJob(jobName);
        return job.getResultDetails();
    }

    private static class PendingTask<T, U> implements Runnable {

        private JobInfo jobInfo;
        private U parameter;

        public PendingTask(JobInfo jobInfo, U parameter) {
            this.jobInfo = jobInfo;
            this.parameter = parameter;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            T t;
            TaskResult<T> result = null;
            ITaskProcessor<T, U> taskProcessor = (ITaskProcessor<T, U>) jobInfo.getTaskProcessor();
            try {
                result = taskProcessor.taskExecutor(parameter);
                if (result == null) {
                    result = new TaskResult<>(TaskResultType.Exception, null, "返回结果为空");
                } else {
                    if (result.getTaskResultType() == null) {
                        t = result.getResultValue();
                        result = new TaskResult<>(TaskResultType.Exception, t, "返回代码为空");
                    } else {
                        if (result.getMessage() == null) {
                            t = result.getResultValue();
                            result = new TaskResult<>(TaskResultType.Exception, t, "返回信息为空");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = new TaskResult<>(TaskResultType.Exception, null, "执行任务出现异常：" + e.getMessage());
            } finally {
                jobInfo.addTaskResult(result, checkJobProcessor);
            }
        }
    }
}
