package com.dongzhili.easylib.remote;

import android.os.Message;

import com.google.gson.JsonElement;

import java.io.Serializable;

/**
 * API 返回结果实体类
 * Created by Jianan on 2016/08/18.
 */
public class ApiResult implements Serializable {

    public static final String STATUS_OK = "ok";
    public static final String STATUS_ERROR = "error";
    private static final long serialVersionUID = 3585511999870589275L;

    private String status;
    private Message message;
    private JsonElement data;

//    public static ApiResult parseApiResult(String response){
//        ApiResult result = JSONHelper.fromJson(response, ApiResult.class);
//        if(!result.isSuccess()){
//            result.error = JSONHelper.fromJson(result.data, com.mallestudio.gugu.common.api.core.model.Error.class);
//        }
//        return result;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public Message getMessage() {
//        return message;
//    }
//
//    public void setMessage(Message message) {
//        this.message = message;
//    }
//
//    public com.mallestudio.gugu.common.api.core.model.Error getError() {
//        return error;
//    }
//
//    public void setError(com.mallestudio.gugu.common.api.core.model.Error error) {
//        this.error = error;
//    }
//
//    public JsonElement getData() {
//        return data;
//    }
//
//    public void setData(JsonElement data) {
//        this.data = data;
//    }
//
//    public JsonElement getWrapData(){
//        if (data != null && data.isJsonPrimitive()) {
//            JsonObject wrapped = new JsonObject();
//            wrapped.add("result", data);
//            return wrapped;
//        } else {
//            return data;
//        }
//    }
//
//    public boolean isSuccess(){
//        return STATUS_OK.equals(status);
//    }
//
//    public <D> D getSuccess(Class<D> dataClass){
//        return JSONHelper.fromJson(data, dataClass);
//    }
//
//    public <D> D getSuccess(Type type){
//        return JSONHelper.fromJson(data, type);
//    }
//
//    public <D> D getSuccess(Type type, JsonDeserializer<D> deserializer){
//        return JSONHelper.fromJson(data, type, deserializer);
//    }
//
//    public <D> List<D> getSuccessList(Type listType){
//        return JSONHelper.getList(data, null, listType);
//    }
//
//    public <D> List<D> getSuccessList(Type listType, String listName){
//        return JSONHelper.getList(data, listName, listType);
//    }
}
