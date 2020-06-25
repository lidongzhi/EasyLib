package com.dongzhili.easylib.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import java.io.File;
import java.util.Date;

public class NotificationUtil {
    //  30天
    private static long intervalTime = 1000L * 60L * 60L * 24L * 30L;

    /**
     * 定期清理LOG文件
     *
     * @param context
     */
    public static void clean(Context context) {
        long currentTime = new Date().getTime();
        long cleanTime = SpUtil.getLongCache(context, "clean");
        LogUtils.w("interval" + intervalTime);
        LogUtils.w("currentTime" + currentTime);
        LogUtils.w("cleanTime" + cleanTime);
        if (currentTime >= cleanTime + intervalTime) {
            SpUtil.setCache(context, "clean", currentTime);
            String filePath = Environment.getExternalStorageDirectory() + "/BdYy/";
            deleteDir(filePath);
        }
    }

    private static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWihtFile(dir);
    }

    private static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    public static void clearNoticeData(Context context) {
        Intent intent = new Intent("com.android.action.NOTIFICATION_CLEAN");
        context.sendBroadcast(intent);
    }
}
