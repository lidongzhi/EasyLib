package com.dongzhili.easylib.net;

/**
 * 捕获异常
 * 将服务器返回的msg加入ApiExecption的msg中
 * 也可以根据和服务器约定好的errorCode来判断
 * Created by 残梦 on 2018/3/30.
 */

public class ServerExecption extends RuntimeException {

    public Throwable throwable;
    public String errorMsg;
    public int errorCode;

    public ServerExecption(int code, String msg) {
        super(msg);
        this.errorCode = code;
        this.errorMsg = msg;
    }

    public ServerExecption(Throwable throwable) {
        this.throwable = throwable;
    }

}
