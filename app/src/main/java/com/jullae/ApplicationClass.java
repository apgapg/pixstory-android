package com.jullae;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.jullae.data.AppDataManager;
import com.jullae.data.prefs.AppPrefsHelper;

public class ApplicationClass extends Application {

    public static final String PACKAGE_NAME = "com.jullae";

    public static final String TAG = ApplicationClass.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private AppDataManager mAppDataManager;


    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
        AppPrefsHelper appPrefsHelper = new AppPrefsHelper(getApplicationContext());
        mAppDataManager = new AppDataManager(appPrefsHelper);

    }

    public AppDataManager getmAppDataManager() {
        return mAppDataManager;
    }

    /**
     * Api hit.
     *
     * @return Object
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


}
