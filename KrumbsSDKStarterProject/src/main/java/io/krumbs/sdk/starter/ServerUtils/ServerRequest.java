package io.krumbs.sdk.starter.ServerUtils;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

import io.krumbs.sdk.starter.Activitys.Dishfeatures;
import io.krumbs.sdk.starter.Models.User;
import io.krumbs.sdk.starter.Preferences.LoginPreferences;
import io.krumbs.sdk.starter.Preferences.UrlPreferences;

/**
 * Created by SYED on 15-02-2017.
 */
public class ServerRequest {
    public LoginPreferences username;
    String localImagePath = "";

    HttpClientWrapper clientWrapper;
    public  ServerRequest(){
        clientWrapper = new HttpClientWrapper();
    }

    public JSONObject userLogin(Context context, String email, String password){
        JSONObject jsonObject = null;
        try {
            JSONObject request = new JSONObject();
            request.put("email", email);
            request.put("password", password);
            Log.e("JSON-REQUEST", request.toString());
            String response = clientWrapper.doPostRequest(Urls.BASE_URL+Urls.LOGIN_URL,request.toString());
            jsonObject = new JSONObject(response);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject userRegistration(Context context,String name, String email, String password,String gender,Integer age){
        JSONObject jsonObject = null;
        try {
            JSONObject request = new JSONObject();
            request.put("name",name);
            request.put("email", email);
            request.put("password", password);
            request.put("gender", gender );
            request.put("age", age);
            Log.e("JSON-REQUEST",request.toString());
            String response = clientWrapper.doPostRequest(Urls.BASE_URL+Urls.REGISTER_URL,request.toString());
            jsonObject = new JSONObject(response);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject uploadImageURL(Context context,String url){
        JSONObject jsonObject = null;
        try {
            JSONObject request = new JSONObject();
            request.put("url", url);
            Log.e("JSON-REQUEST", request.toString());
            String response = clientWrapper.doPostRequest(Urls.BASE_URL+Urls.UPLOAD_IMAGE_URL,request.toString());
            jsonObject = new JSONObject(response);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONArray loadDayData(Context context,String date) {
        username=new LoginPreferences(context);
        User user = username.getUser();
        JSONArray jsonArray = null;
        try {
            JSONObject request = new JSONObject();
            request.put("date", date);
            request.put("username", user.getEmail());
            Log.e("JSON-REQUEST", request.toString());
            String response = clientWrapper.doPostRequest(Urls.BASE_URL+Urls.DateData,request.toString());
            jsonArray = new JSONArray(response);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONArray loadFiveUpload(Context context,String date) {
        username=new LoginPreferences(context);
        User user = username.getUser();
        JSONArray jsonArray = null;
        try {
            JSONObject request = new JSONObject();
            request.put("date", date);
            request.put("username", user.getEmail());
            Log.e("JSON-REQUEST", request.toString());
            String response = clientWrapper.doPostRequest(Urls.BASE_URL+Urls.LastFiveUploads,request.toString());
            jsonArray = new JSONArray(response);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    public JSONObject ExtractDishFeatures(Dishfeatures dishfeatures, String dishname) {
        String response="";
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject request = new JSONObject();
            request.put("dishname", dishname);
            Log.e("JSON-REQUEST", request.toString());
            response = clientWrapper.doPostRequest(Urls.BASE_URL+Urls.FEATURES,request.toString());
            jsonObject = new JSONObject(response);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
}
