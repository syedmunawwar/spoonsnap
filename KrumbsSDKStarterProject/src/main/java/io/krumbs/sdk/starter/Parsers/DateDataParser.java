package io.krumbs.sdk.starter.Parsers;

import org.json.JSONObject;

import io.krumbs.sdk.starter.Models.DateData;


/**
 * Created by SYED on 18-04-2017.
 */

public class DateDataParser {

    public DateData parse(JSONObject object){
        DateData data = null;

        String dishname= "",time = "", type = "";



        try {

            if (object.has("dishname")){
                dishname = object.getString("dishname");
            }
            if (object.has("time")){
                time = object.getString("time");
            }
            if (object.has("type")){
                type = object.getString("type");
            }



            data = new DateData(dishname,time,type);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }


}
