package com.jullae.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;

import com.jullae.SearchActivity;
import com.jullae.ui.adapters.PicturesAdapter;
import com.jullae.ui.home.profile.profileVisitor.ProfileVisitorActivity;
import com.jullae.ui.pictureDetail.PictureDetailActivity;
import com.jullae.ui.storydetails.StoryDetailActivity;

import java.util.List;

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


    public static void showVisitorProfile(Context mContext, String writer_penname) {
        mContext.startActivity(buildVisitorProfileActivityIntent(mContext, writer_penname));
    }

    public static Intent buildVisitorProfileActivityIntent(Context mContext, String writer_penname) {
        Intent i = new Intent(mContext, ProfileVisitorActivity.class);
        i.putExtra("penname", writer_penname);
        return i;

    }

    public static void checkforNull(List<String> strings) {
        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i) == null || strings.get(i).isEmpty())
                throw new NullPointerException("Field is empty at position: " + i);
        }
    }

    public static void checkforNull(Object object) {

        if (object == null)
            throw new NullPointerException("Object is null");


    }

    /**
     * Show search activity.
     *
     * @param activity  the activity
     * @param searchTag the search tag
     */
    public static void showSearchActivity(Activity activity, String searchTag) {
        Intent i = new Intent(activity, SearchActivity.class);
        i.putExtra("searchtag", searchTag);
        activity.startActivity(i);
    }

    public static void showStoryDetailActivity(Context context, String story_id) {
        context.startActivity(buildStoryDetailActivityIntent(context, story_id));

    }

    public static Intent buildStoryDetailActivityIntent(Context context, String story_id) {
        Intent i = new Intent(context, StoryDetailActivity.class);
        i.putExtra("story_id", story_id);
        return i;

    }

    public static Intent buildPictureDetailActivityIntent(Context context, String picture_id) {
        Intent i = new Intent(context, PictureDetailActivity.class);
        i.putExtra("picture_id", picture_id);
        return i;
    }

    public static void showPictureDetailActivity(Activity mContext, String picture_id) {
        mContext.startActivity(buildPictureDetailActivityIntent(mContext, picture_id));
    }
}
