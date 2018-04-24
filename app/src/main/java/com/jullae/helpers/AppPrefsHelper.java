package com.jullae.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.jullae.model.UserPrefsModel;
import com.jullae.utils.AppUtils;

import static com.jullae.app.AppController.PACKAGE_NAME;

/**
 * Created by master on 1/4/18.
 */

public class AppPrefsHelper {

    public static final String KEY_LOCALE = "locale";
    private static final String PREFS = PACKAGE_NAME + ".PREFS";
    private static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String KEY_PENNAME = "penname";
    private static final String KEY_BIO = "bio";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_DP_URL = "dp_url";

    private final SharedPreferences sharedPreferences;
    private final Context mContext;

    public AppPrefsHelper(Context context) {
        this.mContext = context;
        sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }


    public boolean getLoggedInMode() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    private void setLoggedInMode(boolean loggedIn) {
        sharedPreferences.edit().putBoolean("IS_LOGGED_IN", loggedIn).apply();
    }

    public void saveUserDetails(String avatar, String name, String penname, String bio, String user_id, String token) {
        sharedPreferences.edit().putString(KEY_NAME, name).commit();
        sharedPreferences.edit().putString(KEY_PENNAME, penname).commit();
        sharedPreferences.edit().putString(KEY_BIO, bio).commit();
        sharedPreferences.edit().putString(KEY_USER_ID, user_id).commit();
        sharedPreferences.edit().putString(KEY_TOKEN, token).commit();
        sharedPreferences.edit().putString(KEY_DP_URL, avatar).commit();
        setLoggedInMode(true);
    }


    public String getKeyName() {
        return sharedPreferences.getString(KEY_NAME, "");
    }

    public String getKeyPenname() {
        return sharedPreferences.getString(KEY_PENNAME, "");
    }

    public String getKeyToken() {
        return sharedPreferences.getString(KEY_TOKEN, "");
    }

    public String getKeyBio() {
        return sharedPreferences.getString(KEY_BIO, "");
    }

    public String getKeyUserId() {
        return sharedPreferences.getString(KEY_USER_ID, "");
    }


    public String getKeyDpUrl() {
        return sharedPreferences.getString(KEY_DP_URL, "");
    }

    public void putinsharedprefBoolean(String key, boolean value) {

        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public Boolean getvaluefromsharedprefBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void putinsharedprefString(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }

    public String getvaluefromsharedprefString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public String getDeviceId() {
        return AppUtils.getDeviceId(mContext);
    }

    public void updateLocale(String localeCode) {
        putinsharedprefString(KEY_LOCALE, localeCode);

    }


    public UserPrefsModel getPrefsUserData() {
        return new UserPrefsModel(getKeyName(), getKeyPenname(), getKeyBio(), getKeyDpUrl());
    }
}
