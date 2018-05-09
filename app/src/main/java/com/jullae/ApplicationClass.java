package com.jullae;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.jullae.data.AppDataManager;

public class ApplicationClass extends Application {

    public static final String TAG = ApplicationClass.class.getSimpleName();
    private AppDataManager mAppDataManager;


    @Override
    public void onCreate() {
        super.onCreate();

        AndroidNetworking.initialize(getApplicationContext());

        AppDataManager.initialize(getApplicationContext());

    }


}
