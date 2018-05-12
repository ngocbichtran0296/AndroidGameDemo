package com.ngocbich.androidgamedemo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ngocbich.androidgamedemo.MainActivity;
import com.ngocbich.androidgamedemo.R;

import java.io.IOException;


public class CompletedLevelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_level);

        MainActivity.getPlayer().start();
    }

    // Method to start activity for Help button
    public void showHelpScreen(View view) {
        Intent helpIntent = new Intent(this, HelpActivity.class);
        startActivity(helpIntent);
    }

    //Method to start activity for Settings button
    public void showSettingsScreen(View view){
        Intent settingIntent = new Intent(this,SettingActivity.class);
        startActivity(settingIntent);
    }

    // Method to start activity for Play button
    public void showPlayScreen(View view) {
        Intent playIntent = new Intent(this, PlayActivity.class);
        startActivity(playIntent);
        PlayActivity.getInstance().finish();
        this.finish();
    }

    private static final String TAG = "SettingsActivity";
    public void playMusic(View view){
        if(MainActivity.getPlayer().isPlaying()){ MainActivity.getPlayer().stop(); }
        else{
            try {
                MainActivity.getPlayer().prepare();
            }
            catch(IOException ex){
                Log.d(TAG,"Prepare failed");
            }
            finally {
                MainActivity.getPlayer().start();
            }
        }
    }
}
