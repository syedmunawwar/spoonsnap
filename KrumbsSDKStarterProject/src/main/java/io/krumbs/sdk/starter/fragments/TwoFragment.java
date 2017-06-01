package io.krumbs.sdk.starter.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import io.krumbs.sdk.starter.Adapters.CustomAdapterRecyclerView;
import io.krumbs.sdk.starter.Models.DataModel;
import io.krumbs.sdk.starter.R;
import io.krumbs.sdk.starter.ServerUtils.ServerRequest;
import io.krumbs.sdk.starter.Utils.DialogUtils;

/**
 * Created by SYED on 03-04-2017.
 */
public class TwoFragment extends Fragment {

    public TwoFragment() {
        // Required empty public constructor
    }

    static RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    static RecyclerView recyclerView;
    public static ArrayList<DataModel> data;
    TextView Week_text;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vview=inflater.inflate(R.layout.fragment_two, container, false);
        recyclerView = (RecyclerView) vview.findViewById(R.id.my_recycler_view);
        Week_text =(TextView)vview.findViewById(R.id.Week_text);
        getDayData();

        return vview;

    }

    private void getDayData() {
        new AsyncTask<Void, Void, JSONArray>(){

            //DialogUtils dialogUtils = new DialogUtils(getActivity(),DialogUtils.Type.PROGRESS_DIALOG);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               // dialogUtils.showProgressDialog("Loading Data","please wait...",false);
            }

            @Override
            protected JSONArray doInBackground(Void... params) {
                return new ServerRequest().loadDayData(getContext(),getWeekData());
            }

            @Override
            protected void onPostExecute(JSONArray response) {
                super.onPostExecute(response);
              //  dialogUtils.dismissProgressDialog();
                if (response != null) {
                    data = new ArrayList<DataModel>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject object = response.getJSONObject(i);
                            data.add(new DataModel(
                                    object.getString("restaurant_name"),
                                    object.getString("id"),
                                    object.getString("tastiness"),
                                    object.getString("imgurl")
                            ));
                        }
                        adapter = new CustomAdapterRecyclerView(getContext(),data);
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Week_text.setText("Amazing!! No Reports Were Submitted in Past week");
                    Log.e("DateData","FAILED");
                }
            }
        }.execute();
    }

    public String getWeekData(){
        int x = -7;
        Calendar cal = GregorianCalendar.getInstance();
        cal.add( Calendar.DAY_OF_YEAR, x);
        Date tenDaysAgo = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String weekDate = df.format(tenDaysAgo);
        Log.e("Todays Date",weekDate);
        return weekDate;
    }
}
