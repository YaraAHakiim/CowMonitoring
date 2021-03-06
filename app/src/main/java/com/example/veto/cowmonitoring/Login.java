package com.example.veto.cowmonitoring;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity implements View.OnClickListener {


    AppCompatButton btnLogin;
    EditText editTextEmail;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();


        initializeViews();
        btnLogin.setOnClickListener(this);

        SharedPreferences prefs = this.getSharedPreferences("Login session", MODE_PRIVATE);
        String user = prefs.getString("Current User",null);

        if(user != null)
        {
            Intent intent = new Intent(getApplicationContext() , Home.class);
            startActivity(intent);
            finish();
        }
    }

    public void initializeViews() {
        btnLogin = (AppCompatButton) findViewById(R.id.btnLogin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
    }



    boolean validate() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        boolean isValid = true;

        if (email.isEmpty()) {
            editTextEmail.setError("إدخل إسم المستخدم");
            isValid = false;
        }
        if (password.isEmpty())
        {
            editTextPassword.setError("إدخل كلمة المرور");
            isValid = false ;
        }

            return isValid;
    }

    String login() {

        if (validate()) {

            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String result = null;


            String url = "checkLogin?userName=" +
                    email + "&password=" + password;
            HttpGet httpGet = new HttpGet();

            try {
                result = httpGet.execute(url).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            Log.d("res", result);

            return result;
        }

        return null;

    }

    void createUserSession(String result)
    {
        SharedPreferences preferences =  getApplicationContext().getSharedPreferences("Login session" , 0);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("Current User" , result);

        Gson gson = new Gson();
        User currentUser = gson.fromJson(result, User.class) ;

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
        editor.putString("Farm" , farm);
        editor.commit();
    }

    @Override
    public void onClick(View v) {

        String currentUser = login();

        if(currentUser == null){

        }

        else if(currentUser.equals("{}"))
        {
            Toast.makeText(getApplicationContext(), "خطأ فى إسم المستخدم  او كلمة المرور", Toast.LENGTH_LONG).show();
        }
        else {

            createUserSession(currentUser);

            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    }
}
