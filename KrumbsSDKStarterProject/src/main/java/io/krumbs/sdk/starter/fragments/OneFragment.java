package io.krumbs.sdk.starter.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.krumbs.sdk.starter.Adapters.CustomAdapterRecyclerView;
import io.krumbs.sdk.starter.Data.MyData;
import io.krumbs.sdk.starter.Models.DataModel;
import io.krumbs.sdk.starter.R;

/**
 * Created by SYED on 03-04-2017.
 */
public class OneFragment extends Fragment {
    static RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    static RecyclerView recyclerView;
    public static ArrayList<DataModel> data;
    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vview=inflater.inflate(R.layout.fragment_one, container, false);
        recyclerView = (RecyclerView) vview.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.versionArray[i],
                    MyData.id_[i],
                    MyData.drawableArray[i]
            ));
        }



        adapter = new CustomAdapterRecyclerView(data);
        recyclerView.setAdapter(adapter);


        return vview;

    }

}
