package com.dongzhili.easylib.download;

public class Constants {
    public static class Limit{
        public static final int RECEIVE_DEFAULT_SIZE = 5;
        public static final int JOURNEY_DEFAULT_SIZE = 5;
    }
    public static final int SUCCESS = 500;
    public static final int FAILED = 505;

    public static final String RECEIVER_GPS = "com.zxw.gps.receiver";

    public class Path{
        public static final String SECONDPATH = "DriverDevice/download";
        public static final String ERRORPATH = "DriverDevice/error/";
        public static final String APKNAME = "DriverDevice.apk";
    }

}
