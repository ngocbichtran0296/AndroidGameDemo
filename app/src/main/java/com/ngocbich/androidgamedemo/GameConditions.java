package com.ngocbich.androidgamedemo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ngocbich.androidgamedemo.Activity.CompletedLevelActivity;

/**
 * Created by Ngoc Bich on 5/7/2018.
 */

public class GameConditions {
    private static GameConditions instance;
    private int currentScore = 0;           //Current game score
    private int numOfDots;           //Total number of fruits remaining

    private GameConditions() {}

    public void updateCurrentScore()   {
        currentScore += 10;
    }

    public void setCurrentScore(int newScore)   {
        currentScore = newScore;
    }

    public void updateNumOfDots() {
        numOfDots--;
    }

    public void setNumOfDots(int newNum) {
        numOfDots =newNum;
    }

    public int getNumOfDots()    {
        return numOfDots;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public static synchronized GameConditions getInstance(){
        if(instance==null){
            instance=new GameConditions();
        }
        return instance;
    }

    //counts the number of fruits at the start of the game
    public static void countFruist(short[][] currentMap) {
        GameConditions gc = GameConditions.getInstance();
        gc.setNumOfDots(0);
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 17; j++) {
                if ((currentMap[i][j] & 16) != 0) {
                    //increases the total number of fruits
                    gc.setNumOfDots(gc.getNumOfDots() + 1);;

                    Log.i("info", "Pellets = " + Integer.toString(gc.getNumOfDots()));
                }
            }
        }
    }

    public static void checkVictory(Context context) {
        GameConditions gc = GameConditions.getInstance();
        if (gc.getNumOfDots() == 0) {
            Log.i("info", "Level completed - GameOver");

            //go to next level
            Globals.nextLevel();

            Intent completedIntent = new Intent(context, CompletedLevelActivity.class);
            context.startActivity(completedIntent);
        }
    }

    public static void resetCurrentScore()   {
        GameConditions gc = GameConditions.getInstance();
        gc.setCurrentScore(0);
    }

}
