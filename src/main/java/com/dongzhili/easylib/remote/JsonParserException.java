package com.dongzhili.easylib.remote;

import com.google.gson.JsonElement;

public class JsonParserException extends RuntimeException {

    private static final long serialVersionUID = -7083912400183824192L;
    private JsonElement json;

    public JsonParserException(JsonElement json) {
        init(json);
    }

    public JsonParserException(JsonElement json, String detailMessage) {
        super(detailMessage);
        init(json);
    }

    public JsonParserException(JsonElement json, String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        init(json);
    }

    public JsonParserException(JsonElement json, Throwable throwable) {
        super(throwable);
        init(json);
    }

    private void init(JsonElement json) {
        this.json = json;
    }

    @Override
    public String toString() {
        return super.toString() + "\njson: " + json;
    }

    /**
     * 获取原始JSON数据
     */
    public JsonElement getJson() {
        return json;
    }

}

