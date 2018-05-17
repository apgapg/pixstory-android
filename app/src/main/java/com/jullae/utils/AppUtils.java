package com.jullae.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.ApplicationClass;
import com.jullae.R;
import com.jullae.SearchActivity;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.LikesModel;
import com.jullae.data.db.model.PictureModel;
import com.jullae.ui.adapters.LikeAdapter;
import com.jullae.ui.home.profile.profileVisitor.ProfileVisitorActivity;
import com.jullae.ui.loginscreen.LoginActivity;
import com.jullae.ui.pictureDetail.PictureDetailActivity;
import com.jullae.ui.storydetails.StoryDetailActivity;
import com.jullae.ui.writeStory.WriteStoryActivity;

import java.util.List;

/**
 * Created by master on 7/4/18.
 */

public class AppUtils {
    public static final String LOCALE_ENGLISH = "en";
    public static final String LOCALE_HINDI = "hi";
    public static final String LOCALE_MARATHI = "mr";
    public static final int REQUEST_CODE_WRTIE_STORY = 23;
    public static final int REQUEST_CODE_SEARCH_TAG = 33;
    public static final int REQUEST_CODE_WRITESTORY_FROM_PICTURE_TAB = 27;
    private static final String TAG = AppUtils.class.getName();

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void showWriteStoryDialog(Activity mContext, final String picture_id) {

        Intent i = new Intent(mContext, WriteStoryActivity.class);
        i.putExtra("picture_id", picture_id);
        mContext.startActivity(i);
    }

    public static void showWriteStoryDialogWithData(Activity mContext, final String picture_id, String story_title, String story_text) {

        Intent i = new Intent(mContext, WriteStoryActivity.class);
        i.putExtra("picture_id", picture_id);
        i.putExtra("story_title", story_title);
        i.putExtra("story_text", story_text);
        mContext.startActivity(i);
    }


    public static void showVisitorProfile(Context mContext, String writer_penname) {
        mContext.startActivity(buildVisitorProfileActivityIntent(mContext, writer_penname));
    }

    public static Intent buildVisitorProfileActivityIntent(Context mContext, String writer_penname) {
        {
            Intent i = new Intent(mContext, ProfileVisitorActivity.class);
            i.putExtra("penname", writer_penname);
            return i;
        }

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

    public static void checkforNullPenname(String penname) {
        if (penname == null) {
            throw new NullPointerException("penname cannot be null!");
        }
    }


    public static void showLikesDialog(Activity mContext, String picture_id, int likeTypePicture) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        View view = mContext.getLayoutInflater().inflate(R.layout.dialog_likes, null);

        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        final LikeAdapter likeAdapter = new LikeAdapter(mContext);
        recyclerView.setAdapter(likeAdapter);
        AppDataManager.getInstance().getmApiHelper().getLikesList(picture_id, likeTypePicture).getAsObject(LikesModel.class, new ParsedRequestListener<LikesModel>() {

            @Override
            public void onResponse(LikesModel likesModel) {
                NetworkUtils.parseResponse(TAG, likesModel);
                likeAdapter.add(likesModel.getLikes());
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);

            }
        });


    }

    public static void showFullPictureDialog(Activity mContext, PictureModel pictureModel) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        View view = mContext.getLayoutInflater().inflate(R.layout.dialog_full_picture, null);
        ImageView image = view.findViewById(R.id.image);
        ImageView user_photo = view.findViewById(R.id.user_photo);
        TextView user_name = view.findViewById(R.id.text_name);
        TextView pic_title = view.findViewById(R.id.pic_title);
        TextView pic_like_count = view.findViewById(R.id.pic_like_count);
        TextView pic_story_count = view.findViewById(R.id.pic_comment_count);
        user_name.setText(pictureModel.getPhotographer_name());
        pic_title.setText(pictureModel.getPicture_title());
        pic_like_count.setText(pictureModel.getLike_count() + " likes");
        pic_story_count.setText(pictureModel.getStory_count() + " stories");

        GlideUtils.loadImagefromUrl(mContext, pictureModel.getPicture_url(), image);
        GlideUtils.loadImagefromUrl(mContext, pictureModel.getPhotographer_avatar(), user_photo);
        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
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
}