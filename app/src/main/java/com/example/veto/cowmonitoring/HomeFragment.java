package com.example.veto.cowmonitoring;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.veto.cowmonitoring.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {

    View rootView;
    TextView textUserName ;
    TextView textFarmName ;
    PieChart pieChart ;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initializeViews(inflater,container);

        prepareChart();


        return rootView;
    }

    void initializeViews(LayoutInflater inflater, ViewGroup container)
    {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        pieChart = (PieChart) rootView.findViewById(R.id.chart);

        textUserName = (TextView) rootView.findViewById(R.id.textUserName);
        textFarmName = (TextView) rootView.findViewById(R.id.textFarmName);

        SharedPreferences preferences = getContext().getSharedPreferences("Login session",0);
        String user = preferences.getString("Current User", null) ;

        Gson gson = new Gson();

        User currentUser = gson.fromJson(user,User.class);
        textUserName.setText("إسم المستخدم: "+currentUser.getName());

        Farm farm = getFarm();
        textFarmName.setText("إسم المزرعة: " + farm.getFarmName());


    }

    Farm getFarm ()
    {
        SharedPreferences prefs = getContext().getSharedPreferences("Login session", MODE_PRIVATE);
        String user = prefs.getString("Current User",null);

        Gson gson = new Gson();
        User currentUser = gson.fromJson(user, User.class) ;

        String url = "getUserFarm?farmId="+ currentUser.getFarmId();
        HttpGet httpGet = new HttpGet();

        String farm = new String();

        try {
            farm = httpGet.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Farm userFarm = gson.fromJson(farm,Farm.class);
        return userFarm;
    }

    Status getStatus ()
    {
        String url = "getStatus?id=1";
        String statusString = "";

        HttpGet httpGet = new HttpGet();
        try {
            statusString = httpGet.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        Gson gson = new Gson();

        Status status = gson.fromJson(statusString, Status.class);

        Log.d("status", statusString);

        return status;
    }

    void prepareChart()
    {
        Status status = getStatus();
        ArrayList<Entry> entries = new ArrayList<>();


        //if(status != null) {
            entries.add(new Entry(Float.parseFloat(status.getNormal()), 0));
            entries.add(new Entry(Float.parseFloat(status.getAbNormal()), 1));

            PieDataSet dataset = new PieDataSet(entries, "حالة الحيوان");

            ArrayList<String> labels = new ArrayList<String>();
            labels.add("سليم");
            labels.add("مريض");

            PieData data = new PieData(labels, dataset);
            dataset.setColors(new int[
                    ]{ContextCompat.getColor(getContext() ,R.color.green)
                    ,ContextCompat.getColor(getContext() ,R.color.red)});
            pieChart.setDescription("");
            pieChart.setData(data);
            pieChart.setHoleRadius(0);

            pieChart.animateY(5000);
        //}

    }

}