package com.jullae.utils;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by master on 7/4/18.
 */

public class AppUtils {
    public static final String LOCALE_ENGLISH = "en";
    public static final String LOCALE_HINDI = "hi";
    public static final String LOCALE_MARATHI = "mr";

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
