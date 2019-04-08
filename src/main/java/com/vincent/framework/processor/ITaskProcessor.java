package com.vincent.framework.processor;

import com.vincent.framework.vo.TaskResult;

public interface ITaskProcessor<T, U> {
    TaskResult<T> taskExecutor(U parameter);
}
