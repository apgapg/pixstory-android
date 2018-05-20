package com.jullae.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

public class MyActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    private Activity activity;

    public MyActivityLifecycleCallbacks(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (this.activity == activity) {
            Log.i(activity.getClass().getSimpleName(), "onCreate(Bundle)");
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (this.activity == activity) {
            Log.i(activity.getClass().getSimpleName(), "onStart()");
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (this.activity == activity) {
            Log.i(activity.getClass().getSimpleName(), "onResume()");
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (this.activity == activity) {
            Log.i(activity.getClass().getSimpleName(), "onPause()");
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if (this.activity == activity) {
            Log.i(activity.getClass().getSimpleName(), "onSaveInstanceState(Bundle)");
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (this.activity == activity) {
            Log.i(activity.getClass().getSimpleName(), "onStop()");
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (this.activity == activity) {
            Log.i(activity.getClass().getSimpleName(), "onDestroy()");
            activity.getApplication().unregisterActivityLifecycleCallbacks(this);
        }
    }
}

