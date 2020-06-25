package com.dongzhili.easylib.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonUtil {

    private static Gson sGson;
    private static Gson sGsonPretty;

    public static <T> T fromJson(String json, TypeToken<T> typeToken) {
        return fromJson(json, typeToken.getType());
    }

    public static <T> T fromJson(JsonElement json, TypeToken<T> typeToken) {
        return fromJson(json, typeToken.getType());
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return getGson().fromJson(json, typeOfT);
    }

    public static <T> T fromJson(JsonElement json, Type typeOfT) {
        return getGson().fromJson(json, typeOfT);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return getGson().fromJson(json, classOfT);
    }

    public static <T> T fromJson(JsonElement json, Class<T> classOfT) {
        return getGson().fromJson(json, classOfT);
    }

    public static  <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
        return getGson().fromJson(json, classOfT);
    }

    public static  <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return getGson().fromJson(json, typeOfT);
    }

    public static  <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return getGson().fromJson(reader, typeOfT);
    }

    public static <T> T fromJson(File jsonFile, Class<T> classOft) throws FileNotFoundException {
        JsonReader jsonReader = new JsonReader(new FileReader(jsonFile));
        jsonReader.setLenient(true);
        return getGson().fromJson(jsonReader, classOft);
    }

    public static JsonReader newJsonReader(String json) {
        return newJsonReader(new StringReader(json));
    }
    public static JsonReader newJsonReader(Reader reader) {
        return new JsonReader(reader);
    }

    public static JsonElement toJsonElement(Object src) {
        return getGson().toJsonTree(src);
    }

    public static <T> JsonElement toJsonElement(Object src, TypeToken<T> typeToken) {
        return toJsonElement(src, typeToken.getType());
    }

    public static JsonElement toJsonElement(Object src, Type typeOfSrc) {
        return getGson().toJsonTree(src, typeOfSrc);
    }

    public static String toJson(Object src) {
        return getGson().toJson(src);
    }

    public static String toJson(Object src, Type typeOfSrc) {
        return getGson().toJson(src, typeOfSrc);
    }

    public static <T> String toJson(Object src, TypeToken<T> typeToken) {
        return getGson().toJson(src, typeToken.getType());
    }

    public static void toJson(Object src, Appendable writer) throws JsonIOException {
        getGson().toJson(src, writer);
    }

    public static void toJson(Object src, Type typeOfSrc, Appendable writer) throws JsonIOException {
        getGson().toJson(src, typeOfSrc, writer);
    }

    public static void toJson(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException {
        getGson().toJson(src, typeOfSrc, writer);
    }

    @SuppressWarnings("unchecked")
    public static void toJsonWithCustomWriter(Object src, Type typeOfSrc, JsonWriter writer) throws JsonIOException {
        TypeAdapter<?> adapter = getGson().getAdapter(TypeToken.get(typeOfSrc));
        try {
            ((TypeAdapter<Object>) adapter).write(writer, src);
        } catch (IOException e) {
            throw new JsonIOException(e);
        }
    }

    public static JsonWriter newJsonWriter() {
        return newJsonWriter(Streams.writerForAppendable(new StringWriter()));
    }

    public static JsonWriter newJsonWriter(Writer writer) {
        return new JsonWriter(writer);
    }

    public static String toJson(JsonElement jsonElement) {
        return getGson().toJson(jsonElement);
    }

    public static void toJson(JsonElement jsonElement, Appendable writer) throws JsonIOException {
        getGson().toJson(jsonElement, writer);
    }

    public static void toJson(JsonElement jsonElement, JsonWriter writer) throws JsonIOException {
        getGson().toJson(jsonElement, writer);
    }

    public static void toJsonWithCustomWriter(JsonElement jsonElement, JsonWriter writer) throws JsonIOException {
        try {
            Streams.write(jsonElement, writer);
        } catch (IOException e) {
            throw new JsonIOException(e);
        }
    }

    public static String toJsonPrettyPrinting(Object src) {
        return getGsonPretty().toJson(src);
    }

    public static char optCharacter(JsonObject jsonObj, String memberName) {
        return optCharacter(jsonObj, memberName, '0');
    }

    public static char optCharacter(JsonObject jsonObj, String memberName, char defValue) {
        try {
            return jsonObj.get(memberName).getAsCharacter();
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static String optString(JsonObject jsonObj, String memberName) {
        return optString(jsonObj, memberName, "");
    }

    public static String optString(JsonObject jsonObj, String memberName, String defValue) {
        try {
            return jsonObj.get(memberName).getAsString();
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static byte optByte(JsonObject jsonObject, String member) {
        return optByte(jsonObject, member, (byte) 0);
    }

    public static byte optByte(JsonObject jsonObject, String memberName, byte defValue) {
        try {
            return jsonObject.get(memberName).getAsByte();
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static short optShort(JsonObject jsonObject, String member) {
        return optShort(jsonObject, member, (short) 0);
    }

    public static short optShort(JsonObject jsonObject, String memberName, short defValue) {
        try {
            return jsonObject.get(memberName).getAsShort();
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static int optInt(JsonObject jsonObject, String member) {
        return optInt(jsonObject, member, 0);
    }

    public static int optInt(JsonObject jsonObject, String memberName, int defValue) {
        try {
            return jsonObject.get(memberName).getAsInt();
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static long optLong(JsonObject jsonObject, String member) {
        return optLong(jsonObject, member, 0);
    }

    public static long optLong(JsonObject jsonObject, String memberName, long defValue) {
        try {
            return jsonObject.get(memberName).getAsLong();
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static Number optNumber(JsonObject jsonObject, String member) {
        return optNumber(jsonObject, member, null);
    }

    public static Number optNumber(JsonObject jsonObject, String memberName, Number defValue) {
        try {
            return jsonObject.get(memberName).getAsNumber();
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static BigInteger optBigInteger(JsonObject jsonObject, String member) {
        return optBigInteger(jsonObject, member, null);
    }

    public static BigInteger optBigInteger(JsonObject jsonObject, String memberName, BigInteger defValue) {
        try {
            return jsonObject.get(memberName).getAsBigInteger();
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static BigDecimal optBigDecimal(JsonObject jsonObject, String member) {
        return optBigDecimal(jsonObject, member, null);
    }

    public static BigDecimal optBigDecimal(JsonObject jsonObject, String memberName, BigDecimal defValue) {
        try {
            return jsonObject.get(memberName).getAsBigDecimal();
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static float optFloat(JsonObject jsonObject, String memberName) {
        return optFloat(jsonObject, memberName, 0F);
    }

    public static float optFloat(JsonObject jsonObject, String memberName, float defValue) {
        try {
            return jsonObject.get(memberName).getAsFloat();
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static boolean optBoolean(JsonObject jsonObject, String memberName) {
        return optBoolean(jsonObject, memberName, false);
    }

    public static boolean optBoolean(JsonObject jsonObject, String memberName, boolean defValue) {
        try {
            return jsonObject.get(memberName).getAsBoolean();
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static JsonArray optJsonArray(JsonObject jsonObject, String memberName) {
        return optJsonArray(jsonObject, memberName, null);
    }

    public static JsonArray optJsonArray(JsonObject jsonObject, String memberName, JsonArray defValue) {
        try {
            return jsonObject.get(memberName).getAsJsonArray();
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static JsonObject optJsonObject(JsonObject jsonObject, String memberName) {
        return optJsonObject(jsonObject, memberName, null);
    }

    public static JsonObject optJsonObject(JsonObject jsonObject, String memberName, JsonObject defValue) {
        try {
            return jsonObject.get(memberName).getAsJsonObject();
        } catch (Exception ex) {
            return defValue;
        }
    }

    public static Gson getGson() {
        if (sGson == null) {
            synchronized (JsonUtil.class) {
                if (sGson == null) {
                    sGson = new GsonBuilder()
                            .create();
                }
            }
        }
        return sGson;
    }

    public static Gson getGsonPretty() {
        if (sGsonPretty == null) {
            synchronized (JsonUtil.class) {
                if (sGsonPretty == null) {
                    sGsonPretty = new GsonBuilder().setPrettyPrinting().create();
                }
            }
        }
        return sGsonPretty;
    }

    public static Gson configGson(GsonBuilder gsonBuilder) {
        synchronized (JsonUtil.class) {
            sGson = gsonBuilder.create();
        }
        return sGson;
    }

    public static Gson configGsonPretty(GsonBuilder gsonBuilder) {
        synchronized (JsonUtil.class) {
            sGsonPretty = gsonBuilder.setPrettyPrinting().create();
        }
        return sGsonPretty;
    }
}
