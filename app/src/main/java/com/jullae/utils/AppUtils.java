package com.jullae.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.jullae.ApplicationClass;
import com.jullae.SearchActivity;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.PushNotificationModel;
import com.jullae.ui.common.LikesActivity;
import com.jullae.ui.editStory.EditStoryActivity;
import com.jullae.ui.home.profile.message.MessageActivity;
import com.jullae.ui.home.profile.profileVisitor.ProfileVisitorActivity;
import com.jullae.ui.loginscreen.LoginActivity;
import com.jullae.ui.pictureDetail.PictureDetailActivity;
import com.jullae.ui.storydetails.StoryDetailActivity;
import com.jullae.ui.writeStory.WriteStoryActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;

/**
 * Created by master on 7/4/18.
 */

public class AppUtils {

    public static final int REQUEST_CODE_WRTIE_STORY = 23;
    public static final int REQUEST_CODE_SEARCH_TAG = 33;
    public static final int REQUEST_CODE_WRITESTORY_FROM_PICTURE_TAB = 27;
    public static final int REQUEST_CODE_PROFILE_PIC_CAPTURE = 423;
    private static final String TAG = AppUtils.class.getName();

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public static void showWriteStoryDialog(Activity mContext, String pictureurl, final String picture_id, String story_title, String story_text) {

        Intent i = new Intent(mContext, WriteStoryActivity.class);
        i.putExtra("picture_id", picture_id);
        i.putExtra("image", pictureurl);
        i.putExtra("story_title", story_title);
        i.putExtra("story_text", story_text);
        mContext.startActivity(i);
    }


    public static void showVisitorProfile(Context mContext, String writer_penname) {
        Intent i = new Intent(mContext, ProfileVisitorActivity.class);
        i.putExtra("penname", writer_penname);
        mContext.startActivity(i);
    }

    public static Intent buildVisitorProfileActivityIntent(Context mContext, String writer_penname) {
        {
            Intent i = new Intent(mContext, ProfileVisitorActivity.class);
            i.putExtra("penname", writer_penname);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            return i;
        }

    }

    public static void checkforNull(List<String> strings) {
        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i) == null || strings.get(i).isEmpty())
                throw new NullPointerException("Field is empty at position: " + i);
        }
    }

    public static <T> T checkforNull(T object) {

        if (object == null)
            throw new NullPointerException("Object is null");
        else return object;

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
        // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        return i;

    }

    public static Intent buildPictureDetailActivityIntent(Context context, String picture_id) {
        Intent i = new Intent(context, PictureDetailActivity.class);
        i.putExtra("picture_id", picture_id);
        //   i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        return i;
    }

    public static Intent buildMessageActivityIntent(Context context, PushNotificationModel pushNotificationModel) {
        Intent i = new Intent(context, MessageActivity.class);
        i.putExtra("user_id", pushNotificationModel.getActor_id());
        i.putExtra("user_name", pushNotificationModel.getActor_name());
        i.putExtra("user_avatar", pushNotificationModel.getActor_avatar());
        //   i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        return i;
    }


    public static void showPictureDetailActivity(Activity mContext, String picture_id) {
        mContext.startActivity(buildPictureDetailActivityIntent(mContext, picture_id));
    }

    public static void checkforNullPenname(String penname) {
        if (penname == null) {
            throw new NullPointerException("penname cannot be null!");
        }
    }


    public static void showLikesDialog(Activity mContext, String picture_id, int likeType) {

        Intent i = new Intent(mContext, LikesActivity.class);
        i.putExtra("pictureid", picture_id);
        i.putExtra("liketype", likeType);
        mContext.startActivity(i);


    }


    public static void sendRefreshBroadcast(Context context, int refreshCode) {
        Log.d(TAG, "sendRefreshBroadcast: ");
        Intent intent = new Intent(Constants.REFRESH_INTENT_FILTER);
        intent.putExtra(Constants.REFRESH_MODE, refreshCode);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


    public static void showLogoutDialog(final Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you really want to Logout?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppDataManager.getInstance().getmSharedPrefsHelper().clear();
                context.startActivity(new Intent(context, LoginActivity.class));
                context.finishAffinity();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        //creating alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static float convertdpTopx(int i) {
        return ApplicationClass.density * i;
    }

    public static void showEditStoryActivity(Activity activity, String story_id, String story_title, String story_text) {
        Intent i = new Intent(activity, EditStoryActivity.class);
        i.putExtra("story_id", story_id);
        i.putExtra("story_title", story_title);
        i.putExtra("story_text", story_text);
        activity.startActivity(i);
    }

    public static void startImagePickActivity(Fragment context) {
        Intent i = CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setFixAspectRatio(true)
                .setRequestedSize(500, 500)
                .setMinCropResultSize(200, 200)
                .getIntent(context.getContext());

        context.startActivityForResult(i, AppUtils.REQUEST_CODE_PROFILE_PIC_CAPTURE);
    }

    public static void startImagePickActivity(Activity context) {
        Intent i = CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setFixAspectRatio(true)

                .setRequestedSize(500, 500)
                .setMinCropResultSize(200, 200)
                .getIntent(context);

        context.startActivityForResult(i, AppUtils.REQUEST_CODE_PROFILE_PIC_CAPTURE);
    }


    public interface LikeClickListener {
        void onLikeClick();
    }
}