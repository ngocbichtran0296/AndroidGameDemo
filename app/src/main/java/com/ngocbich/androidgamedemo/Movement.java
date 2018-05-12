package com.ngocbich.androidgamedemo;

import android.content.Context;
import android.content.Intent;

import com.ngocbich.androidgamedemo.Activity.FailedLevelActivity;
import com.ngocbich.androidgamedemo.GameObject.Enemy;
import com.ngocbich.androidgamedemo.GameObject.Player;

// Directions
// 0 means going up
// 1 means going right
// 2 means going down
// 3 means going left
// 4 means stop moving, look at move function

public class Movement {

    private Player player;
    private Enemy enemy0;
    private Enemy enemy1;

    private int blockSize;
    private short [][] currentMap;
    private int swipeDir;
    private boolean dotsEaten;

    public Movement(final short [][] curMap, final int blockSize){
        currentMap = curMap;
        this.blockSize = blockSize;

        player = new Player(blockSize);
        enemy0 = new Enemy(blockSize);
        enemy1 = new Enemy(blockSize);

        swipeDir = 4;
        dotsEaten = false;
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy0() {
        return enemy0;
    }

    public Enemy getEnemy1() {
        return enemy1;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public short[][] getCurrentMap() {
        return currentMap;
    }

    public int getSwipeDir() {
        return swipeDir;
    }

    public boolean isDotsEaten() {
        return dotsEaten;
    }

    //checks to see if game over happens
    public void checkPlayerDeath() throws PlayerDeathException{
        if(((enemy0.getXPos()/blockSize) == (player.getXPos()/blockSize))&&
                ((enemy0.getYPos()/blockSize) == (player.getYPos()/blockSize)) ||
                ((enemy1.getXPos()/blockSize) == (player.getXPos()/blockSize)) &&
                        ((enemy1.getYPos()/blockSize) == (player.getYPos()/blockSize))){
            throw new PlayerDeathException("Player and Enemy collision");
        }
    }

    //returns the updated map with the dots removed
    public short[][] updateMap(){
        dotsEaten = false;
        return currentMap;
    }

    //to see if we need to update the map if a dot was removed
    public boolean needMapRefresh(){ return dotsEaten; }
    //private method to change the map when the pellet is eaten
    private void dotWasEaten(int x, int y, short value){
        currentMap[x][y] = value;
        dotsEaten = true;
    }


    //move player
    public void movePlayer(){
        short ch;
        int nextDirection = player.getNextDir();
        int xPosPlayer = player.getXPos();
        int yPosPlayer = player.getYPos();

        // Check if xPos and yPos of player is both a multiple of block size
        if((xPosPlayer % blockSize ==0)&&(yPosPlayer % blockSize ==0)){
            // When player goes through tunnel on
            // the right reappear at left tunnel
            if (xPosPlayer >= blockSize * 17) {
                xPosPlayer = 0;
                player.setXPos(0);
            }

            // Is used to find the number in the level array in order to
            // check wall placement, dot placement, and candy placement
            ch = currentMap[yPosPlayer / blockSize][xPosPlayer / blockSize];

            // If there is a dot, eat it
            if ((ch & 16) != 0) {

                // Toggle dot so it won't be drawn anymore
                dotWasEaten(yPosPlayer / blockSize, xPosPlayer / blockSize, (short) (ch ^ 16));
            }


            // Checks for direction buffering
            if (!((nextDirection == 3 && (ch & 1) != 0) ||
                    (nextDirection == 1 && (ch & 4) != 0) ||
                    (nextDirection == 0 && (ch & 2) != 0) ||
                    (nextDirection == 2 && (ch & 8) != 0))) {
                player.setCurDir(nextDirection);
                swipeDir = nextDirection;
            }

            // Checks for wall collisions
            if ((swipeDir == 3 && (ch & 1) != 0) ||
                    (swipeDir == 1 && (ch & 4) != 0) ||
                    (swipeDir == 0 && (ch & 2) != 0) ||
                    (swipeDir == 2 && (ch & 8) != 0)) {
                swipeDir = 4;
            }
        }
        // When pacman goes through tunnel on
        // the left reappear at right tunnel
        if (xPosPlayer < 0) {
            xPosPlayer = blockSize * 17;
            player.setXPos(blockSize * 17);
        }
    }


    //call method after we have moved and drawn player
    public void updatePlayer(){
        // Depending on the direction move the position of player
        if (swipeDir == 0) {        // 0: up
            player.setYPos(player.getYPos() + -blockSize/15);
        } else if (swipeDir == 1) {         // 1: right
            player.setXPos(player.getXPos() + blockSize/15);
        } else if (swipeDir == 2) {         // 2: down
            player.setYPos(player.getYPos() + blockSize/15);
        } else if (swipeDir == 3) {         // 3: left
            player.setXPos(player.getXPos() + -blockSize/15);
        }
    }



    //move ghost
    public void moveEnemy0() {
        short ch;
        int enemyDir = enemy0.getDir();
        int xPosEnemy = enemy0.getXPos();
        int yPosEnemy = enemy0.getYPos();
        int xDis = player.getXPos() - xPosEnemy;
        int yDis = player.getYPos() - yPosEnemy;


        if ((xPosEnemy % blockSize == 0) && (yPosEnemy % blockSize == 0)) {
            ch = currentMap[yPosEnemy / blockSize][xPosEnemy / blockSize];

            if (xPosEnemy >= blockSize * 17) {
                xPosEnemy = 0;
                enemy0.setXPos(0);
            }
            if (xPosEnemy < 0) {
                xPosEnemy = blockSize * 17;
                enemy0.setXPos(blockSize * 17);
            }


            // Move ghost right and down
            if (xDis >= 0 && yDis >= 0) {
                if ((ch & 4) == 0 && (ch & 8) == 0) {
                    if (Math.abs(xDis) > Math.abs(yDis)) {
                        enemyDir = 1;
                        enemy0.setDir(1);
                    } else {
                        enemyDir = 2;
                        enemy0.setDir(2);
                    }
                } else if ((ch & 4) == 0) {
                    enemyDir = 1;
                    enemy0.setDir(1);
                } else if ((ch & 8) == 0) {
                    enemyDir = 2;
                    enemy0.setDir(2);
                } else {
                    enemyDir = 3;
                    enemy0.setDir(3);
                }
            }


            // Move ghost right and up
            if (xDis >= 0 && yDis <= 0) {
                if (xDis >= 0 && yDis <= 0) {
                    if ((ch & 4) == 0 && (ch & 2) == 0) {
                        if (Math.abs(xDis) > Math.abs(yDis)) {
                            enemyDir = 1;
                            enemy0.setDir(1);
                        } else {
                            enemyDir = 0;
                            enemy0.setDir(0);
                        }
                    } else if ((ch & 4) == 0) {
                        enemyDir = 1;
                        enemy0.setDir(1);
                    } else if ((ch & 2) == 0) {
                        enemyDir = 0;
                        enemy0.setDir(0);
                    } else {
                        enemyDir = 2;
                        enemy0.setDir(2);
                    }
                }
            }


            // Move ghost left and down
            if (xDis <= 0 && yDis >= 0) {
                if ((ch & 1) == 0 && (ch & 8) == 0) {
                    if (Math.abs(xDis) > Math.abs(yDis)) {
                        enemyDir = 3;
                        enemy0.setDir(3);
                    } else {
                        enemyDir = 2;
                        enemy0.setDir(2);
                    }
                } else if ((ch & 1) == 0) {
                    enemyDir = 3;
                    enemy0.setDir(3);
                } else if ((ch & 8) == 0) {
                    enemyDir = 2;
                    enemy0.setDir(2);
                } else {
                    enemyDir = 1;
                    enemy0.setDir(1);
                }
            }


            // Move ghost left and up
            if (xDis <= 0 && yDis <= 0) {
                if ((ch & 1) == 0 && (ch & 2) == 0) {
                    if (Math.abs(xDis) > Math.abs(yDis)) {
                        enemyDir = 3;
                        enemy0.setDir(3);
                    } else {
                        enemyDir = 0;
                        enemy0.setDir(0);
                    }
                } else if ((ch & 1) == 0) {
                    enemyDir = 3;
                    enemy0.setDir(3);
                } else if ((ch & 2) == 0) {
                    enemyDir = 0;
                    enemy0.setDir(0);
                } else {
                    enemyDir = 2;
                    enemy0.setDir(2);
                }
            }


            // Handles wall collisions
            if ((enemyDir == 3 && (ch & 1) != 0) ||
                    (enemyDir == 1 && (ch & 4) != 0) ||
                    (enemyDir == 0 && (ch & 2) != 0) ||
                    (enemyDir == 2 && (ch & 8) != 0)) {
                enemyDir = 4;
                enemy0.setDir(4);
            }
        }



        if (enemy0.getDir() == 0) {
            enemy0.setYPos(enemy0.getYPos() + -blockSize/20);
        } else if (enemy0.getDir() == 1) {
            enemy0.setXPos(enemy0.getXPos() + blockSize/20);
        } else if (enemy0.getDir() == 2) {
            enemy0.setYPos(enemy0.getYPos() + blockSize/20);
        } else if (enemy0.getDir() == 3) {
            enemy0.setXPos(enemy0.getXPos() + -blockSize/20);
        }
    }

    public void moveEnemy1() {
        short ch;
        int enemyDir = enemy1.getDir();
        int xPosEnemy= enemy1.getXPos();
        int yPosEnemy = enemy1.getYPos();
        int xDis = player.getXPos() - xPosEnemy;
        int yDis = player.getYPos() - yPosEnemy;


        if ((xPosEnemy% blockSize == 0) && (yPosEnemy % blockSize == 0)) {
            ch = currentMap[yPosEnemy / blockSize][xPosEnemy / blockSize];

            if (xPosEnemy >= blockSize * 17) {
                xPosEnemy = 0;
                enemy1.setXPos(0);
            }
            if (xPosEnemy < 0) {
                xPosEnemy = blockSize * 17;
                enemy1.setXPos(blockSize * 17);
            }


            // Move ghost right and down
            if (xDis >= 0 && yDis >= 0) {
                if ((ch & 4) == 0 && (ch & 8) == 0) {
                    if (Math.abs(xDis) > Math.abs(yDis)) {
                        enemyDir = 1;
                        enemy1.setDir(1);
                    } else {
                        enemyDir = 2;
                        enemy1.setDir(2);
                    }
                } else if ((ch & 4) == 0) {
                    enemyDir = 1;
                    enemy1.setDir(1);
                } else if ((ch & 8) == 0) {
                    enemyDir = 2;
                    enemy1.setDir(2);
                } else {
                    enemyDir = 3;
                    enemy1.setDir(3);
                }
            }


            // Move ghost right and up
            if (xDis >= 0 && yDis <= 0) {
                if (xDis >= 0 && yDis <= 0) {
                    if ((ch & 4) == 0 && (ch & 2) == 0) {
                        if (Math.abs(xDis) > Math.abs(yDis)) {
                            enemyDir = 1;
                            enemy1.setDir(1);
                        } else {
                            enemyDir = 0;
                            enemy1.setDir(0);
                        }
                    } else if ((ch & 4) == 0) {
                        enemyDir = 1;
                        enemy1.setDir(1);
                    } else if ((ch & 2) == 0) {
                        enemyDir = 0;
                        enemy1.setDir(0);
                    } else {
                        enemyDir = 2;
                        enemy1.setDir(2);
                    }
                }
            }


            // Move ghost left and down
            if (xDis <= 0 && yDis >= 0) {
                if ((ch & 1) == 0 && (ch & 8) == 0) {
                    if (Math.abs(xDis) > Math.abs(yDis)) {
                        enemyDir = 3;
                        enemy1.setDir(3);
                    } else {
                        enemyDir = 2;
                        enemy1.setDir(2);
                    }
                } else if ((ch & 1) == 0) {
                    enemyDir = 3;
                    enemy1.setDir(3);
                } else if ((ch & 8) == 0) {
                    enemyDir = 2;
                    enemy1.setDir(2);
                } else {
                    enemyDir = 1;
                    enemy1.setDir(1);
                }
            }


            // Move ghost left and up
            if (xDis <= 0 && yDis <= 0) {
                if ((ch & 1) == 0 && (ch & 2) == 0) {
                    if (Math.abs(xDis) > Math.abs(yDis)) {
                        enemyDir = 3;
                        enemy1.setDir(3);
                    } else {
                        enemyDir = 0;
                        enemy1.setDir(0);
                    }
                } else if ((ch & 1) == 0) {
                    enemyDir = 3;
                    enemy1.setDir(3);
                } else if ((ch & 2) == 0) {
                    enemyDir = 0;
                    enemy1.setDir(0);
                } else {
                    enemyDir = 2;
                    enemy1.setDir(2);
                }
            }


            // Handles wall collisions
            if ((enemyDir == 3 && (ch & 1) != 0) ||
                    (enemyDir == 1 && (ch & 4) != 0) ||
                    (enemyDir == 0 && (ch & 2) != 0) ||
                    (enemyDir == 2 && (ch & 8) != 0)) {
                enemyDir = 4;
                enemy1.setDir(4);
            }
        }



        if (enemy1.getDir() == 0) {
            enemy1.setYPos(enemy1.getYPos() + -blockSize/20);
        } else if (enemy1.getDir() == 1) {
            enemy1.setXPos(enemy1.getXPos() + blockSize/20);
        } else if (enemy1.getDir() == 2) {
            enemy1.setYPos(enemy1.getYPos() + blockSize/20);
        } else if (enemy1.getDir() == 3) {
            enemy1.setXPos(enemy1.getXPos() + -blockSize/20);
        }
    }




    public void tryDeath(Context context){
        try{
            checkPlayerDeath();
        } catch (PlayerDeathException e){
            Intent failedIntent = new Intent(context, FailedLevelActivity.class);
            context.startActivity(failedIntent);
        }
    }
}
