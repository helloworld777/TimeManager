package com.tm.lyw.timemanager;

/**
 * Created by lyw on 2017/12/10.
 */

public class TestJava {
    public static void main(String[] args){
        long time=20171210;
        int year=(int) time/10000;
        int month=(int)time/10000%100;
        int day=(int)time%100;
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
    }
}
