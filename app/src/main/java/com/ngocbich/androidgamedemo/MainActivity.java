package com.ngocbich.androidgamedemo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ngocbich.androidgamedemo.Activity.HelpActivity;
import com.ngocbich.androidgamedemo.Activity.PlayActivity;
import com.ngocbich.androidgamedemo.Activity.SettingActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.gamemusic);
        mediaPlayer.setVolume(100, 100);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private static final String TAG = "MainActivity";

    public void toggleMusic(View view){
        if(mediaPlayer.isPlaying()){ mediaPlayer.stop(); }
        else{
            try {
                mediaPlayer.prepare();
            }
            catch(IOException ex){
                Log.d(TAG,"Prepare failed");
            }
            finally {
                mediaPlayer.start();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    public void onResume() {
        Log.i("info", "MainActivity onResume");
        super.onResume();
        mediaPlayer.start();
    }

    public static MediaPlayer getPlayer() {
        return mediaPlayer;
    }

    //Method to start play sreen for play button
    public void showPlayScreen(View view) {
        Intent playIntent = new Intent(this, PlayActivity.class);
        startActivity(playIntent);
        GameConditions.resetCurrentScore();
    }

    public void showHelpScreen(View view) {
        Intent helpIntent = new Intent(this, HelpActivity.class);
        startActivity(helpIntent);
    }

    public void showSettingScreen(View view) {
        Intent settingIntent = new Intent(this,SettingActivity.class);
        startActivity(settingIntent);
    }
}
