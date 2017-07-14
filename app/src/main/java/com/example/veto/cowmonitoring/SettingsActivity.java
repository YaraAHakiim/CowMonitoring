package com.example.veto.cowmonitoring;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    TextView fontSelected, notifState;
    AlertDialog langDialog;
    RadioButton smallFontChosen, largeFontChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setTitle("الإعدادات");


        fontSelected = (TextView) findViewById(R.id.txtView_font_selected);
        notifState = (TextView) findViewById(R.id.txtView_notif_state);

    }

    public void chooseFont(View v){
        AlertDialog.Builder langDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
        View langView = getLayoutInflater().inflate(R.layout.font_sizes, null);
        langDialogBuilder.setView(langView);

        langDialog = langDialogBuilder.create();
        langDialog.show();

    }

    public void smallFontChosen(View v){
        if(!fontSelected.equals("صغير")){
            fontSelected.setText("صغير");
        }

        langDialog.dismiss();
    }


    public void largeFontChosen(View v){
        if(!fontSelected.equals("كبير")){
            fontSelected.setText("كبير");
        }

        langDialog.dismiss();

    }

    public void chooseNotif(View v){
        AlertDialog.Builder langDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
        View langView = getLayoutInflater().inflate(R.layout.on_off_notif, null);
        langDialogBuilder.setView(langView);

        langDialog = langDialogBuilder.create();
        langDialog.show();
    }

    public void notifOn(View v){
        if(!notifState.equals("تشغيل")){
            fontSelected.setText("تشغيل");
        }

        langDialog.dismiss();
    }

    public void notifOff(View v){
        if(!notifState.equals("إيقاف")){
            fontSelected.setText("إيقاف");
        }

        langDialog.dismiss();
    }

    public void chooseVibrate(View v){
        AlertDialog.Builder langDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
        View langView = getLayoutInflater().inflate(R.layout.on_off_vibrate, null);
        langDialogBuilder.setView(langView);

        langDialog = langDialogBuilder.create();
        langDialog.show();

    }

    public void vibrateOn(View v){
        if(!notifState.equals("تشغيل")){
            fontSelected.setText("تشغيل");
        }

        langDialog.dismiss();
    }

    public void vibrateOff(View v){
        if(!notifState.equals("إيقاف")){
            fontSelected.setText("إيقاف");
        }

        langDialog.dismiss();
    }

    public void chooseTone(View v){
        AlertDialog.Builder langDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
        View langView = getLayoutInflater().inflate(R.layout.tones, null);
        langDialogBuilder.setView(langView);

        langDialog = langDialogBuilder.create();
        langDialog.show();

    }


}



