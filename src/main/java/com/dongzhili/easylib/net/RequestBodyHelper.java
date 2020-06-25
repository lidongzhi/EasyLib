package com.dongzhili.easylib.net;

import android.util.Log;

import com.google.gson.Gson;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestBodyHelper {
    private MediaType mediaType;
    private Gson gson;

    private RequestBodyHelper() {
        mediaType = MediaType.parse("application/json; charset=utf-8");
        gson = new Gson();
    }


    private static class SingletonHolder {
        private static final RequestBodyHelper INSTANCE = new RequestBodyHelper();
    }

    public static RequestBodyHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }


    public RequestBody empty() {
        return RequestBody.create(mediaType, "");
    }


    public RequestBody createBody(Map<String,String> map) {
        String json = gson.toJson(map);
        Log.e("lidz", "请求的json是:" + json);
        return RequestBody.create(mediaType, json);
    }

    public RequestBody createBody(Object object) {
        String json = gson.toJson(object);
        Log.e("lidz", "请求的json是:" + json);
        return RequestBody.create(mediaType, json);
    }
}
