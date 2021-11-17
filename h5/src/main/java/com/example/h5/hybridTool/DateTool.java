package com.example.h5.hybridTool;

import android.os.SystemClock;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTool {
    public final static String DATETIME = "yyyy-MM-dd HH:mm:ss";
    public final static String DATETIME1 = "yyyy-MM-dd HH:mm";
    public final static String DATE = "yyyy-MM-dd";
    public final static String MONTH = "yyyy-MM";
    public final static String TIME = "HH:mm:ss";
    public final static String SFDC_DATETIME = "yyyy-MM-ddTHH:mm:ssZ";

    private static String m_BackData = "fail";// R.dimen.DIMEN_300PX;

    private static long m_SystemTime = 0;// R.dimen.DIMEN_300PX;
    private static Date m_Date;

    public static void setSystemUpTime(String time) {
        m_Date = strToDateLong(time);
        m_SystemTime = SystemClock.elapsedRealtime();
    }

    public static String CurrDate() {
        java.util.Date date = new java.util.Date();
        return FormatDate(date, DATE);
    }

    public static String FormatDate(Date date, String fromat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(fromat);
        dateFormat.setTimeZone(TimeZone.getTimeZone(Constants.TimeZone.ZH_CH));
        return dateFormat.format(date);
    }

    public static String CurrDateTime() {
        java.util.Date date = new java.util.Date();
        if (m_Date != null && m_SystemTime != 0) {
            long curTime = SystemClock.elapsedRealtime();
            long gap = curTime - m_SystemTime;
            date = new Date(gap + m_Date.getTime());
        }
        return FormatDate(date, DATETIME);
    }

    public static void setBackData(String result) {
        m_BackData = result;
    }

    public static String getBackData() {
        return m_BackData;
    }

    public static String getWeekDay(Calendar c) {
        if (c == null) {
            return "星期一";
        }

        if (Calendar.MONDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期一";
        }
        if (Calendar.TUESDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期二";
        }
        if (Calendar.WEDNESDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期三";
        }
        if (Calendar.THURSDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期四";
        }
        if (Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期五";
        }
        if (Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期六";
        }
        if (Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期日";
        }

        return "星期一";
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
//	public static Date strToDateLong(String strDate) {
//		// SimpleDateFormat formatter = new
//		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		ParsePosition pos = new ParsePosition(0);
//		Date strtodate = formatter.parse(strDate, pos);
//		return strtodate;
//	}
    private static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone
                .getTimeZone(Constants.TimeZone.ZH_CH));
        ParsePosition pos = new ParsePosition(0);

        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
}
