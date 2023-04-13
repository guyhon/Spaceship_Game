package com.example.myapplication10;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication10.object.Asteroid;
import com.example.myapplication10.object.Heart;
import com.example.myapplication10.object.Spaceship;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private Spaceship spaceships = new Spaceship();

    private Asteroid asteroids = new Asteroid();

    private Heart hearts = new Heart();

    private MaterialButton[] buttons;

    private Timer crateAsteroidT = new Timer();
    private Timer moveAsteroidT = new Timer();
    private CountDownTimer crateTimer;
    private CountDownTimer moveTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setUp();
        buttonsHandle();
    }

    protected void onStart() {
        super.onStart();
        asteroids.updateAsteroidsUI();
        spaceships.updateSpaceshipUI();
        hearts.updateHeartUI();
        //new Handler().postDelayed(this::startTicker, 2000);
        startTime();
    }
    private void moveSpaceship(int direction) {

        //Left
        if (direction == 0 && spaceships.getSpaceshipTrace() != 0) {
            spaceships.moveSpaceshipLeft();
        }

        // Right
        else if (direction == 1 && spaceships.getSpaceshipTrace() != 2) {
            spaceships.moveSpaceshipRight();
        }
    }



    private void updateTimeUI(){
        asteroids.updateAsteroidsUI();
        spaceships.updateSpaceshipUI();
        hearts.updateHeartUI();
    }

    private final Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //handler.postDelayed(this, 5000); //Do it again in a second
            updateTimeUI();
        }
    };

    private void buttonsHandle() {

        for (int i = 0; i < buttons.length; i++) {
            int direction = i;
            buttons[i].setOnClickListener(v -> moveSpaceship(direction));
        }
    }

    private void initViews() {

        spaceships.setSpaceship(new ShapeableImageView[]{
                findViewById(R.id.main_IMG_40spaceship),
                findViewById(R.id.main_IMG_41spaceship),
                findViewById(R.id.main_IMG_42spaceship)});


        hearts.setHearts(new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)});

        asteroids.setAsteroid(new ShapeableImageView[][]{
                {findViewById(R.id.main_IMG_00), findViewById(R.id.main_IMG_01), findViewById(R.id.main_IMG_02)},
                {findViewById(R.id.main_IMG_10), findViewById(R.id.main_IMG_11), findViewById(R.id.main_IMG_12)},
                {findViewById(R.id.main_IMG_20), findViewById(R.id.main_IMG_21), findViewById(R.id.main_IMG_22)},
                {findViewById(R.id.main_IMG_30), findViewById(R.id.main_IMG_31), findViewById(R.id.main_IMG_32)}});


        buttons = new MaterialButton[]{
                findViewById(R.id.main_left_button),
                findViewById(R.id.main_right_button)};

    }

    private void setUp() {

        spaceships.initSpaceship();
        hearts.initHeart();
        asteroids.initAsteroid();
    }

    public void moveAsteroid() {
        for (int i = 0; i < asteroids.getAsteroidInGame(); i++) {
            if (asteroids.getAsteroidIndex().get(i) < 30) {
                asteroids.updateAsteroids(i);
            } else if (asteroids.getAsteroidIndex().get(i) >= 30) {
                asteroids.setAsteroidFlagInIndex(asteroids.getAsteroidIndex().get(i) / 10, asteroids.getAsteroidIndex().get(i) % 10, 0);
                asteroids.updateAsteroidsUI();
                moveAsteroidToEnd(i);

                try{asteroids.getAsteroidIndex().remove(i);} catch (Exception e) {
                    e.printStackTrace();
                }
                asteroids.setAsteroidInGame(asteroids.getAsteroidInGame() - 1);
                asteroids.updateAsteroids(i);
                new Handler().postDelayed(runnable, 500);

            }
        }
    }

    public void moveAsteroidToEnd(int i) {
        ImageView im = spaceships.getSpaceshipImgByIndex(asteroids.getAsteroidIndex().get(i) % 10);
        im.setImageResource(R.drawable.asteroid_2);
        im.setVisibility(View.VISIBLE);

        if (spaceships.checkFlag(asteroids.getAsteroidIndex().get(i) % 10)){// crash
            handleCrash(im);
        }

    }

    private void startTime() {
        //startTime = System.currentTimeMillis();
        if (crateTimer == null) {
            crateTimer = new CountDownTimer(999999999, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    asteroids.crateAsteroid();
                }
                @Override
                public void onFinish() {
                    crateTimer.cancel();
                }
            }.start();
        }
        //new Handler().postDelayed(runnable, 1000);
        if (moveTimer == null) {
            moveTimer = new CountDownTimer(999999999, 500) {
                @Override
                public void onTick(long millisUntilFinished) {
                    moveAsteroid();
                }
                @Override
                public void onFinish() {
                    moveTimer.cancel();
                }
            }.start();
        }
    }
//    private void startTicker() {
//        crateAsteroidT = new Timer();
//        crateAsteroidT.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(asteroids::crateAsteroid);
//            }
//        }, 0, 1500);
//
//        moveAsteroidT = new Timer();
//        moveAsteroidT.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(() -> moveAsteroid());
//            }
//        }, 500, 500);
//
//    } //start

    private void handleCrash(@NonNull ImageView im) {
        loseLife();
        im.setImageResource(R.drawable.explosion);
        im.setVisibility(View.VISIBLE);

        spaceships.setSpaceshipValByIndex(spaceships.getSpaceshipTrace(),1);
    }

    private  void loseLife(){
        vibrate(1);
        hearts.loseLife();
        Context context = getApplicationContext();
        CharSequence text = "Crash!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void vibrate(int style) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (style == 1)//boom
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        if (style == 2)//click
            v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}
