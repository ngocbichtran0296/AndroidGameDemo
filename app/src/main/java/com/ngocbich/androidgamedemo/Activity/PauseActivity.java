package com.ngocbich.androidgamedemo.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ngocbich.androidgamedemo.GameConditions;
import com.ngocbich.androidgamedemo.MainActivity;
import com.ngocbich.androidgamedemo.R;

public class PauseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause);
        MainActivity.getPlayer().start();
    }


    // method for Resume button
    public void resumeGame(View view) {
        Intent resumeIntent = new Intent(this, PlayActivity.class);
        resumeIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(resumeIntent);
        this.finish();
    }

    // method for Play new game button
    public void showPlayScreen(View view) {
        Intent playIntent = new Intent(this, PlayActivity.class);
        startActivity(playIntent);
        PlayActivity.getInstance().finish();
        this.finish();
        GameConditions.resetCurrentScore();
    }

    //method for How to play button
    public void showHelpScreen(View view) {
        Intent helpIntent = new Intent(this, HelpActivity.class);
        startActivity(helpIntent);
    }
}
