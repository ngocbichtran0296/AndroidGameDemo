package com.ngocbich.androidgamedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Ngoc Bich on 5/7/2018.
 */

public class BitmapImages {
    private int spriteSize,buttonSizeHeight, buttonSizeWidth;
    private Bitmap[] playerRight, playerDown, playerLeft, playerUp;
    private Bitmap enemyBitmap0, enemyBitmap1;

    private Bitmap muteBitmap, pauseBitmap;     //button mute music and pause


    public BitmapImages(int blockSize, Context context){
        spriteSize = blockSize;

        buttonSizeHeight = blockSize*2;
        buttonSizeWidth = blockSize*4;

        // Add bitmap images of player facing right
        playerRight = new Bitmap[4];
        playerRight[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(),R.drawable.player_right1), spriteSize, spriteSize, false);
        playerRight[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player_right2), spriteSize, spriteSize, false);
        playerRight[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player_right3), spriteSize, spriteSize, false);
        playerRight[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player_right), spriteSize, spriteSize, false);


        // Add bitmap images of player facing down
        playerDown = new Bitmap[4];
        playerDown[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player_down1), spriteSize, spriteSize, false);
        playerDown[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player_down2), spriteSize, spriteSize, false);
        playerDown[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player_down3), spriteSize, spriteSize, false);
        playerDown[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player_down), spriteSize, spriteSize, false);


        // Add bitmap images of player facing left
        playerLeft = new Bitmap[4];
        playerLeft[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player_left1), spriteSize, spriteSize, false);
        playerLeft[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player_left2), spriteSize, spriteSize, false);
        playerLeft[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player_left3), spriteSize, spriteSize, false);
        playerLeft[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player_left), spriteSize, spriteSize, false);


        // Add bitmap images of player facing up
        playerUp = new Bitmap[4];
        playerUp[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player_up1), spriteSize, spriteSize, false);
        playerUp[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player_up2), spriteSize, spriteSize, false);
        playerUp[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player_up3), spriteSize, spriteSize, false);
        playerUp[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.player_up), spriteSize, spriteSize, false);


        // Add bitmap image of mute
        muteBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.mute), buttonSizeWidth, buttonSizeHeight, false);

        // Add bitmap image of pause
        pauseBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.pause), buttonSizeWidth, buttonSizeHeight, false);


        // Add bitmap image of ghost
        enemyBitmap0 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.enemy0), spriteSize, spriteSize, false);

        // Add bitmap image of ghost
        enemyBitmap1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                context.getResources(), R.drawable.enemy1), spriteSize, spriteSize, false);

    }


    public int getSpriteSize() {
        return spriteSize;
    }

    public int getButtonSizeHeight() {
        return buttonSizeHeight;
    }

    public int getButtonSizeWidth() {
        return buttonSizeWidth;
    }

    public Bitmap[] getPlayerRight() {
        return playerRight;
    }

    public Bitmap[] getPlayerDown() {
        return playerDown;
    }

    public Bitmap[] getPlayerLeft() {
        return playerLeft;
    }

    public Bitmap[] getPlayerUp() {
        return playerUp;
    }

    public Bitmap getEnemyBitmap0() {
        return enemyBitmap0;
    }

    public Bitmap getEnemyBitmap1() {
        return enemyBitmap1;
    }

    public Bitmap getMuteBitmap() {
        return muteBitmap;
    }

    public Bitmap getPauseBitmap() {
        return pauseBitmap;
    }
}
