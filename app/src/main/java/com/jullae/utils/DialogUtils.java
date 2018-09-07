package com.jullae.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.R;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.PictureModel;
import com.jullae.data.db.model.ProfileModel;
import com.jullae.data.db.model.StoryModel;
import com.jullae.ui.FollowersActivity;
import com.jullae.ui.base.BasePresentor;
import com.jullae.ui.base.BaseResponseModel;
import com.jullae.ui.common.MessageListActivity;
import com.jullae.ui.home.homeFeed.HomeFeedModel;
import com.jullae.ui.home.profile.message.MessageActivity;
import com.jullae.ui.home.profile.profileVisitor.ProfileVisitorPresentor;
import com.jullae.ui.storydetails.StoryDetailPresentor;

public class DialogUtils {
    private static final String TAG = DialogUtils.class.getName();

    public static void showDeleteStoryDialog(Context context, final StoryDetailPresentor mPresentor, final String story_id) {
        MyAlertDialog myAlertDialog = new MyAlertDialog(context, "Delete Story!", "Are you sure you want to delete this story?", "Yes", "No") {
            @Override
            public void onNegativeButtonClick(DialogInterface dialog) {
                dialog.dismiss();
            }

            @Override
            public void onPositiveButtonClick(DialogInterface dialog) {
                dialog.dismiss();
                mPresentor.sendStoryDeleteReq(story_id);
            }
        };
        myAlertDialog.show();
    }

    public static void showReportStoryDialog(final Activity context, final StoryDetailPresentor mPresentor, final StoryModel storyModel) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final View view = context.getLayoutInflater().inflate(R.layout.dialog_report_story, null);
        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();


        view.findViewById(R.id.t1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onReportTextClick(view, dialog, mPresentor, storyModel, context);
            }
        });

        view.findViewById(R.id.t2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onReportTextClick(view, dialog, mPresentor, storyModel, context);
            }
        });

        view.findViewById(R.id.t3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onReportTextClick(view, dialog, mPresentor, storyModel, context);
            }
        });

        view.findViewById(R.id.t4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onReportTextClick(view, dialog, mPresentor, storyModel, context);
            }
        });

        dialog.show();


    }

    private static void onReportTextClick(final View view, final AlertDialog dialog, BasePresentor mPresentor, StoryModel storyModel, final Activity context) {


        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

        mPresentor.reportStory(((TextView) view).getText().toString(), storyModel.getStory_id(), new StoryDetailPresentor.StringReqListener() {
            @Override
            public void onSuccess() {
                view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                dialog.dismiss();

                showReportStorySuccessDialog(context);
                Toast.makeText(context.getApplicationContext(), "Your reportStory has been submitted!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFail() {
                view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                Toast.makeText(context.getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void showReportPictureDialog(final Activity context, final BasePresentor mPresentor, final PictureModel pictureModel) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final View view = context.getLayoutInflater().inflate(R.layout.dialog_report_story, null);
        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();


        view.findViewById(R.id.t1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onReportTextClick(view, dialog, mPresentor, pictureModel, context, ((TextView) view.findViewById(R.id.t1)).getText());
            }
        });

        view.findViewById(R.id.t2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onReportTextClick(view, dialog, mPresentor, pictureModel, context, ((TextView) view.findViewById(R.id.t2)).getText());
            }
        });

        view.findViewById(R.id.t3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onReportTextClick(view, dialog, mPresentor, pictureModel, context, ((TextView) view.findViewById(R.id.t3)).getText());
            }
        });

        view.findViewById(R.id.t4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onReportTextClick(view, dialog, mPresentor, pictureModel, context, ((TextView) view.findViewById(R.id.t4)).getText());
            }
        });

        dialog.show();


    }

    private static void onReportTextClick(final View view, final AlertDialog dialog, BasePresentor mPresentor, PictureModel pictureModel, final Activity context, CharSequence text) {


        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

        mPresentor.reportPicture(text.toString(), pictureModel.getPicture_id(), new StoryDetailPresentor.StringReqListener() {
            @Override
            public void onSuccess() {
                view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                dialog.dismiss();

                showReportStorySuccessDialog(context);
                Toast.makeText(context.getApplicationContext(), "Your reportStory has been submitted!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFail() {
                view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                Toast.makeText(context.getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void showReportPictureDialog(final Activity context, final BasePresentor mPresentor, final HomeFeedModel.Feed pictureModel) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final View view = context.getLayoutInflater().inflate(R.layout.dialog_report_story, null);
        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();


        view.findViewById(R.id.t1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onReportTextClick(view, dialog, mPresentor, pictureModel, context, ((TextView) view.findViewById(R.id.t1)).getText());
            }
        });

        view.findViewById(R.id.t2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onReportTextClick(view, dialog, mPresentor, pictureModel, context, ((TextView) view.findViewById(R.id.t2)).getText());
            }
        });

        view.findViewById(R.id.t3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onReportTextClick(view, dialog, mPresentor, pictureModel, context, ((TextView) view.findViewById(R.id.t3)).getText());
            }
        });

        view.findViewById(R.id.t4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onReportTextClick(view, dialog, mPresentor, pictureModel, context, ((TextView) view.findViewById(R.id.t4)).getText());
            }
        });

        dialog.show();


    }

    private static void onReportTextClick(final View view, final AlertDialog dialog, BasePresentor mPresentor, HomeFeedModel.Feed pictureModel, final Activity context, CharSequence text) {


        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

        mPresentor.reportPicture(text.toString(), pictureModel.getPicture_id(), new StoryDetailPresentor.StringReqListener() {
            @Override
            public void onSuccess() {
                view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                dialog.dismiss();

                showReportStorySuccessDialog(context);
                Toast.makeText(context.getApplicationContext(), "Your reportStory has been submitted!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFail() {
                view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                Toast.makeText(context.getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private static void onReportUserClick(final View view, final AlertDialog dialog, BasePresentor mPresentor, final Activity context, String id) {


        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

        mPresentor.reportUser(((TextView) view).getText().toString(), id, new StoryDetailPresentor.StringReqListener() {
            @Override
            public void onSuccess() {
                view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                dialog.dismiss();

                showReportStorySuccessDialog(context);
                Toast.makeText(context.getApplicationContext(), "Your reportStory has been submitted!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFail() {
                view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                Toast.makeText(context.getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private static void showReportStorySuccessDialog(Activity context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final View view = context.getLayoutInflater().inflate(R.layout.dialog_report_success, null);
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

    public static void showProfileMoreOptions(final Activity context, final ProfileModel profileModel, final ProfileVisitorPresentor mPresentor) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final View view = context.getLayoutInflater().inflate(R.layout.dialog_visitor_profile_options, null);
        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();

        view.findViewById(R.id.report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReportUserDialog(context, mPresentor, profileModel.getId());
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MessageActivity.class);
                i.putExtra("user_id", profileModel.getId());
                i.putExtra("user_name", profileModel.getName());
                i.putExtra("user_avatar", profileModel.getUser_avatar());
                context.startActivity(i);
                dialog.dismiss();

            }
        });
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();


    }

    public static void showPictureMoreOptions(final Activity context, final BasePresentor mPresentor, final PictureModel pictureModel) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View view;
        view = context.getLayoutInflater().inflate(R.layout.picture_options, null);

        if (pictureModel.getIs_self() && (pictureModel.getStory_count() == 0)) {
            view.findViewById(R.id.delete).setVisibility(View.VISIBLE);
        }

        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();


        view.findViewById(R.id.report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showReportPictureDialog(context, mPresentor, pictureModel);
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.view_all_creations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showPictureDetailActivity(context, pictureModel.getPicture_id());
                dialog.dismiss();

            }
        });

        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public static void showPictureMoreOptions(final Activity context, final BasePresentor mPresentor, final HomeFeedModel.Feed pictureModel) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View view;
        view = context.getLayoutInflater().inflate(R.layout.picture_options, null);

        if (pictureModel.getIs_self() && (pictureModel.getStory_count() == 0)) {
            view.findViewById(R.id.delete).setVisibility(View.VISIBLE);
        }

        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();


        view.findViewById(R.id.report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showReportPictureDialog(context, mPresentor, pictureModel);
                dialog.hide();

            }
        });

        view.findViewById(R.id.view_all_creations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showPictureDetailActivity(context, pictureModel.getPicture_id());
                dialog.hide();

            }
        });

        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    public static void showReportUserDialog(final Activity context, final BasePresentor mPresentor, final String id) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final View view = context.getLayoutInflater().inflate(R.layout.dialog_report_story, null);
        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();


        view.findViewById(R.id.t1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onReportUserClick(view, dialog, mPresentor, context, id);
            }
        });

        view.findViewById(R.id.t2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onReportUserClick(view, dialog, mPresentor, context, id);
            }
        });

        view.findViewById(R.id.t3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onReportUserClick(view, dialog, mPresentor, context, id);
            }
        });

        view.findViewById(R.id.t4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onReportUserClick(view, dialog, mPresentor, context, id);
            }
        });

        dialog.show();


    }


    public static void showFollowersDialog(final Activity mContext, final String userId) {

        Intent i = new Intent(mContext, FollowersActivity.class);
        i.putExtra("follower", true);
        i.putExtra("userId", userId);
        mContext.startActivity(i);
    }

    public static void showFollowingDialog(final Activity mContext, final String userId) {
        Intent i = new Intent(mContext, FollowersActivity.class);
        i.putExtra("follower", false);
        i.putExtra("userId", userId);
        mContext.startActivity(i);

    }

    public static void showCommentDeleteWarning(final Context context, final String commentId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to delete this comment?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                MyProgressDialog.showProgressDialog(context, "Please Wait!");
                makeDeleteCommentReq(context, commentId);
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

    private static void makeDeleteCommentReq(final Context context, String commentId) {
        AppDataManager.getInstance().getmApiHelper().makeCommentDeleteReq(commentId).getAsObject(BaseResponseModel.class, new ParsedRequestListener<BaseResponseModel>() {

            @Override
            public void onResponse(BaseResponseModel response) {
                NetworkUtils.parseResponse(TAG, response);
                MyProgressDialog.dismissProgressDialog();
                AppUtils.sendRefreshBroadcast(context, Constants.REFRESH_COMMENT);
                AppUtils.sendRefreshBroadcast(context, Constants.REFRESH_HOME_FEEDS);

            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                MyProgressDialog.dismissProgressDialog();
                ToastUtils.showSomethingWentWrongToast(context);
            }
        });

    }

    public static void showMessageDialog(Activity activity) {

        activity.startActivity(new Intent(activity, MessageListActivity.class));
    }

}
