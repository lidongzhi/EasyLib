package com.dongzhili.easylib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatHelper {
    public static final String limitTime = "00:00";
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    public static final int day = 60 * 60 * 24 * 1000;

    /**
     *  将年月日和分割时刻进行拼接再转Date型
     * @param yyyyMMdd 参数年月日
     */
    public static Date beginDate(String yyyyMMdd) throws ParseException {
        String date = yyyyMMdd + " " + limitTime;
        return simpleDateFormat.parse(date);
    }

    public static Date endDate(String yyyyMMdd) throws ParseException {
        Date startDate = beginDate(yyyyMMdd);
        long endTime = startDate.getTime() + day;
        Date endDate = new Date(endTime);
        return endDate;
    }
}
