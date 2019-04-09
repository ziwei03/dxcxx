package com.vincent.framework.vo;

public class TaskResult<T> {
    private final TaskResultType taskResultType;
    private final T resultValue;
    private final String message;

    public TaskResult(TaskResultType taskResultType, T resultValue, String message) {
        this.taskResultType = taskResultType;
        this.resultValue = resultValue;
        this.message = message;
    }

    public TaskResult(TaskResultType taskResultType, T resultValue) {
        this.taskResultType = taskResultType;
        this.resultValue = resultValue;
        this.message = "success";
    }

    public TaskResult(T resultValue, String message) {
        this.taskResultType = TaskResultType.Success;
        this.resultValue = resultValue;
        this.message = message;
    }

    public TaskResultType getTaskResultType() {
        return taskResultType;
    }

    public T getResultValue() {
        return resultValue;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "taskResultType=" + taskResultType +
                ", resultValue=" + resultValue +
                ", message='" + message + '\'' +
                '}';
    }
}
