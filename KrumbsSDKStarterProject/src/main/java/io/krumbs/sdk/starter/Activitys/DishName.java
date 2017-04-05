package io.krumbs.sdk.starter.Activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;

import io.krumbs.sdk.krumbscapture.KCaptureCompleteListener;
import io.krumbs.sdk.starter.Models.User;
import io.krumbs.sdk.starter.Parsers.UserParser;
import io.krumbs.sdk.starter.Preferences.UrlPreferences;
import io.krumbs.sdk.starter.R;
import io.krumbs.sdk.starter.ServerUtils.ServerRequest;
import io.krumbs.sdk.starter.Utils.DialogUtils;

import static io.krumbs.sdk.starter.R.id.DishNameNext;

/**
 * Created by SYED on 03-04-2017.
 */
public class DishName extends AppCompatActivity {
    private EventBus bus=EventBus.getDefault();
    public UrlPreferences localImageurl;
    String localImagePath = "";
    ImageButton next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dish_name);
        next = (ImageButton) findViewById(DishNameNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        Dishfeatures.class);
                startActivity(i);
                finish();
            }
        });
        Intent intent = getIntent();
        String url = intent.getStringExtra("imageUrl");
        localImageurl=new UrlPreferences(DishName.this);
        localImagePath=localImageurl.GetURL();
        File imgFile = new File(localImagePath);
        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView myImage = (ImageView) findViewById(R.id.imageViewDishName);

            myImage.setImageBitmap(myBitmap);

        }
        uploadImage(url);
    }

    private void uploadImage(final String url) {
        new AsyncTask<Void, Void, JSONObject>(){
            DialogUtils progressDialog = new DialogUtils(DishName.this,DialogUtils.Type.PROGRESS_DIALOG);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.showProgressDialog("Login", "please wait..", false);
                //progress
            }

            @Override
            protected JSONObject doInBackground(Void... params) {
                return new ServerRequest().uploadImageURL(DishName.this, url);
            }

            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                progressDialog.dismissProgressDialog();
                if (response != null) {
                    Toast.makeText(DishName.this,"Hello" +response.toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DishName.this, "oops!..something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String image){
        //do whatever you want to do with the object or its attributes
        localImagePath=image;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }
}
