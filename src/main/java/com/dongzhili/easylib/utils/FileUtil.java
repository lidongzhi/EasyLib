package com.dongzhili.easylib.utils;

import com.dongzhili.easylib.remote.AppUtils;

import java.io.File;

public class FileUtil {

    private static File insideStorageDir;
    private static File insideCacheDir;
    private static File outsideStorageDir;
    private static File outsideCacheDir;

    private static File currentStorageDir;
    private static File currentCacheDir;

    static {
        insideStorageDir = AppUtils.getApplication().getFilesDir();
        insideCacheDir = AppUtils.getApplication().getCacheDir();
        outsideStorageDir = AppUtils.getApplication().getExternalFilesDir(null);
        outsideCacheDir = AppUtils.getApplication().getExternalCacheDir();

        init();
    }

    public static void init() {
//        if (SettingsManagement.global().getBoolean(SettingConstant.KEY_IS_USE_EXTERNAL)) {
            //sdcard
//            currentStorageDir = outsideStorageDir;
//            currentCacheDir = outsideCacheDir;
//        } else {
            //room
            currentStorageDir = insideStorageDir;
            currentCacheDir = insideCacheDir;
//        }
    }


    public static File createDir(File parentDir, String dirName) {
        File dir = new File(parentDir, dirName);
        return checkDir(dir);
    }

    public static File getHttpCacheDir() {
        return createDir(getCacheDir(), "http");
    }


    public static File checkDir(File dir) {
        if (dir.exists() && dir.isFile()) {
            dir.delete();
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static File getCacheDir() {
        if (!currentCacheDir.exists()) {
            currentCacheDir.mkdirs();
        }
        return currentCacheDir;
    }
}
