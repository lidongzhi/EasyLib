package com.dongzhili.easylib.remote;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.pm.ApplicationInfo;

import org.joor.Reflect;
import org.joor.ReflectException;

public class AppUtils {

    private AppUtils() {
    }

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    /**
     * 在Application初始化之后初始化
     *
     * @param application app Application
     * @param isDebug     是否debug
     */
    public static void init(Application application, boolean isDebug) {
        if (application != null) {
            sApplication = application;
        }
        AppUtils.isDebug = isDebug;
    }

    /**
     * @return 获取当前app运行时Application
     */
    @TargetApi(14)
    public static Application getApplication() {
        if (sApplication == null) {
            synchronized (AppUtils.class) {
                if (sApplication == null) {
                    // 适用于 android 4.0 以后
                    Application application = null;
                    try {
                        application = Reflect.on("android.app.AppGlobals").call("getInitialApplication").get();
                    } catch (ReflectException ignored) {
                    }
                    if (application == null) {
                        try {
                            application = Reflect.on("android.app.ActivityThread").call("currentApplication").get();
                        } catch (ReflectException ignored) {
                        }
                    }
                    sApplication = application;
                }
            }
        }
        return sApplication;
    }


    private static Boolean isDebug = null;

    /**
     * @return 是否Debug
     */
    public static boolean isDebug() {
        if (isDebug != null) {
            return isDebug;
        }
        if (getApplication() == null) {
            return false;
        }

        try {
            ApplicationInfo info = getApplication().getApplicationInfo();
            isDebug = info != null && (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            return isDebug;
        } catch (Exception ignored) {
            return false;
        }
    }
}