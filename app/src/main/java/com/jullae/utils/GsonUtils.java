package com.jullae.utils;

import com.google.gson.Gson;

public class GsonUtils {

    private static GsonUtils sInstance;
    private Gson mGson;

    private GsonUtils() {
        mGson = new Gson();
    }

    public synchronized static GsonUtils getInstance() {
        if (sInstance == null) {
            sInstance = new GsonUtils();
        }
        return sInstance;
    }


    public String fromJson(String json, T object) {
        return getGson().toJson()
    }

    public Gson getGson() {
        return mGson;
    }

}
