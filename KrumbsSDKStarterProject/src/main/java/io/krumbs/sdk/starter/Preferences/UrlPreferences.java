package io.krumbs.sdk.starter.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SYED on 04-04-2017.
 */
public class UrlPreferences {
    SharedPreferences preferences;
    Context context;
    public static final String LocalImageURL = "";

    public UrlPreferences(Context context) {
        preferences = context.getSharedPreferences(LocalImageURL,context.MODE_PRIVATE);
        this.context = context;
    }

    public void SetURL(String image){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LocalImageURL,image);
        editor.apply();
    }

    public void RemoveURL(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public String GetURL(){
        String url = preferences.getString(LocalImageURL,"");
        return url;
    }
}
