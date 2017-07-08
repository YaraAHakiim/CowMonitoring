package com.example.veto.cowmonitoring;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by YaRa on 6/30/2017.
 */

public class HttpGet extends AsyncTask <String , String , String>  {

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;

    @Override
    protected String doInBackground(String... params) {

        String urlString = params[0] ;
        String result = "" ;
        String line ;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        InputStreamReader inputStreamReader = null ;

        try {
            URL url = new URL(urlString);

            urlConnection = (HttpURLConnection) url.openConnection() ;

            urlConnection.setRequestMethod(REQUEST_METHOD);
            urlConnection.setReadTimeout(READ_TIMEOUT);
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);

            urlConnection.connect();

            inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
            reader = new BufferedReader(inputStreamReader) ;
            StringBuilder stringBuilder = new StringBuilder();

            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            result = stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();

                try {
                    if(inputStreamReader != null)
                        inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            try {
                if (reader != null)
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
