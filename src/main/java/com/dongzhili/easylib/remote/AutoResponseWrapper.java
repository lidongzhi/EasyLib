package com.dongzhili.easylib.remote;


import android.text.TextUtils;

import com.dongzhili.easylib.utils.LogUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * API响应结果的包装类
 * 注意：仅在需要自动获取'data'下的首个字段时使用
 *
 * @author imkarl
 */
public class AutoResponseWrapper<T> extends ResponseWrapper<T> {

    @Override
    public String toString() {
        return "AutoResponseWrapper{" +
                "status='" + getStatus() + '\'' +
                ", errorMessage=" + getErrorMessage() +
                ", data=" + getData() +
                '}';
    }

    public final static class AutoResponseWrapperJsonDeserializer implements JsonDeserializer<AutoResponseWrapper> {

        @Override
        public AutoResponseWrapper deserialize(JsonElement json, Type typeOfT,
                                               JsonDeserializationContext context) throws JsonParseException {
            if (json instanceof JsonObject) {
                handler((JsonObject) json);
            }
            return JsonUtil.fromJson(json, typeOfT);
        }

        private void handler(JsonObject json) {
            JsonElement dataJson = json.get("data");

            // 'data'节点不是JsonObject则忽略
            if (dataJson == null || !dataJson.isJsonObject()) {
                return;
            }
            // 'data'下的节点数不是恰好为1个则忽略
            JsonObject data = dataJson.getAsJsonObject();
            if (data.size() != 1) {
                return;
            }

            // 'data'下包含'error_code'
            JsonElement errorJson = data.get("error_code");
            if (errorJson != null) {
                data.remove("error_code");

                if (errorJson.isJsonPrimitive()) {
                    try {
                        String errorCode = errorJson.getAsString();
                        if (!TextUtils.isEmpty(errorCode)) {
                            // 将'data'下的'error_code'移到'message'节点下
                            JsonElement errMessageJson = json.get("message");
                            if (errMessageJson == null) {
                                errMessageJson = new JsonObject();
                                json.add("message", errMessageJson);
                            }
                            if (errMessageJson.isJsonObject()) {
                                errMessageJson.getAsJsonObject().addProperty("key", errorCode);
                            }
                        }
                    } catch (Exception ignored) {
                    }
                }
            }

            if (data.size() == 0) {
                json.add("data", null);
                return;
            }
            try {
                // 取出'data'下的第一个节点
                Set<Map.Entry<String, JsonElement>> entrySet = data.entrySet();
                Map.Entry[] entries = new Map.Entry[data.size()];
                entries = entrySet.toArray(entries);
                JsonElement element = (JsonElement) entries[0].getValue();

                // 更新'data'的值
                json.add("data", element);
            } catch (Exception e) {
                LogUtils.e(e.getLocalizedMessage());
            }
        }
    }
}
