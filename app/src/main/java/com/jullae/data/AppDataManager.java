package com.jullae.data;


import android.content.Context;

import com.jullae.data.network.ApiHelper;
import com.jullae.data.prefs.SharedPrefsHelper;

/**
 * Created by master on 7/4/18.
 */

public class AppDataManager {

    private static AppDataManager uniqueInstance;
    private final ApiHelper mApiHelper;

    private AppDataManager(Context context) {

        //Prevent form the reflection api.
        if (uniqueInstance != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");

        SharedPrefsHelper.initialize(context);
        mApiHelper = new ApiHelper(SharedPrefsHelper.getInstance().getKeyToken());
    }

    public static AppDataManager getInstance() {
        if (uniqueInstance == null)
            throw new NullPointerException("Please call initialize() before getting the instance.");
        return uniqueInstance;
    }

    public synchronized static void initialize(Context applicationContext) {
        if (applicationContext == null)
            throw new NullPointerException("Provided application context is null");
        else if (uniqueInstance == null) {
            uniqueInstance = new AppDataManager(applicationContext);
        }
    }

    public SharedPrefsHelper getmSharedPrefsHelper() {
        return SharedPrefsHelper.getInstance();
    }

    public ApiHelper getmApiHelper() {
        return mApiHelper;
    }
}
