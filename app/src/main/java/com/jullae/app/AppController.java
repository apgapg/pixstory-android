package com.jullae.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.jullae.helpers.AppDataManager;
import com.jullae.helpers.AppPrefsHelper;

/**
 * Created by macbunny on 22/11/17.
 * <p>
 * Controller for app.
 */

public class AppController extends Application {

    public static final String PACKAGE_NAME = "com.jullae";

    public static final String TAG = AppController.class.getSimpleName();
    //private ImageLoader mImageLoader;
    private static AppController mInstance;
    private RequestQueue mRequestQueue;
    private AppDataManager mAppDataManager;

    /**
     * Get the instance og this class.
     * It act as a singlton so that it cannot perform
     * 2nd action until finish first.
     *
     * @return instance of this class.
     */
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
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

    /*public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }*/

    /**
     * Add request.
     *
     * @param req params
     * @param tag tag
     * @param <T> object
     */
    public <T> void addToRequestQueue(final Request<T> req, final String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * Add request.
     *
     * @param req request.
     * @param <T> object.
     */
    public <T> void addToRequestQueue(final Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * cancel all pending request if exists.
     *
     * @param tag object.
     */
    public void cancelPendingRequests(final Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
