package com.tm.lyw.timemanager;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * Created by lyw on 2017/11/26.
 */

public class DateUtil {


//    public static Date getToday(){
//
//
//        Calendar calendar=Calendar.getInstance();
//        calendar.set(Calendar.HOUR,0);
//        calendar.set(Calendar.MINUTE,0);
//        calendar.set(Calendar.SECOND,0);
////        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        Log.d("DateUtil",formatter.format(calendar.getTime()));
//        return calendar.getTime();
//    }
    public static int dayOfMonth(int month,int year){
        int[] monthDay = {31,28,31,30,31,30,31,31,30,31,30,31};
        if((year%4==0 && year%100 != 0)|| year%400==0 )
            monthDay[1]++;
        return monthDay[month];
    }
    public static int getCurrentYear(){
        Calendar calendar=Calendar.getInstance();
       return calendar.get(Calendar.YEAR);
    }
    public static int getCurrentMonth(){
        Calendar calendar=Calendar.getInstance();
       return calendar.get(Calendar.MONTH)+1;
    }
    public static long getToday(){


        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
        Log.d("DateUtil",formatter.format(calendar.getTime()));
        return Long.valueOf(formatter.format(calendar.getTime()));
    }
}
