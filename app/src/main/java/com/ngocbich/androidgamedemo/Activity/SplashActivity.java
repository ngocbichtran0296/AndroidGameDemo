package com.ngocbich.androidgamedemo.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ngocbich.androidgamedemo.MainActivity;
import com.ngocbich.androidgamedemo.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread mythread=new Thread(){
            @Override
            public void run() {
                try{
                    sleep(4000);

                    Intent startgame=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(startgame);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };//end thread

        mythread.start();
    }
}
