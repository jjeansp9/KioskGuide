package com.avad.humancare.kiosk.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static String[] mWeekDayName = {"일","월","화","수","목","금","토"};
    public static String DATE_FORMAT_YMD = "yyyy년 MM월 dd일";
    public static SimpleDateFormat DATE_FORMAT_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat DATE_FORMAT_YYYY_MM_WITH_LETTER = new SimpleDateFormat("yyyy년 MM월");
    public static SimpleDateFormat DATE_FORMAT_YYdotMMdotDD = new SimpleDateFormat("yy.MM.dd");

    public static String getYearMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월");
        String strDate = sdf.format(date);
        return strDate;
    }

    public static String getDateFormatter(String dateStr) {
        // 기존 format에 요일 더하기
        String str = dateStr;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMD);

        try {
            Date date = sdf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            str += " (" + mWeekDayName[cal.get(Calendar.DAY_OF_WEEK)-1] + ")";

        } catch(Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    // 날짜 변경(계산)하기
    public static String getCalculationDate(String dateStr, int day) {
        String str = dateStr;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMD);

        try {
            Date date = sdf.parse(dateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, day);

            str = sdf.format(cal.getTime());

        } catch(Exception e) {
            e.printStackTrace();
        }

        return str;
    }

}
