package com.example.drn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drn.Pages.Evening_MedsActivity;
import com.example.drn.Pages.Morning_MedsActivity;
import com.example.drn.Pages.Night_MedsActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    private CardView mornin, evenin, night;
    private TextView mornin_status, evenin_status, night_status;
    private DatabaseHelper databaseHelper;
    private int mornin_count, evenin_count, night_count;
    private GifImageView morning_gif, evening_gif, night_gif;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mornin = findViewById(R.id.morning);
        mornin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Morning_MedsActivity.class);
                i.putExtra("message_key", "Morning");
                startActivity(i);
            }
        });

        evenin = findViewById(R.id.evening);
        evenin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Evening_MedsActivity.class);
                i.putExtra("message_key", "Evening");
                startActivity(i);
            }
        });

        night = findViewById(R.id.night);
        night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Night_MedsActivity.class);
                i.putExtra("message_key", "Night");
                startActivity(i);
            }
        });

        mornin_status = findViewById(R.id.mornin_status);
        evenin_status = findViewById(R.id.evenin_status);
        night_status = findViewById(R.id.night_status);

        databaseHelper = DatabaseHelper.getDB(MainActivity.this);

        mornin_count = databaseHelper.dao_morning().getStatusMorning();
        if(mornin_count == 0){
            mornin_status.setText("All Completed!");
            mornin_status.setTextColor(Color.parseColor("#66bb6a"));
        }
        else{
            mornin_status.setText(String.valueOf(mornin_count) + " left");
            mornin_status.setTextColor(Color.parseColor("#424242"));
        }

        evenin_count = databaseHelper.dao_evening().getStatusEvening();
        if(evenin_count == 0){
            evenin_status.setText("All Completed!");
            evenin_status.setTextColor(Color.parseColor("#66bb6a"));
        }
        else{
            evenin_status.setText(String.valueOf(evenin_count) + " left");
            evenin_status.setTextColor(Color.parseColor("#424242"));
        }

        night_count = databaseHelper.dao_night().getStatusNight();
        if(night_count == 0){
            night_status.setText("All Completed!");
            night_status.setTextColor(Color.parseColor("#66bb6a"));
        }
        else{
            night_status.setText(String.valueOf(night_count) + " left");
            night_status.setTextColor(Color.parseColor("#424242"));
        }

    }

    @Override
    public void onResume(){
        super.onResume();

        mornin_status = findViewById(R.id.mornin_status);
        evenin_status = findViewById(R.id.evenin_status);
        night_status = findViewById(R.id.night_status);

        databaseHelper = DatabaseHelper.getDB(MainActivity.this);

        mornin_count = databaseHelper.dao_morning().getStatusMorning();
        if(mornin_count == 0){
            mornin_status.setText("All Completed!");
            mornin_status.setTextColor(Color.parseColor("#66bb6a"));
        }
        else{
            mornin_status.setText(String.valueOf(mornin_count) + " left");
            mornin_status.setTextColor(Color.parseColor("#424242"));
        }

        evenin_count = databaseHelper.dao_evening().getStatusEvening();
        if(evenin_count == 0){
            evenin_status.setText("All Completed!");
            evenin_status.setTextColor(Color.parseColor("#66bb6a"));
        }
        else{
            evenin_status.setText(String.valueOf(evenin_count) + " left");
            evenin_status.setTextColor(Color.parseColor("#424242"));
        }

        night_count = databaseHelper.dao_night().getStatusNight();
        if(night_count == 0){
            night_status.setText("All Completed!");
            night_status.setTextColor(Color.parseColor("#c5e1a5"));
        }
        else{
            night_status.setText(String.valueOf(night_count) + " left");
            night_status.setTextColor(Color.parseColor("#c5cae9"));
        }


        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("H");
        String time = simpleDateFormat.format(new Date());
//        Toast.makeText(this, time, Toast.LENGTH_SHORT).show();


        //Gif Animation Start and Stop.
        morning_gif = findViewById(R.id.morning_gif);
        evening_gif = findViewById(R.id.evening_gif);
        night_gif = findViewById(R.id.night_gif);

        GifDrawable gifDrawable_morning = (GifDrawable) morning_gif.getDrawable();
        GifDrawable gifDrawable_evening = (GifDrawable) evening_gif.getDrawable();
        GifDrawable gifDrawable_night = (GifDrawable) night_gif.getDrawable();

        int time_int = Integer.parseInt(time);

        if(time_int >= 20){
            gifDrawable_morning.stop();
            gifDrawable_evening.stop();
            gifDrawable_night.start();
        }

        else if(time_int <= 5){
            gifDrawable_morning.stop();
            gifDrawable_evening.stop();
            gifDrawable_night.start();
        }

        else if(time_int >= 5 && time_int < 16){
            gifDrawable_morning.start();
            gifDrawable_evening.stop();
            gifDrawable_night.stop();
        }

        else if(time_int >= 16 && time_int < 20){
            gifDrawable_morning.stop();
            gifDrawable_evening.start();
            gifDrawable_night.stop();
        }


    }
}