package io.krumbs.sdk.starter.Activitys;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.ArrayList;
import java.util.Locale;

import io.krumbs.sdk.krumbscapture.KCaptureCompleteListener;
import io.krumbs.sdk.starter.Adapters.TimeLineTabAdapter;
import io.krumbs.sdk.starter.Models.User;
import io.krumbs.sdk.starter.Parsers.UserParser;
import io.krumbs.sdk.starter.Preferences.UrlPreferences;
import io.krumbs.sdk.starter.R;
import io.krumbs.sdk.starter.ServerUtils.ServerRequest;
import io.krumbs.sdk.starter.Utils.DialogUtils;
import io.krumbs.sdk.starter.fragments.OneFragment;
import io.krumbs.sdk.starter.fragments.ThreeFragment;
import io.krumbs.sdk.starter.fragments.TwoFragment;

import static io.krumbs.sdk.starter.R.id.DishNameNext;

/**
 * Created by SYED on 03-04-2017.
 */
public class DishName extends AppCompatActivity {
    private EditText resultTEXT;



    public UrlPreferences localImageurl;
    String localImagePath = "";
    ImageButton next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dish_name);
        next = (ImageButton) findViewById(DishNameNext);
        resultTEXT = (EditText)findViewById(R.id.EditDishName);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Dishfeatures.class);
                i.putExtra("Dishname", resultTEXT.getText().toString());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

        //uploadImage(url);
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
                   // Toast.makeText(DishName.this,"Hello" +response.toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DishName.this, "oops!..something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    public void onButtonClick(View v){
        if(v.getId() == R.id.imageButton2){
            promptSpeechInput();
        }
    }

    public void promptSpeechInput(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something!");

        try {
            startActivityForResult(i, 100);
        }
        catch (ActivityNotFoundException a){
            Toast.makeText(DishName.this, "Sorry your device doesn't support this", Toast.LENGTH_LONG).show();
        }

    }

    public void onActivityResult(int request_code, int result_code, Intent i){
        super.onActivityResult(request_code, result_code, i);

        switch (request_code){

            case 100: if(result_code == RESULT_OK && i!= null){
                ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                resultTEXT.setText(result.get(0));
            }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(DishName.this, "Upload data Canceled", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(DishName.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
