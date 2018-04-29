package com.jullae.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;

import com.jullae.ui.adapters.PicturesAdapter;
import com.jullae.ui.home.profile.profileVisitor.ProfileVisitorActivity;

/**
 * Created by master on 7/4/18.
 */

public class AppUtils {
    public static final String LOCALE_ENGLISH = "en";
    public static final String LOCALE_HINDI = "hi";
    public static final String LOCALE_MARATHI = "mr";
    private static final String TAG = AppUtils.class.getName();

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void showWriteStoryDialog(Activity mContext, final String picture_id, final PicturesAdapter.ReqListener reqListener) {


    }


    public static void showVisitorProfile(Activity mContext, String writer_penname) {
        Intent i = new Intent(mContext, ProfileVisitorActivity.class);
        i.putExtra("penname", writer_penname);
        mContext.startActivity(i);
    }
}
