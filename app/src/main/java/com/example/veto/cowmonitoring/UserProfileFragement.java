package com.example.veto.cowmonitoring;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;


public class UserProfileFragement extends Fragment {

    View rootView;
    TextView userName;
    TextView userPhone;
    TextView userEmail;
    TextView userGender;
    TextView userFarm;
    TextView userPosition;


    public UserProfileFragement() {
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
        rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        userName = (TextView) rootView.findViewById(R.id.textUserName);
        userEmail = (TextView) rootView.findViewById(R.id.textUserEmail);
        userPhone = (TextView) rootView.findViewById(R.id.textUserPhone);
        userGender = (TextView) rootView.findViewById(R.id.textUserGender);
        userFarm = (TextView) rootView.findViewById(R.id.textUserFarm);
        userPosition = (TextView) rootView.findViewById(R.id.textUserPosition);
    }

    void fillData()
    {
        SharedPreferences prefs = getContext().getSharedPreferences("Login session", MODE_PRIVATE);
        String user = prefs.getString("Current User",null);

        Gson gson = new Gson();
        User currentUser = gson.fromJson(user, User.class) ;

        userName.setText(currentUser.getName());
        userEmail.setText(currentUser.getEmail());
        userPhone.setText(currentUser.getPhoneNumber());

        if(currentUser.getGenderId().equals("Male"))
            userGender.setText("ذكر");
        else
            userGender.setText("أنثى");

        if(currentUser.getJobId().equals("1"))
            userPosition.setText("الوظيفة: مدير المزرعة");
        else
            userPosition.setText("الوظيفة: مزارع");


        Farm farm =getFarm();
        userFarm.setText("المزرعة"+farm.getFarmName());
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


}
