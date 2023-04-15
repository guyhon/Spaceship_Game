package com.example.myapplication10.object;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication10.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Asteroid {

    private ShapeableImageView[][] asteroids;
    private int[][] asteroidFlag;
    private int asteroidInGame;
    private Random asteroidStart;
    private ArrayList<Integer> asteroidIndex;

    public Asteroid(){}

    public void setAsteroidFlagInIndex( int i ,int j, int value) {
        this.asteroidFlag[i][j] = value;
    }

    public int[][] getAsteroidFlag() {
        return asteroidFlag;
    }

    public int getAsteroidInGame() {
        return asteroidInGame;
    }

    public ArrayList<Integer> getAsteroidIndex() {
        return asteroidIndex;
    }

    public void setAsteroidFlag(int[][] asteroidFlag) {
        this.asteroidFlag = asteroidFlag;
    }

    public void setAsteroidInGame(int asteroidInGame) {
        this.asteroidInGame = asteroidInGame;
    }

    public void setAsteroid(ShapeableImageView[][] asteroids) {
        this.asteroids = asteroids;
    }

    public  void initAsteroid(){

        setAsteroidFlag(new int[asteroids.length][asteroids[0].length]);
        asteroidInGame = 0;
        asteroidStart = new Random();
        asteroidIndex = new ArrayList<>();

        for (int[] ints : asteroidFlag) {
            Arrays.fill(ints, 0);
        }
    }

    public void updateAsteroids(int i){
        setAsteroidFlagInIndex( (getAsteroidIndex().get(i) / 10) ,(getAsteroidIndex().get(i) % 10), 0) ; //remove old
        setAsteroidFlagInIndex( (getAsteroidIndex().get(i) / 10) + 1,(getAsteroidIndex().get(i) % 10), 1) ; //new
        getAsteroidIndex().set(i,getAsteroidIndex().get(i) + 10);
        updateAsteroidsUI();
    }



    public void updateAsteroidsUI() {
        for (int i = 0; i < asteroids.length; i++) {
            for (int j = 0; j < asteroids[i].length; j++) {
                ImageView im = asteroids[i][j];
                if (asteroidFlag[i][j] == 0) {
                    im.setVisibility(View.INVISIBLE);
                } else if (asteroidFlag[i][j] == 1) {
                    im.setVisibility(View.VISIBLE);
                    im.setImageResource(R.drawable.asteroid_2);
                }
            }
        }
    }

    public void crateAsteroid() {
        int n = asteroidStart.nextInt(3);
        if (asteroidFlag[0][n] == 0) {
            asteroidFlag[0][n] = 1;
            asteroidIndex.add(n);
        }
        asteroidInGame++;
        updateAsteroidsUI();
    }



}
