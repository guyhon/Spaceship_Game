package com.example.myapplication10;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MenuActivity extends AppCompatActivity {

    public int crateSpeed;
    public int moveSpeed;

    public boolean sensorMode;
    public int points;
   // Button fastButton;
    Button slowButton;
    Button fastButton;

    Button scoreButton;

    Switch sensorsButton;

    private static final int LOCATION_PERMISSION_CODE = 1234;
    public static Boolean locationPermissionsGranted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        fastButton = findViewById(R.id.fastModeButton);
        slowButton =    findViewById(R.id.slowModeButton);
        scoreButton =    findViewById(R.id.scoreButton);
        sensorsButton =  findViewById(R.id.sensorModeButton);

        fastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crateSpeed = 400;
                moveSpeed = 200;
                sensorMode = ifSensors();
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                intent.putExtra("crate_speed", crateSpeed);
                intent.putExtra("move_speed", moveSpeed);
                intent.putExtra("sensor_mode", sensorMode);
                startActivity(intent);

            }
        });

        slowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crateSpeed = 600;
                moveSpeed = 300;
                sensorMode = ifSensors();
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                intent.putExtra("crate_speed", crateSpeed);
                intent.putExtra("move_speed", moveSpeed);
                intent.putExtra("sensor_mode", sensorMode);
                startActivity(intent);
            }

        });

        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                points =0;
                Intent intent = new Intent(MenuActivity.this, ScoreActivity.class);
                //intent.putExtra("points", points);
                startActivity(intent);
            }
        });
        getLocationPermission();

    }

    public void getLocationPermission() {
        String[] permissions = {ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationPermissionsGranted = true;
            }
        }else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_CODE);
        }
    }

    private boolean ifSensors(){
        if(sensorsButton.isChecked())
            return true;
        else
            return false;
    }

}
