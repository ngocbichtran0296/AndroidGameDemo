package com.ngocbich.androidgamedemo.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ngocbich.androidgamedemo.MainActivity;
import com.ngocbich.androidgamedemo.R;

public class FailedLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failed_level);
        MainActivity.getPlayer().start();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.gameOver).setNeutralButton(R.string.ok, new AlertDialog.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Intent failedIntent = new Intent(PlayActivity.getInstance(), MainActivity.class);
                PlayActivity.getInstance().startActivity(failedIntent);

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void showPlayScreen(View view) {
        Intent playIntent = new Intent(this, PlayActivity.class);
        startActivity(playIntent);
        PlayActivity.getInstance().finish();
        MainActivity.getPlayer().stop(); //*
        this.finish();
    }

    public void showHelpScreen(View view) {
        Intent helpIntent = new Intent(this, HelpActivity.class);
        startActivity(helpIntent);
    }
}
