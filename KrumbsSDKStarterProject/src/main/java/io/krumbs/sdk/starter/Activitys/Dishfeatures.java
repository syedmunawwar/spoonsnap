package io.krumbs.sdk.starter.Activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import io.krumbs.sdk.starter.R;
import io.krumbs.sdk.starter.ServerUtils.ServerRequest;
import io.krumbs.sdk.starter.Utils.DialogUtils;

import static io.krumbs.sdk.starter.R.id.buttontomain;

/**
 * Created by SYED on 04-04-2017.
 */
public class Dishfeatures extends AppCompatActivity {
    Button mainclass;
    private TabLayout tabLayout;
    private ViewPager viewPager;
   // TextView Dish_Features_text;
    TextView Feature_Dish_name;
    TextView Feature_Type;
    TextView Feature_Ingredients;
    TextView Feature_carbs;
    TextView Feature_proteins;
    TextView Feature_Fat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dishfeature);
        mainclass = (Button) findViewById(buttontomain);
      //  Dish_Features_text = (TextView) findViewById(R.id.features_text);
        Feature_Dish_name=(TextView) findViewById(R.id.feature_Dish_name_Value);
        Feature_Type =(TextView)findViewById(R.id.feature_Type_Value);
        Feature_Ingredients=(TextView)findViewById(R.id.feature_Ingredients_Value);
        Feature_carbs=(TextView) findViewById(R.id.feature_carbs_Value);
        Feature_proteins =(TextView)findViewById(R.id.feature_proteins_Value);
        Feature_Fat=(TextView)findViewById(R.id.feature_fat_Value);
        mainclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager = (ViewPager) findViewById(R.id.viewpager);
                tabLayout = (TabLayout) findViewById(R.id.tabs);

                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        Intent intent = getIntent();
        String dishname = intent.getStringExtra("Dishname");
       ExtractFeatures(dishname);
    }

    private void ExtractFeatures(final String dishname) {
        new AsyncTask<Void, Void, JSONObject>() {
            DialogUtils progressDialog = new DialogUtils(Dishfeatures.this, DialogUtils.Type.PROGRESS_DIALOG);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.showProgressDialog("Extracting Features", "please wait..", false);
                //progress
            }

            @Override
            protected JSONObject doInBackground(Void... params) {
                return new ServerRequest().ExtractDishFeatures(Dishfeatures.this, dishname);
            }

            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                progressDialog.dismissProgressDialog();
                if (response != null) {
                    //Dish_Features_text.setText(response.toString());
                    try {
                        Feature_Dish_name.setText(response.getString("Classes").toString());
                        Feature_Type.setText(response.getString("Veg/Non Veg"));
                        Feature_Ingredients.setText(response.getString("Basic Ingredients").toString());
                        Feature_carbs.setText(response.getString("Calorie Carb(g)").toString());
                        Feature_proteins.setText(response.getString("Proteins").toString());
                        Feature_Fat.setText(response.getString("Fat").toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //Toast.makeText(Dishfeatures.this, "Hello" + response.toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Dishfeatures.this, "oops!..something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(Dishfeatures.this, "Upload data Canceled", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Dishfeatures.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
