package com.example.myapplication10;

import android.app.Application;
import com.example.myapplication10.Utilities.ImageLoader;
public class App  extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        SignalGenerator.init(this);
        ImageLoader.initImageLoader(this);
        MySharedPreferences.init(this);

    }
}
