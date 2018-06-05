package com.jullae.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GsonUtils {


    private static GsonUtils sInstance;
    private Gson mGson;

    private GsonUtils() {
        mGson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();
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

class DateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        String date = element.getAsString();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        // format.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            return format.parse(date);
        } catch (ParseException exp) {
            Log.d("Failed to parse Date:", exp.getMessage());
            return null;
        }
    }
}
