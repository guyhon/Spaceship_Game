package com.example.myapplication10.object;

import android.view.View;
import android.widget.ImageView;

import com.example.myapplication10.R;
import com.google.android.material.imageview.ShapeableImageView;

public class Spaceship  {

    private ShapeableImageView[] spaceships;
    private int[] spaceshipFlag;
    private int SpaceshipTrace;

    public Spaceship(){

    }

    public void initSpaceship(){

        spaceshipFlag = new int[spaceships.length];
        for (int i = 0; i < spaceshipFlag.length; i++) {
            if (i == 1) {
                spaceshipFlag[i] = 1;
                SpaceshipTrace = 1;
            } else {
                spaceshipFlag[i] = 0;
            }
        }
       // updateSpaceshipUI();
    }

    public void moveSpaceshipLeft(){
        spaceshipFlag[SpaceshipTrace] = 0;
        SpaceshipTrace -= 1;
        spaceshipFlag[SpaceshipTrace] = 1;
        updateSpaceshipUI();
    }

    public void moveSpaceshipRight(){
        spaceshipFlag[SpaceshipTrace] = 0;
        SpaceshipTrace += 1;
        spaceshipFlag[SpaceshipTrace] = 1;
        updateSpaceshipUI();
    }

    public void updateSpaceshipUI() {
        //car move
        for (int i = 0; i < spaceships.length; i++) {
            ImageView im = spaceships[i];
            if (spaceshipFlag[i] == 0)
                im.setVisibility(View.INVISIBLE);
            else if (spaceshipFlag[i] == 1) {
                im.setVisibility(View.VISIBLE);
                im.setImageResource(R.drawable.spaceship_3);
            }
        }
    }

    public boolean checkFlag (int i){
        return spaceshipFlag[i] == 1;
    }

    public  ImageView getSpaceshipImgByIndex(int i){
        return this.spaceships[i];
    }

    public void setSpaceshipValByIndex(int i, int val){this.spaceshipFlag[i]=val;}

    public int getSpaceshipTrace() {
        return SpaceshipTrace;
    }

    public void setSpaceship(ShapeableImageView[] spaceships) {
        this.spaceships = spaceships;
    }



}
