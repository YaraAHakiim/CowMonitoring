package com.example.veto.cowmonitoring;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.gson.Gson;

import org.json.*;

import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;


public class FarmProfileFragement extends Fragment {

    View rootView ;
    TextView farmName;
    TextView farmAddress;
    TextView farmPhone;
    TextView farmManager;
    TextView managerPhone;


    public FarmProfileFragement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initializeViews(inflater,container);
        fillData();


        // Inflate the layout for this fragment
        return rootView ;
    }

    void initializeViews(LayoutInflater inflater, ViewGroup container)
    {
        rootView = inflater.inflate(R.layout.fragment_farmprofile, container, false);
        farmName = (TextView) rootView.findViewById(R.id.textFarmName);
        farmAddress = (TextView) rootView.findViewById(R.id.textFarmAddress);
        farmPhone = (TextView) rootView.findViewById(R.id.textFarmPhone);
        farmManager = (TextView) rootView.findViewById(R.id.textFarmManager);
        managerPhone= (TextView) rootView.findViewById(R.id.textManagerPhone);
    }

    String getFarmId()
    {
        SharedPreferences prefs = getContext().getSharedPreferences("Login session", MODE_PRIVATE);
        String user = prefs.getString("Current User",null);

        String farmId = new String();

        Gson gson = new Gson() ;

        User currentUser = gson.fromJson(user , User.class);


        if (currentUser != null) {
            farmId = currentUser.getFarmId();
        }
        return farmId ;
    }

    Farm getFarm ()
    {
        String farmId = getFarmId();

        String url = "getUserFarm?farmId="+farmId;
        HttpGet httpGet = new HttpGet();
        String farm = new String();

        try {
            farm = httpGet.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        return gson.fromJson(farm,Farm.class);
    }

    void fillData ()
    {
        Farm farm  = getFarm();
        farmName.setText("مزرعة "+farm.getFarmName());
        farmAddress.setText(farm.getFarmAddress());

        HttpGet httpGet = new HttpGet();
        //TODO complete info
        //String url = ""
    }
}