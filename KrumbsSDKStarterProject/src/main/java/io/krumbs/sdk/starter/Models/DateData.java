package io.krumbs.sdk.starter.Models;

/**
 * Created by SYED on 19-04-2017.
 */

public class DateData {
    String dishname= "",time = "", type = "";
    public DateData(String dishname, String time, String type) {
        this.dishname = dishname;
        this.time = time;
        this.type = type;

    }


    public String getDishname() {
        return dishname;
    }


    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }


}
