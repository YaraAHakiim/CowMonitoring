package com.example.veto.cowmonitoring;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonitorFragment extends Fragment {

    View rootView ;

    Context context ;
    LinearLayout linearLayout;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;


    public MonitorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initializeViews(inflater,container);
        prepareRecyclerView();


        return rootView ;
    }

    void initializeViews (LayoutInflater inflater, ViewGroup container)
    {
        rootView = inflater.inflate(R.layout.fragment_monitor, container, false);

        context = getContext();
        linearLayout = (LinearLayout) rootView.findViewById(R.id.linearLayout);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

    }

    void prepareRecyclerView ()
    {
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CowAdapter(context,getFarmNodes()));
    }

    List<String> getFarmNodes ()
    {
        List<String> nodes ;

        HttpGet httpGet = new HttpGet();

        SharedPreferences prefs = getContext().getSharedPreferences("Login session", MODE_PRIVATE);
        String user = prefs.getString("Current User",null);

        String farmId = new String();

        Gson gson = new Gson() ;

        User currentUser = gson.fromJson(user , User.class);


        if (currentUser != null) {
            farmId = currentUser.getFarmId();
        }

        String url = "getFarmNodes?farmId="+farmId;
        String nodesString = "";

        try {
            nodesString = httpGet.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        nodes = gson.fromJson(nodesString,new TypeToken<ArrayList<String>>(){}.getType());

        return nodes;
    }


}
