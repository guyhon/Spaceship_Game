package com.example.myapplication10.object;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.Arrays;

public class Heart {
    private ShapeableImageView[] hearts;
    private int[] heartFlag;
    private int life;

    public Heart(){}


    public void initHeart(){
        setHeartFlag(new int[hearts.length]);
        Arrays.fill(heartFlag, 1);
        life=2;
    }
    public void updateHeartUI() {
        for (int i = 0; i < hearts.length; i++) {
            ImageView im = hearts[i];
            if (heartFlag[i] == 0)
                im.setVisibility(View.INVISIBLE);
            else if (heartFlag[i] == 1) {
                im.setVisibility(View.VISIBLE);
                //im.setImageResource(R.drawable.main_img_heart);
            }
        }
    }

    public  void loseLife() {

        if(life>=0) {
            heartFlag[life] = 0;
            life--;
        }
        updateHeartUI();
    }

    public boolean checkFlag (int i){
        return heartFlag[i] != 0;
    }

    public  int getHeartValByIndex(int i){
        return this.heartFlag[i];
    }

    public void setHeartValByIndex(int i, int val){
        this.heartFlag[i]=val;
    }


    public int[] getHeartFlag() {
        return heartFlag;
    }

    public void setHeartFlag(int[] heartFlag) {
        this.heartFlag = heartFlag;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public ImageView[] getHearts() {
        return hearts;
    }

    public void setHearts(ShapeableImageView[] hearts) {
        this.hearts = hearts;
    }
}
