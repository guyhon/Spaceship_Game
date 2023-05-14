package com.example.myapplication10.object;

import android.view.View;
import android.widget.ImageView;

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

    public void setHeartFlag(int[] heartFlag) {
        this.heartFlag = heartFlag;
    }
    public void setHearts(ShapeableImageView[] hearts) {
        this.hearts = hearts;
    }

//    public static class Score {
//
//        public class Score {
//
//            private int score;
//
//            public Score(int points){
//                this.score = points;
//            }
//            public int getScore() {
//                return score;
//            }
//        }
//    }
}
