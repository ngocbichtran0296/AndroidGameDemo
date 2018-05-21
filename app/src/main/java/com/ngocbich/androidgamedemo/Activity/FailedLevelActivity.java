package com.ngocbich.androidgamedemo.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ngocbich.androidgamedemo.GameConditions;
import com.ngocbich.androidgamedemo.MainActivity;
import com.ngocbich.androidgamedemo.R;

public class FailedLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failed_level);
       // MainActivity.getPlayer().start();

        TextView score=findViewById(R.id.score);
        score.setText("Your score: "+GameConditions.currentScore);

    }

    public void showPlayScreen(View view) {
        Intent playIntent = new Intent(this, MainActivity.class);
        startActivity(playIntent);
        PlayActivity.getInstance().finish();
        MainActivity.getPlayer().stop(); //*
        this.finish();
    }

}
