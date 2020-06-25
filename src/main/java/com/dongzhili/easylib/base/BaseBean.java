package com.dongzhili.easylib.base;

public class BaseBean<T> {

    /**
     * returnCode : 200
     * returnInfo : 操作成功
     * returnData
     */
    public int returnCode;
    public String returnInfo;
    public T returnData;
}
