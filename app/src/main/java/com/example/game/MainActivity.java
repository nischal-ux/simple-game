package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
private  Flyingfishview gameview;

private Handler handler=new Handler();
private final static  long interval=30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      gameview=new Flyingfishview(this);
      setContentView(gameview);


      getSupportActionBar().hide();
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameview.invalidate();
                    }
                });
            }
        },0,interval );
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(getApplicationContext(),myservice.class));
        super.onDestroy();
    }
}
