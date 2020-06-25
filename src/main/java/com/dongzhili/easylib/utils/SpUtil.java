package com.dongzhili.easylib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class SpUtil {

    private static SharedPreferences sp;
    private final static String CACHE_FILE_NAME = "config";
    public final static String FIRST = "first";
    public final static String USER_ID = "userId";
    public final static String NAME = "name";
    public final static String KEYCODE = "keycode";
    public final static String TOKEN = "token";
    public final static String FLEET_NAME = "fleetName";

    private static void initSp(Context mContext, String fileName) {

        sp = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public static boolean isFirst(Context mContext) {
        if (sp == null) {
            initSp(mContext, CACHE_FILE_NAME);
        }
        return sp.getBoolean(FIRST, true);
    }

    public static void setCache(Context mContext, String key, String value) {
        if (sp == null) {
            initSp(mContext, CACHE_FILE_NAME);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static void setCache(Context mContext, String key, int value) {
        if (sp == null) {
            initSp(mContext, CACHE_FILE_NAME);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static int getIntCache(Context mContext, String key) {
        if (sp == null) {
            initSp(mContext, CACHE_FILE_NAME);
        }
        return sp.getInt(key, -1);
    }

    public static String getCache(Context mContext, String key) {
        if (sp == null) {
            initSp(mContext, CACHE_FILE_NAME);
        }
        return sp.getString(key, null);
    }

    public static boolean getCacheBoolean(Context mContext, String key) {
        if (sp == null) {
            initSp(mContext, CACHE_FILE_NAME);
        }
        return sp.getBoolean(key, false);
    }

    public static void setCache(Context mContext, String key, boolean value) {
        if (sp == null) {
            initSp(mContext, CACHE_FILE_NAME);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }


    public static void cacheErrorLog(Context mContext, String errorLog, String userPhone) {
        if (sp == null) {
            initSp(mContext, CACHE_FILE_NAME);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("errorLog", errorLog);
        edit.putString("errorLogName", userPhone);
        edit.commit();
    }

    public static List<String> getErrorLog(Context mContext) {
        if (sp == null) {
            initSp(mContext, CACHE_FILE_NAME);
        }
        List<String> list = new ArrayList<String>();
        list.add(sp.getString("errorLog", ""));
        list.add(sp.getString("errorLogName", ""));

        return list;
    }


    public static Long getLongCache(Context mContext, String key) {
        if (sp == null) {
            initSp(mContext, CACHE_FILE_NAME);
        }
        return sp.getLong(key, -1);
    }

    // 数据库更新时间记录方法
    public static long getTableUpdateTime(Context context, String key) {
        if (sp == null) {
            initSp(context, CACHE_FILE_NAME);
        }
        return sp.getLong(key, -1);
    }

    public static void setCache(Context mContext, String key, Long value) {
        if (sp == null) {
            initSp(mContext, CACHE_FILE_NAME);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(key, value);
        edit.apply();
    }


    public static boolean isLogin(Context mContext) {
        if(sp == null){
            initSp(mContext,CACHE_FILE_NAME);
        }
        String userId = sp.getString(USER_ID, "");
        String keycode = sp.getString(KEYCODE, "");
        String name = sp.getString(NAME, "");

        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(keycode) || TextUtils.isEmpty(name))
            return false;
        return true;
    }
}
