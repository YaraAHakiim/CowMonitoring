package com.example.veto.cowmonitoring;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by YaRa on 7/14/2017.
 */

public class NotificationsService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class getNotifications extends HttpGet{

        void doiInBackground()
        {
            HttpGet httpGet = new HttpGet();

        }
    }
}
