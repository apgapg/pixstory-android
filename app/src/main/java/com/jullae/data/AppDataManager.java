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

    public AppDataManager(Context context) {
        SharedPrefsHelper.initialize(context);
        mApiHelper = new ApiHelper(SharedPrefsHelper.getInstance().getKeyToken());
    }

    public static AppDataManager getInstance() {
        return uniqueInstance;
    }

    public static void initialize(Context applicationContext) {
        if (applicationContext == null)
            throw new NullPointerException("Provided application context is null");
        else if (uniqueInstance == null) {
            synchronized (SharedPrefsHelper.class) {
                uniqueInstance = new AppDataManager(applicationContext);
            }

        }
    }

    public SharedPrefsHelper getmSharedPrefsHelper() {
        return SharedPrefsHelper.getInstance();
    }

    public ApiHelper getmApiHelper() {
        return mApiHelper;
    }
}
