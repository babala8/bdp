package com.zjft.microservice.treasurybrain.task.domain;

import lombok.Data;

@Data
public class TaskDetailKey {
    private String taskNo;

    private String customerNo;

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo == null ? null : taskNo.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }
}
