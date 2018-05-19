package com.jullae;

import android.app.Application;
import android.util.DisplayMetrics;

import com.androidnetworking.AndroidNetworking;
import com.jullae.data.AppDataManager;

public class ApplicationClass extends Application {

    public static final String TAG = ApplicationClass.class.getSimpleName();
    public static float density;
    private float dpWidth, dpHeight;

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidNetworking.initialize(getApplicationContext());

        AppDataManager.initialize(getApplicationContext());


        setScreenSize();
    }

    private void setScreenSize() {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        density = displayMetrics.density;
    }

    public float getDpHeight() {
        return dpHeight;
    }

    public float getDpWidth() {
        return dpWidth;
    }


    public float getDensity() {
        return density;
    }
}



