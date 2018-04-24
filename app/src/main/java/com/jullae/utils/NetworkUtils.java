package com.jullae.utils;

import com.androidnetworking.error.ANError;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class NetworkUtils {


    public static final int PASSWORD_WRONG = 703;
    public static final int EMAIL_NOT_REGISTERED = 703;
    public static final int PENNAME_ALREADY_TAKEN = 717;

    public static ErrorResponseModel parseError(String TAG, ANError error) {
        if (error.getErrorCode() != 0) {
            Log.d(TAG, "onError errorCode : " + error.getErrorCode());
            Log.d(TAG, "onError errorBody : " + error.getErrorBody());
            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
            // ApiError apiError = error.getErrorAsObject(ApiError.class);
            ErrorResponseModel errorResponseModel = error.getErrorAsObject(ErrorResponseModel.class);
            return errorResponseModel;
        } else {
            Log.d(TAG, "onError errorDetail : " + error.getErrorDetail());
            if (error.getErrorDetail().equals("connectionError")) {
                return new ErrorResponseModel("false", -20, "couldn't connect!");
            }
        }
        return null;
    }

    public static void parseResponse(String TAG, Object response) {
        if (response instanceof String) {
            Log.d(TAG, "parseResponse: " + response);
        } else if (response instanceof JSONObject) {
            Log.d(TAG, "parseResponse: " + response.toString());
        } else if (response instanceof JSONArray) {
            Log.d(TAG, "parseResponse: " + response.toString());
        } else if (response instanceof List) {
            Log.d(TAG, "parseResponse: " + new Gson().toJson(response));
        } else {
            Log.d(TAG, "parseResponse: " + response.toString());
        }
    }
}
