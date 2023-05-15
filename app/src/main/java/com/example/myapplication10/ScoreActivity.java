package com.example.myapplication10;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication10.Fragments.ListFragment;
import com.example.myapplication10.Fragments.MapFragment;
import com.example.myapplication10.Interfaces.CallBack_SendClick;
import com.google.android.gms.maps.model.LatLng;

public class ScoreActivity extends AppCompatActivity {

    private ListFragment listFragment;
    private MapFragment mapFragment;

    private int points;

    CallBack_SendClick callBack_SendClick = new CallBack_SendClick() {
        @Override
        public void giveLocation(double longitude, double latitude) {
            mapFragment.moveCamera(new LatLng(latitude, longitude), 15f);
        }
    };

//    CallBack_SendClick callBack_SendClick = new CallBack_SendClick() {
//        @Override
//        public void userNameChosen(String name) {
//            showUserLocation(name);
//        }
//    };

//    private void showUserLocation(String name) {
//        mapFragment.zoomOnUser(name);
//    }

//    private static final int LOCATION_PERMISSION_CODE = 1234;
//    private Boolean locationPermissionsGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        initFragments();
        beginTransactions();
    }


    private void initFragments() {
        listFragment = new ListFragment();
        listFragment.setCallBack(callBack_SendClick);
        mapFragment = new MapFragment();
    }

    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, mapFragment).commit();
    }


}
