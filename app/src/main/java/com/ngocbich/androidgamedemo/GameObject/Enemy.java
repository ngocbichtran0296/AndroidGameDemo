package com.ngocbich.androidgamedemo.GameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.ngocbich.androidgamedemo.BitmapImages;
import com.ngocbich.androidgamedemo.Movement;

/**
 * Created by Ngoc Bich on 5/7/2018.
 */

public class Enemy {
    private int xPos, yPos,dir;

    public Enemy(int blockSize){
        xPos=8*blockSize;

        yPos=4*blockSize;

        dir = 4;
    }

    public int getXPos(){ return xPos; }
    public int getYPos(){ return yPos; }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public void setXPos(int xPos){ this.xPos = xPos; }
    public void setYPos(int yPos){ this.yPos = yPos; }


    public void drawEnemy(BitmapImages bitmap, Canvas canvas, Movement movement, Paint paint, Context context, int type){
        if (type == 0) {
            //move enemy
            movement.moveEnemy0();
            //draw enemy
            canvas.drawBitmap(bitmap.getEnemyBitmap0(), this.getXPos(), this.getYPos(), paint);
            //check if there is a collision and handle game over
            movement.tryDeath(context);
        }
        else if (type == 1) {
            //move enemy
            movement.moveEnemy1();
            //draw enemy
            canvas.drawBitmap(bitmap.getEnemyBitmap1(), this.getXPos(), this.getYPos(), paint);
            //check if there is a collision and handle game over
            movement.tryDeath(context);
        }
    }

}
