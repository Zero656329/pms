package com.sunny.pms.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private int x;                  // 日期属性：年
    private int y;                  // 日期属性：月
    private int z;                  // 日期属性：日
    private Calendar localTime;     // 当前日期
    //日期比较方法
     public static int compareDate(Date first, Date second){
               return first.compareTo(second);
     }

    public static Date getNextDate(Date date) throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
        return calendar.getTime();
    }

    public static Date getTheDateOfBegin(Date date) throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static int daydiff(Date fDate, Date oDate) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(fDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(oDate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));

    }

    public static String format(Date date, String pattern)
    {
        return date == null ? " " : new SimpleDateFormat(pattern).format(date);
    }

    /**
     *
     * @param input "2012-5-27"
     * @return
     * @throws Exception
     */
    public static Date convernString2Date(String input) throws Exception {
        return convernString2Date(input,"yyyy-MM-dd");
    }

    public static Date convernString2Date(String input,String pattern) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(input);
    }

    /**
     * @param input "2012-5-27"
     * @return
     * @throws Exception
     */
    public static Calendar convernString2Calendar(String input) throws Exception {
        Date date =  convernString2Date(input);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public DateUtil() {
        localTime = Calendar.getInstance();
    }

    public DateUtil(String inputDate) {
        try {
            localTime = convernString2Calendar(inputDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 功能：得到当前日期 格式为：xxxx-yy-zz (eg: 2007-12-05)<br>
     *
     * @return String
     * @author pure
     */
    public  String today() {
        String strY = null;
        String strZ = null;
        x = localTime.get(Calendar.YEAR);
        y = localTime.get(Calendar.MONTH) + 1;
        z = localTime.get(Calendar.DATE);
        strY = y >= 10 ? String.valueOf(y) : ("0" + y);
        strZ = z >= 10 ? String.valueOf(z) : ("0" + z);
        return x + "-" + strY + "-" + strZ;
    }

    /**
     * 功能：得到当前月份月初 格式为：xxxx-yy-zz (eg: 2007-12-01)<br>
     *
     * @return String
     * @author pure
     */
    public String thisMonth() {
        String strY = null;
        x = localTime.get(Calendar.YEAR);
        y = localTime.get(Calendar.MONTH) + 1;
        strY = y >= 10 ? String.valueOf(y) : ("0" + y);
        return x + "-" + strY + "-01";
    }

    /**
     * 功能：得到当前月份月底 格式为：xxxx-yy-zz (eg: 2007-12-31)<br>
     *
     * @return String
     * @author pure
     */
    public String thisMonthEnd() {
        String strY = null;
        String strZ = null;
        boolean leap = false;
        x = localTime.get(Calendar.YEAR);
        y = localTime.get(Calendar.MONTH) + 1;
        if (y == 1 || y == 3 || y == 5 || y == 7 || y == 8 || y == 10 || y == 12) {
            strZ = "31";
        }
        if (y == 4 || y == 6 || y == 9 || y == 11) {
            strZ = "30";
        }
        if (y == 2) {
            leap = leapYear(x);
            if (leap) {
                strZ = "29";
            } else {
                strZ = "28";
            }
        }
        strY = y >= 10 ? String.valueOf(y) : ("0" + y);
        return x + "-" + strY + "-" + strZ;
    }

    /**
     * 功能：得到当前季度季初 格式为：xxxx-yy-zz (eg: 2007-10-01)<br>
     *
     * @return String
     * @author pure
     */
    public String thisSeason() {
        String dateString = "";
        x = localTime.get(Calendar.YEAR);
        y = localTime.get(Calendar.MONTH) + 1;
        if (y >= 1 && y <= 3) {
            dateString = x + "-" + "01" + "-" + "01";
        }
        if (y >= 4 && y <= 6) {
            dateString = x + "-" + "04" + "-" + "01";
        }
        if (y >= 7 && y <= 9) {
            dateString = x + "-" + "07" + "-" + "01";
        }
        if (y >= 10 && y <= 12) {
            dateString = x + "-" + "10" + "-" + "01";
        }
        return dateString;
    }

    /**
     * 功能：得到当前季度季末 格式为：xxxx-yy-zz (eg: 2007-12-31)<br>
     *
     * @return String
     * @author pure
     */
    public String thisSeasonEnd() {
        String dateString = "";
        x = localTime.get(Calendar.YEAR);
        y = localTime.get(Calendar.MONTH) + 1;
        if (y >= 1 && y <= 3) {
            dateString = x + "-" + "03" + "-" + "31";
        }
        if (y >= 4 && y <= 6) {
            dateString = x + "-" + "06" + "-" + "30";
        }
        if (y >= 7 && y <= 9) {
            dateString = x + "-" + "09" + "-" + "30";
        }
        if (y >= 10 && y <= 12) {
            dateString = x + "-" + "12" + "-" + "31";
        }
        return dateString;
    }

    /**
     * 功能：得到当前年份年初 格式为：xxxx-yy-zz (eg: 2007-01-01)<br>
     *
     * @return String
     * @author pure
     */
    public String thisYear() {
        x = localTime.get(Calendar.YEAR);
        return x + "-01" + "-01";
    }

    /**
     * 功能：得到当前年份年底 格式为：xxxx-yy-zz (eg: 2007-12-31)<br>
     *
     * @return String
     * @author pure
     */
    public String thisYearEnd() {
        x = localTime.get(Calendar.YEAR);
        return x + "-12" + "-31";
    }

    /**
     * 功能：判断输入年份是否为闰年<br>
     *
     * @param year
     * @return 是：true  否：false
     * @author pure
     */
    public boolean leapYear(int year) {
        boolean leap;
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) leap = true;
                else leap = false;
            } else leap = true;
        } else leap = false;
        return leap;
    }
}
