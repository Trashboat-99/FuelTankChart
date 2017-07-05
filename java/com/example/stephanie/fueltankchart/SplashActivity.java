package com.example.stephanie.fueltankchart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //create a task timer to invoke a scheduled timer
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                finish();
                //launch main activity
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        };
        //instance of the timer class set for 5 seconds.
        //the splash screen will end after 5 seconds.
        Timer opening = new Timer();
        opening.schedule(task,5000);
    }
}
