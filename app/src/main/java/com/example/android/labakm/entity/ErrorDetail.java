package com.example.android.labakm.entity;

public class ErrorDetail {
    public int status;
    public String message;
    public Object detailmessage;
    public int executiontime;
    public Object detailInfo;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDetailmessage() {
        return detailmessage;
    }

    public void setDetailmessage(Object detailmessage) {
        this.detailmessage = detailmessage;
    }

    public int getExecutiontime() {
        return executiontime;
    }

    public void setExecutiontime(int executiontime) {
        this.executiontime = executiontime;
    }

    public Object getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(Object detailInfo) {
        this.detailInfo = detailInfo;
    }

    @Override
    public String toString() {
        return "ErrorDetail{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", detailmessage=" + detailmessage +
                ", executiontime=" + executiontime +
                ", detailInfo=" + detailInfo +
                '}';
    }
}
