package com.ngocbich.androidgamedemo.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ngocbich.androidgamedemo.MainActivity;
import com.ngocbich.androidgamedemo.R;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        MainActivity.getPlayer().start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.getPlayer().pause();
    }
}
