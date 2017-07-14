package com.example.veto.cowmonitoring;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by YaRa on 7/14/2017.
 */

public class NotificationsService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    void getTemp(ArrayList<String> nodeKeys)
        {
            HttpGet httpGet = new HttpGet();

        }

}
