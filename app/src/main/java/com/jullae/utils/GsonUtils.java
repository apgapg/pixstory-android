package com.jullae.utils;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {

    private static GsonUtils sInstance;
    private Gson mGson;

    private GsonUtils() {
        mGson = new GsonBuilder().create();
    }

    public synchronized static GsonUtils getInstance() {
        if (sInstance == null) {
            sInstance = new GsonUtils();
        }
        return sInstance;
    }


    public <T> T fromJson(@NonNull String jsonString, @NonNull Class<T> tClass) {
        return getGson().fromJson(jsonString, tClass);
    }

    public <T> String toJson(@NonNull final T t) {
        return getGson().toJson(t);
    }

    public Gson getGson() {
        return mGson;
    }

}
