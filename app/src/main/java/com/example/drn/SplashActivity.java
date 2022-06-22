package com.example.drn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SplashActivity extends AppCompatActivity {

    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat simpleDateFormat1;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        databaseHelper = DatabaseHelper.getDB(SplashActivity.this);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        SharedPreferences pref =  getSharedPreferences("com.example.drn.reset", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        boolean firstRun = pref.getBoolean("firstRun", true);
        if(firstRun) {
//            Toast.makeText(this, "First Time", Toast.LENGTH_LONG).show();
            editor.putString("date", simpleDateFormat.format(new Date()));
            editor.putBoolean("firstRun",false);
            editor.commit();
        }
        else {
            String date = pref.getString("date", "0");
            if(!date.equals(simpleDateFormat.format(new Date()))){
                databaseHelper.dao_morning().resetStatusMorning();
                databaseHelper.dao_evening().resetStatusEvening();
                databaseHelper.dao_night().resetStatusNight();
                editor.putString("date", simpleDateFormat.format(new Date()));
                editor.apply();
//                Toast.makeText(this, "Date Changed", Toast.LENGTH_SHORT).show();

            }
//            Toast.makeText(this, "Second Time", Toast.LENGTH_LONG).show();
        }


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this , MainActivity.class);
                startActivity(i);
                finish();
            }
        } , 1000);
    }
}