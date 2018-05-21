package com.ngocbich.androidgamedemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Ngoc Bich on 5/10/2018.
 */

public class UserInterface {
    //handles elements user interacts with
    private UserInterface(){}

    public static void drawPauseButton(BitmapImages bitmap, Canvas canvas, Paint paint, int blockSize) {
        //draw pause button
        canvas.drawBitmap(bitmap.getPauseBitmap(), 0, 20*blockSize, paint);
    }


    public static void drawMuteButton(BitmapImages bitmap, Canvas canvas, Paint paint, int blockSize) {
        //draw mute button
        canvas.drawBitmap(bitmap.getMuteBitmap(), 0 * blockSize, 24*blockSize, paint);
    }

    // Method that draws dots and updates them when eaten
    public static void drawDots(Canvas canvas, short[][] currentMap, Paint paint, int blockSize) {
        float x, y;
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 17; j++) {
                x = j * blockSize;
                y = i * blockSize;
                // Draws fruit in the middle of a block
                if ((currentMap[i][j] & 16) != 0) {
                    canvas.drawCircle(x + blockSize / 2, y + blockSize / 2, blockSize / 10, paint);
                }
            }
        }
    }


    // add pause/unpause button
    // add mute/unmute button
    public static void drawMap(Canvas canvas, short[][] currentMap, Paint paint, int blockSize){
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(3.0f);

        int x, y;

        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 17; j++) {
                x = j * blockSize;
                y = i * blockSize;
                if ((currentMap[i][j] & 1) != 0) // draws left
                    canvas.drawLine(x, y, x, y + blockSize - 1, paint);

                if ((currentMap[i][j] & 2) != 0) // draws top
                    canvas.drawLine(x, y, x + blockSize - 1, y, paint);

                if ((currentMap[i][j] & 4) != 0) // draws right
                    canvas.drawLine(
                            x + blockSize, y, x + blockSize, y + blockSize - 1, paint);
                if ((currentMap[i][j] & 8) != 0) // draws bottom
                    canvas.drawLine(
                            x, y + blockSize, x + blockSize - 1, y + blockSize , paint);
            }
        }
        paint.setColor(Color.WHITE);
    }


    public static void drawScores(Canvas canvas, Paint paint, int blockSize) {
        paint.setTextSize(blockSize);

        Globals g = Globals.getInstance();
        GameConditions gc = GameConditions.getInstance();
        int currentScore = gc.getCurrentScore();
        int highScore = g.getHighScore();
        if (currentScore > highScore) {
            g.setHighScore(currentScore);
        }

        String formattedHighScore = String.format("%05d", highScore);
        String hScore = "High Score : " + formattedHighScore;
        canvas.drawText(hScore, 0, 2*blockSize - 10, paint);

        String formattedScore = String.format("%05d", currentScore);
        String score = "Score : " + formattedScore;
        canvas.drawText(score, 11 * blockSize, 2 * blockSize - 10, paint);
    }
}
