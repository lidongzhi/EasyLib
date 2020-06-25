package com.dongzhili.easylib.utils;

import android.os.Environment;
import android.util.Log;

import com.dongzhili.easylib.base.BaseToolLibConfig;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {

    private static final String TAG = "zxw";
    private static final boolean isDebuggable = BaseToolLibConfig.DEBUG;
    static String className;
    static String methodName;

    private static String createLog(String log) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(methodName);
        buffer.append("]");
        buffer.append(log);

        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
    }

    public static void e(String message) {
        if (!isDebuggable)
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }

    public static void e(String tag, String message) {
        if (!isDebuggable)
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        Log.e(tag, createLog(message));
    }

    public static void i(String message) {
        if (!isDebuggable)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void i(String tagName, String message) {
        if (!isDebuggable)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(tagName, createLog(message));
    }

    public static void d(String message) {
        if (!isDebuggable)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void v(String message) {
        if (!isDebuggable)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }

    public static void w(String message) {
        if (!isDebuggable)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

    public static void w(String tag, String message) {
        if (!isDebuggable)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(tag, createLog(message));
    }

    public static void wtf(String message) {
        if (!isDebuggable)
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(className, createLog(message));
    }


    private static void log(String fileName, String str){
//        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd_");
//        String day = format2.format(new Date());
//        fileName = day + fileName;

        SimpleDateFormat pathFormat = new SimpleDateFormat("yyyy-MM-dd");
        String datePath = pathFormat.format(new Date());

        String filePath = Environment.getExternalStorageDirectory() + "/zxw/driver/" + datePath + "/";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String time = format.format(new Date());

        str = "\r\n" + time +"\r\n" + str +"\r\n";
        writeTxtToFile(str, filePath, fileName);
    }

    public static void log(String str){
        Log.e(TAG, "log: " + str );
        String fileName = "main.txt";
        log(fileName, str);
    }

    public static void loadRemoteError(String str) {
        String fileName = "loadRemoteError.txt";
        log(fileName, str);
    }

    // 将字符串写入到文本文件中
    public static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        makeFilePath(filePath, fileName);
        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
        }

    }

}
