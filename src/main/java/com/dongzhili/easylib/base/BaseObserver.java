package com.dongzhili.easylib.base;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.dongzhili.easylib.net.ServerExecption;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<BaseBean<T>> {

    //对应 HTTP 的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    private static final String LOGIN = "545";


    @Override
    public void onError(Throwable e) {
//        mExceptionHandler.handleException(e);
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                    onError("当前请求需要用户验证");
                    break;
                case FORBIDDEN:
                    onError("服务器已经理解请求，但是拒绝执行它");
                    break;
                case NOT_FOUND:
                    onError("服务器404异常，请稍后再试");
                    break;
                case REQUEST_TIMEOUT:
                    onError("请求超时");
                    break;
                case GATEWAY_TIMEOUT:
                    onError("作为网关或者代理工作的服务器尝试执行请求时，未能及时从上游服务器（URI 标识出的服务器，例如 HTTP、FTP、LDAP）或者辅助服务器（例如 DNS）收到响应");
                    break;
                case INTERNAL_SERVER_ERROR:
                    onError("服务器遇到了一个未曾预料的状况，导致了它无法完成对请求的处理");
                    break;
                case BAD_GATEWAY:
                    onError("作为网关或者代理工作的服务器尝试执行请求时，从上游服务器接收到无效的响应");
                    break;
                case SERVICE_UNAVAILABLE:
                    onError("由于临时的服务器维护或者过载，服务器当前无法处理请求");
                    break;
                default:
                    onError("网络错误");  //其它均视为网络错误
                    break;
            }
        } else if (e instanceof ServerExecption) {
            ServerExecption execption = (ServerExecption) e;
            if (LOGIN.equals(execption.errorCode)) onLogin();
            else onError(execption.getMessage());
        } else if (e instanceof ConnectException) onError("连接失败");
        else if (e instanceof JsonParseException || e instanceof JSONException) onError("解析失败");
        else if (e instanceof SSLHandshakeException) onError("证书验证失败");
        else if (e instanceof UnknownHostException) onError("网络异常");
        else if (e instanceof SocketTimeoutException) onError("连接超时");
        else {
            e.printStackTrace();
            onError("挂了代理或服务器错误");
        }
    }

    @Override
    public void onNext(BaseBean<T> response) {
        int returnCode = response.returnCode;
        if (returnCode == 200 || returnCode == 500) {
            onSuccess(response.returnData);
        } else {
            onFailed(returnCode, response.returnInfo);
        }
    }

    protected abstract void onFailed(int returnCode, String returnInfo);

    protected abstract void onSuccess(T returnData);

    protected abstract void onError(String errorMsg);

    protected void onLogin() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }
}
