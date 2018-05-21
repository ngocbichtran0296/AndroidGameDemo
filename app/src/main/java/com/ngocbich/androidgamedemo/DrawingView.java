package com.ngocbich.androidgamedemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ngocbich.androidgamedemo.Activity.PauseActivity;
import com.ngocbich.androidgamedemo.GameObject.Enemy;
import com.ngocbich.androidgamedemo.GameObject.Player;

import static java.lang.Thread.sleep;

/**
 * Created by Ngoc Bich on 5/7/2018.
 */

public class DrawingView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private Thread thread;
    private SurfaceHolder holder;
    private boolean canDraw = true;

    private int FPS=30;

    private Paint paint;

    private int totalFrame = 4;             // Total amount of frames fo each direction
    private int currentPlayerFrame = 0;     // Current Player frame to draw

    private long frameTicker;               // Current time since last frame has been drawn

    private float x1, x2, y1, y2;           // positions of swipe

    private int screenWidth;                // Width of the phone screen
    private int blockSize;                  // Size of a block on the map

    final Handler handler = new Handler();

    private short currentMap[][];           //the current map being played


    private Movement movement;

    private BitmapImages bitmap;

    private Player player;
    private Enemy enemy0;
    private Enemy enemy1;


    public DrawingView(Context context){
        super(context);

        holder = getHolder();
        holder.addCallback(this);
        frameTicker = 1000/totalFrame; //time for each frame

        paint = new Paint();
        paint.setColor(Color.WHITE);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;

        blockSize = screenWidth/17;
        blockSize = (blockSize / 5) * 5;

        Globals map = Globals.getInstance();
        int theMap = map.getLevelSelector();

        currentMap = LevelGenerator.getMap(theMap); //get level 1

        movement = new Movement(currentMap, blockSize);   //create a new instance of the movement class

        player = movement.getPlayer();                    //reference of player object
        enemy0 = movement.getEnemy0();                      //reference of enemy object 0
        enemy1 = movement.getEnemy1();


        //each enemy spawns at a different position
        enemy1.setXPos(4 * blockSize);
        enemy1.setYPos(8 * blockSize);
        enemy1.setDir(1);


        bitmap = new BitmapImages(blockSize, context);    //loads the bitmap images


        Log.i("info", "Constructor");
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("info", "Surface Created");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("info", "Surface Changed");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("info", "Surface Destroyed");
    }

    private double averageFPS;
    @Override
    public void run() {
        Log.i("info", "Run");
        GameConditions.countDots(currentMap);

        long startTime;
        long timeMillis = 1000 / FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / FPS;

        while (canDraw){
            startTime = System.nanoTime();

            if (!holder.getSurface().isValid()) {
                continue;
            }

            Canvas canvas = holder.lockCanvas();
            // Set background
            if(canvas!=null){
                canvas.drawColor(Color.BLACK);
                UserInterface.drawMap(canvas, currentMap, paint, blockSize);

                updateFrame(System.currentTimeMillis());


                player.drawPlayer(bitmap, canvas, movement, paint, getContext(), currentPlayerFrame);

                enemy0.drawEnemy(bitmap, canvas, movement, paint, getContext(), 0);
                enemy1.drawEnemy(bitmap, canvas, movement, paint, getContext(), 1);


                updateConditions(movement);

                GameConditions.checkVictory(getContext());

                UserInterface.drawDots(canvas, currentMap, paint, blockSize);
                UserInterface.drawPauseButton(bitmap, canvas, paint, blockSize);
                UserInterface.drawMuteButton(bitmap, canvas, paint, blockSize);
                UserInterface.drawScores(canvas, paint, blockSize);


                holder.unlockCanvasAndPost(canvas);
            }//end if()

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            try {
                if (waitTime > 0) {
                    sleep(waitTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == FPS) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }

    // Check to see if we should update the current frame
    // based on time passed so the animation won't be too
    // quick and look bad
    private void updateFrame(long gameTime) {

        // If enough time has passed go to next frame
        if (gameTime > frameTicker + (totalFrame * 30)) {
            frameTicker = gameTime;

            // Increment the frame
            currentPlayerFrame++;
            // Loop back the frame when you have gone through all the frames
            if (currentPlayerFrame >= totalFrame) {
                currentPlayerFrame = 0;
            }
        }
    }


    Runnable pauseThread = new Runnable() {
        public void run() {
            Log.i("info", "pauseThread");
            Intent pauseIntent = new Intent(getContext(), PauseActivity.class);
            getContext().startActivity(pauseIntent);
        }
    };


    Runnable musicThread = new Runnable() {
        public void run() {
            Log.i("info", "musicThread");
            if (MainActivity.getPlayer().isPlaying())
                MainActivity.getPlayer().pause();
            else
                MainActivity.getPlayer().start();
        }
    };


    // Method to get touch events
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case (MotionEvent.ACTION_DOWN):{
                x1 = event.getX();
                y1 = event.getY();

                if (x1 >= blockSize*0 && x1 <= blockSize*4 && y1 >= blockSize*20 && y1 <= blockSize*22) {
                    handler.postAtFrontOfQueue(pauseThread);
                }
                if (x1 >= blockSize*0 && x1 <= blockSize*4 && y1 >= blockSize*24 && y1 <= blockSize*26) {
                    handler.postAtFrontOfQueue(musicThread);
                }
                break;
            }
            case (MotionEvent.ACTION_UP):{
                x2 = event.getX();
                y2 = event.getY();
                calculateSwipeDirection();
                if (x1 >= blockSize*0 && x1 <= blockSize*4 && y1 >= blockSize*20 && y1 <= blockSize*22) {
                    handler.removeCallbacks(pauseThread);
                }
                if (x1 >= blockSize*0 && x1 <= blockSize*4 && y1 >= blockSize*24 && y1 <= blockSize*26) {
                    handler.removeCallbacks(musicThread);
                }
                break;
            }
        }
        return true;
    }



    // Calculates which direction the user swipes
    // based on calculating the differences in
    // initial position vs final position of the swipe
    private void calculateSwipeDirection() {
        float xDiff = (x2 - x1);
        float yDiff = (y2 - y1);

        // Directions
        // 0 - up
        // 1 - right
        // 2 - down
        // 3 - left
        // 4 - stop

        // Checks which axis has the greater distance
        // in order to see which direction the swipe is
        // going to be (buffering of direction)
        if (Math.abs(yDiff) > Math.abs(xDiff)) {
            if (yDiff < 0) {
                player.setNextDir(0);
            } else if (yDiff > 0) {
                player.setNextDir(2);
            }
        } else {
            if (xDiff < 0) {
                player.setNextDir(3);
            } else if (xDiff > 0) {
                player.setNextDir(1);
            }
        }

    }



    private void updateConditions(Movement movement) {
        if (movement.needMapRefresh()) {
            GameConditions gc = GameConditions.getInstance();
            currentMap = movement.updateMap();
            gc.updateCurrentScore();
            gc.updateNumOfDots();
        }
    }


    public void pause() {
        Log.i("info", "pause");
        canDraw = false;
        thread = null;
    }

    public void resume() {
        Log.i("info", "resume");
        if (thread != null) {
            thread.start();
        }
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
            Log.i("info", "resume thread");
        }
        canDraw = true;
    }
}
