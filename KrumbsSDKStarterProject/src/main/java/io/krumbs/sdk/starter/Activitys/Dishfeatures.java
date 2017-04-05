package io.krumbs.sdk.starter.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import io.krumbs.sdk.starter.R;

import static io.krumbs.sdk.starter.R.id.buttontomain;

/**
 * Created by SYED on 04-04-2017.
 */
public class Dishfeatures extends AppCompatActivity{
    Button mainclass;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dishfeature);
        mainclass = (Button) findViewById(buttontomain);

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
    }
}
