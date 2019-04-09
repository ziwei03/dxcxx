package com.vincent.framework.vo;

import com.vincent.framework.processor.CheckJobProcessor;
import com.vincent.framework.processor.ITaskProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class JobInfo {

    private final String jobName;
    private final int taskLength;
    private final ITaskProcessor<?, ?> taskProcessor;
    private final AtomicInteger successCount;
    private final AtomicInteger processCount;
    private final LinkedBlockingDeque<TaskResult<?>> resultQueue;
    private final long expireTime;

    public JobInfo(String jobName, int taskLength, ITaskProcessor<?, ?> taskProcessor, long expireTime) {
        this.jobName = jobName;
        this.taskLength = taskLength;
        this.taskProcessor = taskProcessor;
        this.successCount = new AtomicInteger(0);
        this.processCount = new AtomicInteger(0);
        this.resultQueue = new LinkedBlockingDeque<>(taskLength);
        this.expireTime = expireTime;
    }

    public String getJobName() {
        return jobName;
    }

    public int getTaskLength() {
        return taskLength;
    }

    public ITaskProcessor<?, ?> getTaskProcessor() {
        return taskProcessor;
    }

    public int getSuccessCount() {
        return successCount.get();
    }

    public int getProcessCount() {
        return processCount.get();
    }

    public int getFailCount() {
        return processCount.get() - successCount.get();
    }

    public <T> void addTaskResult(TaskResult<T> taskResult, CheckJobProcessor checkJobProcessor) {
        if (TaskResultType.Success == taskResult.getTaskResultType()) {
            successCount.incrementAndGet();
        }
        resultQueue.addLast(taskResult);
        processCount.incrementAndGet();

        if (processCount.get() == taskLength) {
            checkJobProcessor.putJob(jobName, expireTime);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<TaskResult<T>> getResultDetails() {
        TaskResult<T> taskResult;
        List<TaskResult<T>> taskResultList = new ArrayList<>();
        while ((taskResult = (TaskResult<T>) resultQueue.pollFirst()) != null) {
            taskResultList.add(taskResult);
        }
        return taskResultList;
    }

    public String getTotalProgress() {
        return "[success:" + successCount.get() + "/current:" + processCount.get() + "] [total:" + taskLength + "]";
    }

}
