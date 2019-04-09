package com.vincent.framework.test;

import java.util.Random;

import com.vincent.framework.processor.ITaskProcessor;
import com.vincent.framework.vo.TaskResult;
import com.vincent.framework.vo.TaskResultType;

public class MyTask implements ITaskProcessor<Integer,Integer> {


	@Override
	public TaskResult<Integer> taskExecutor(Integer parameter){
		try {
			Random r = new Random();
			int flag = r.nextInt(500);
			Thread.sleep(flag);
			if(flag<=300) {//正常处理的情况
				Integer returnValue = parameter + flag;
				return new TaskResult<Integer>(TaskResultType.Success,returnValue);
			}else if(flag>301&&flag<=400) {//处理失败的情况
				return new TaskResult<Integer>(TaskResultType.Failure,-1,"Failure");
			}else {//发生异常的情况
				try {
					throw new RuntimeException("异常发生了！！");
				} catch (Exception e) {
					return new TaskResult<Integer>(TaskResultType.Exception,
							-1,e.getMessage());
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return new TaskResult<>(TaskResultType.Exception, -1, e.getMessage());
		}
	}
}
