package com.ngocbich.androidgamedemo.GameObject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.ngocbich.androidgamedemo.BitmapImages;
import com.ngocbich.androidgamedemo.Movement;

/**
 * Created by Ngoc Bich on 5/7/2018.
 */

public class Player {
    private int xPos, yPos, curDir, nextDir;

    public Player(final int blockSize){
        xPos = 8 * blockSize;

        yPos = 13 * blockSize;

        curDir = 2;
        nextDir = 4;
    }

    //public getters
    public int getXPos(){ return xPos; }
    public int getYPos(){ return yPos; }
    public int getCurDir(){ return curDir; }
    public int getNextDir(){ return nextDir; }

    //public setters
    public void setXPos(int xPos){ this.xPos = xPos; }
    public void setYPos(int yPos){ this.yPos = yPos; }
    public void setCurDir(int curDir){ this.curDir = curDir; }
    public void setNextDir(int nextDir){ this.nextDir = nextDir; }


    //Method that draws player based on his viewDirection
    public void drawPlayer(BitmapImages bitmap, Canvas canvas, Movement movement, Paint paint, Context context, int currentPlayerFrame) {
        //move player
        movement.movePlayer();

        //draw player
        switch (this.getCurDir()) {
            case (0):
                canvas.drawBitmap(bitmap.getPlayerUp()[currentPlayerFrame], this.getXPos(), this.getYPos(), paint);
                break;
            case (1):
                canvas.drawBitmap(bitmap.getPlayerRight()[currentPlayerFrame], this.getXPos(), this.getYPos(), paint);
                break;
            case (3):
                canvas.drawBitmap(bitmap.getPlayerLeft()[currentPlayerFrame], this.getXPos(), this.getYPos(), paint);
                break;
            default:
                canvas.drawBitmap(bitmap.getPlayerDown()[currentPlayerFrame], this.getXPos(), this.getYPos(), paint);
                break;
        }

        //update player
        movement.updatePlayer();
        //check if there is a collision
        movement.tryDeath(context);
    }
}
