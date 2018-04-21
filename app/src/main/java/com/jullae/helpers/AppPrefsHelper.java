package com.jullae.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.jullae.utils.AppUtils;

import static com.jullae.app.AppController.PACKAGE_NAME;

/**
 * Created by master on 1/4/18.
 */

public class AppPrefsHelper {

    public static final String KEY_LOCALE = "locale";
    private static final String PREFS = PACKAGE_NAME + ".PREFS";
    private static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    private static final String KEY_UID = "UID";
    private static final String KEY_AUTH_TOKEN = "AUTHTOKEN";
    private static final String KEY_MOBILE_NUMBER = "NUMBER";
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

    public void saveUserDetails(String uid, String authtoken, String number) {
        sharedPreferences.edit().putString(KEY_UID, uid).commit();
        sharedPreferences.edit().putString(KEY_AUTH_TOKEN, authtoken).commit();
        sharedPreferences.edit().putString(KEY_MOBILE_NUMBER, number).commit();
        setLoggedInMode(true);
    }

    public String getUid() {
        return sharedPreferences.getString(KEY_UID, null);
    }

    public String getAuthToken() {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null);
    }

    public String getNumber() {
        return sharedPreferences.getString(KEY_MOBILE_NUMBER, null);
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
}
