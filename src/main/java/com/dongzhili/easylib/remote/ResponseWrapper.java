package com.dongzhili.easylib.remote;


import com.google.gson.annotations.SerializedName;

public class ResponseWrapper<T> {
    private static final String STATUS_OK = "ok";

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private ErrorMessage errorMessage;
    @SerializedName("data")
    private T data;


    /**
     * 异常信息的包装类
     */
    public static class ErrorMessage {

        @SerializedName("key")
        private String key;
        @SerializedName("message")
        private String message;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "message{" +
                    "key='" + key + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }

    }


    public boolean isSuccess(){
        return STATUS_OK.equalsIgnoreCase(status);
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseWrapper{" +
                "status='" + status + '\'' +
                ", errorMessage=" + errorMessage +
                ", data=" + data +
                '}';
    }
}
