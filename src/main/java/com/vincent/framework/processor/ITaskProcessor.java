package com.vincent.framework.processor;

import com.vincent.framework.vo.TaskResult;

public interface ITaskProcessor<T, U> {
    /**
     * 框架使用者需要实现的方法，该方法实现将作为工作的方法
     * @param parameter 方法的参数
     * @return 无
     */
    TaskResult<T> taskExecutor(U parameter);
}
