package com.tm.lyw.timemanager.db;

import com.tm.lyw.timemanager.TimeType;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lyw on 2017/11/26.
 */

@Entity(nameInDb = "t_time_info")
public class TimeBean {

    @Property(nameInDb = "time")
    public String time;

    @Property(nameInDb = "type")
    public int type;

    @Property(nameInDb = "date")
    public long date;

    @Property(nameInDb = "sortIndex")
    public int sortIndex;

    public TimeBean(){

    }


    @Generated(hash = 1837487465)
    public TimeBean(String time, int type, long date, int sortIndex) {
        this.time = time;
        this.type = type;
        this.date = date;
        this.sortIndex = sortIndex;
    }


    @Override
    public String toString() {
        return "TimeBean{" +
                "time='" + time + '\'' +
                ", type=" + type +
                ", date=" + date +
                ", sortIndex=" + sortIndex +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        TimeBean timeBean= (TimeBean) obj;
        return timeBean.time.equals(time);
    }


    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSortIndex() {
        return this.sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }


    public long getDate() {
        return this.date;
    }


    public void setDate(long date) {
        this.date = date;
    }


}
