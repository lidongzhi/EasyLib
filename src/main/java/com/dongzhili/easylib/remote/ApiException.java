package com.dongzhili.easylib.remote;


public class ApiException extends Exception {

    private static final long serialVersionUID = 2191003853750268836L;
    private String code;

    public ApiException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "code='" + code + '\'' +
                ", message='" + getMessage() + '\'' +
                '}';
    }

}
