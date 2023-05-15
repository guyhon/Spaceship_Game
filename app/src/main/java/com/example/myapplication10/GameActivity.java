package com.example.myapplication10;

import static com.example.myapplication10.Fragments.MapFragment.isLocationEnabled;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapplication10.Interfaces.StepCallback;
import com.example.myapplication10.Utilities.StepDetector;
import com.example.myapplication10.object.Asteroid;
import com.example.myapplication10.object.Heart;
import com.example.myapplication10.object.Score;
import com.example.myapplication10.object.ScoreList;
import com.example.myapplication10.object.Spaceship;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;

import java.util.Timer;

public class GameActivity extends AppCompatActivity {

    private Spaceship spaceships = new Spaceship();

    private Asteroid asteroids = new Asteroid();

    private Heart hearts = new Heart();

    private MaterialButton[] buttons;

    private Timer crateAsteroidT = new Timer();
    private Timer moveAsteroidT = new Timer();
    private CountDownTimer crateTimer;
    private CountDownTimer moveTimer;

    private CountDownTimer pointsTimer;

    private TextView pointsText;
    private int points;

    private Score score;

    private int crateSpeed;
    private int moveSpeed;

    private boolean sensorsMode;

    private int life = 3;


    private static final String TAG = "GameActivity";

    private StepDetector stepDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setUp();
        crateSpeed = getIntent().getIntExtra("crate_speed", 0);
        moveSpeed = getIntent().getIntExtra("move_speed", 0);
        sensorsMode = getIntent().getBooleanExtra("sensor_mode", false);
        if(sensorsMode) {
            initStepDetector();
            stepDetector.start();
        }
        else
            buttonsHandle();
    }

    protected void onStart() {
        super.onStart();
        updateUI();
        startTime();
    }

    private void moveSpaceship(int direction) {

        if (direction == 0 && spaceships.getSpaceshipTrace() != 0) {
            spaceships.moveSpaceshipLeft();
        } else if (direction == 1 && spaceships.getSpaceshipTrace() != 4) {
            spaceships.moveSpaceshipRight();
        }
    }

    private void initStepDetector() {

        for (int i = 0; i < buttons.length; i++) {
            int direction = i;
            buttons[i].setVisibility(View.INVISIBLE);
        }
        stepDetector = new StepDetector(this, new StepCallback() {
            @Override
            public void stepX() {
                moveSpaceship(0);
            }

            @Override
            public void stepY() {
                moveSpaceship(1);
            }


        });
    }

    private void updateUI() {
        asteroids.updateAsteroidsUI();
        spaceships.updateSpaceshipUI();
        hearts.updateHeartUI();
    }

    private final Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateUI();
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
                findViewById(R.id.main_IMG_50),
                findViewById(R.id.main_IMG_51),
                findViewById(R.id.main_IMG_52),
                findViewById(R.id.main_IMG_53),
                findViewById(R.id.main_IMG_54)});


        hearts.setHearts(new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)});

        asteroids.setAsteroid(new ShapeableImageView[][]{
                {findViewById(R.id.main_IMG_00), findViewById(R.id.main_IMG_01), findViewById(R.id.main_IMG_02), findViewById(R.id.main_IMG_03), findViewById(R.id.main_IMG_04)},
                {findViewById(R.id.main_IMG_10), findViewById(R.id.main_IMG_11), findViewById(R.id.main_IMG_12), findViewById(R.id.main_IMG_13), findViewById(R.id.main_IMG_14)},
                {findViewById(R.id.main_IMG_20), findViewById(R.id.main_IMG_21), findViewById(R.id.main_IMG_22), findViewById(R.id.main_IMG_23), findViewById(R.id.main_IMG_24)},
                {findViewById(R.id.main_IMG_30), findViewById(R.id.main_IMG_31), findViewById(R.id.main_IMG_32), findViewById(R.id.main_IMG_33), findViewById(R.id.main_IMG_34)},
                {findViewById(R.id.main_IMG_40), findViewById(R.id.main_IMG_41), findViewById(R.id.main_IMG_42), findViewById(R.id.main_IMG_43), findViewById(R.id.main_IMG_44)}});


        buttons = new MaterialButton[]{
                findViewById(R.id.main_left_button),
                findViewById(R.id.main_right_button)};

        pointsText = findViewById(R.id.main_LBL_score);
        points = 0;

    }

    private void setUp() {

        spaceships.initSpaceship();
        hearts.initHeart();
        asteroids.initAsteroid();
    }


    public void moveAsteroid() {
        for (int i = 0; i < asteroids.getAsteroidInGame(); i++) {
            boolean isCoin = asteroids.isCoin(asteroids.getAsteroidIndex().get(i) / 10, asteroids.getAsteroidIndex().get(i) % 10);
            if (asteroids.getAsteroidIndex().get(i) < 40) {
                if (isCoin)
                    asteroids.updateCoins(i);
                else
                    asteroids.updateAsteroids(i);
            } else if (asteroids.getAsteroidIndex().get(i) >= 40) {
                moveToEnd(i, isCoin);
            }
        }
    }

    public void moveToEnd(int i, boolean isCoin) {
        boolean isNextCoin = asteroids.isCoin(asteroids.getAsteroidIndex().get(i + 1) / 10, asteroids.getAsteroidIndex().get(i + 1) % 10);
        if (isCoin)
            asteroids.updateCoins(i);
        else
            asteroids.updateAsteroids(i);
        if (isCoin)
            moveCoinToEnd(i);
        else
            moveAsteroidToEnd(i);
        try {
            asteroids.getAsteroidIndex().remove(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        asteroids.setAsteroidInGame(asteroids.getAsteroidInGame() - 1);
        if (isNextCoin)
            asteroids.updateCoins(i);
        else
            asteroids.updateAsteroids(i);
        new Handler().postDelayed(runnable, moveSpeed);
    }


    public void moveAsteroidToEnd(int i) {
        ImageView im = spaceships.getSpaceshipImgByIndex(asteroids.getAsteroidIndex().get(i) % 10);
        im.setImageResource(R.drawable.asteroid_2);
        im.setVisibility(View.VISIBLE);

        if (spaceships.checkFlag(asteroids.getAsteroidIndex().get(i) % 10)) {
            handleCrash(im);
        }

    }

    public void moveCoinToEnd(int i) {
        ImageView im = spaceships.getSpaceshipImgByIndex(asteroids.getAsteroidIndex().get(i) % 10);
        im.setImageResource(R.drawable.coin_4);
        im.setVisibility(View.VISIBLE);

        if (spaceships.checkFlag(asteroids.getAsteroidIndex().get(i) % 10)) {
            givePoints(10);
            SignalGenerator.getInstance().toast("You got 10 points!", Toast.LENGTH_SHORT);
            SignalGenerator.getInstance().vibrate(100);
            SignalGenerator.getInstance().sound(2);
        }

    }

    public void givePoints(int point) {
        points += point;
        String text = "" + (points);
        pointsText.setText(text);
    }

    private void startTime() {
        //startTime = System.currentTimeMillis();
        if (crateTimer == null) {

            crateTimer = new CountDownTimer(999999999, crateSpeed) {
                int count = 0;

                @Override
                public void onTick(long millisUntilFinished) {
                    if (count == 10) {
                        asteroids.crateCoin();
                        count = 0;
                    } else
                        asteroids.crateAsteroid();
                    count++;
                }

                @Override
                public void onFinish() {
                    crateTimer.cancel();
                }
            }.start();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (moveTimer == null) {
                    moveTimer = new CountDownTimer(999999999, moveSpeed) {
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
        }, 300);


        if (pointsTimer == null) {
            pointsTimer = new CountDownTimer(999999999, 2000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    pointByTime();
                }

                @Override
                public void onFinish() {
                    crateTimer.cancel();
                }
            }.start();
        }


    }

    private void stopGame(){
        crateTimer.cancel();
        moveTimer.cancel();
        pointsTimer.cancel();
        if(sensorsMode)
            stepDetector.stop();
    }


    private void handleCrash(@NonNull ImageView im) {
        loseLife();
        im.setImageResource(R.drawable.explosion);
        im.setVisibility(View.VISIBLE);

        spaceships.setSpaceshipValByIndex(spaceships.getSpaceshipTrace(), 1);
    }

    private void pointByTime() {
        String text = "" + (++points);
        pointsText.setText(text);
    }

    private void loseLife() {
        hearts.loseLife();
        life--;
        SignalGenerator.getInstance().toast("Crash!", Toast.LENGTH_SHORT);
        SignalGenerator.getInstance().vibrate(500);
        SignalGenerator.getInstance().sound(1);
        if (life < 0)
            gameOver();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        stopGame();
    }



    private void gameOver() {
        makeScore();
        String fromSP = MySharedPreferences.getInstance().getString("com.example.myapplication10", "");
        ScoreList scoreListFromJson = new Gson().fromJson(fromSP, ScoreList.class);
        if (scoreListFromJson == null) {
            scoreListFromJson = new ScoreList();
        }
        scoreListFromJson.addScore(score);
        Log.d("From JSON", scoreListFromJson.toString());
        String scoreListJson = new Gson().toJson(scoreListFromJson);
        Log.d("Json", scoreListJson);
        MySharedPreferences.getInstance().putString("com.example.myapplication10", scoreListJson);

        Intent intent = new Intent(GameActivity.this, ScoreActivity.class);
        startActivity(intent);
        stopGame();
    }

    private void makeScore() {
        score = new Score();
        score.setScore(points);
        score.setTitle("Score");

        if(isLocationEnabled(this)) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            score.setLatitude(latitude);
            score.setLongitude(longitude);
        }
        else{
            SignalGenerator.getInstance().toast("Your GPS is not activated", Toast.LENGTH_SHORT);
        }

    }

}
