package com.example.mobilesensorfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{
    Handler handler = new Handler();
    ProgressBar progressBar;
    Timer timer;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = this.findViewById(R.id.progressBar);
        timer = new Timer();
        TimerTask timerTask =new TimerTask() {
            @Override
            public void run() {
                count++;
                progressBar.setProgress(count);
                if (count == 100){
                    timer.cancel();
                }
            }
        };
        timer.schedule(timerTask,0,40);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,MainMenu.class);
                startActivity(i);
                finish();
            }
        },4000);

    }
}