package com.jullae.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.R;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.FollowersModel;
import com.jullae.data.db.model.FollowingModel;
import com.jullae.data.db.model.StoryModel;
import com.jullae.databinding.DialogFollowersBinding;
import com.jullae.ui.adapters.LikeAdapter;
import com.jullae.ui.storydetails.StoryDetailPresentor;
import com.jullae.utils.customview.PagingRecyclerView;

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

        final EditText field_report = view.findViewById(R.id.field_report);
        final View btn_report = view.findViewById(R.id.report);
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String report = field_report.getText().toString().trim();
                if (report.length() != 0) {

                    view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                    btn_report.setVisibility(View.INVISIBLE);

                    mPresentor.reportStory(report, storyModel.getStory_id(), new StoryDetailPresentor.StringReqListener() {
                        @Override
                        public void onSuccess() {
                            view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                            dialog.dismiss();
                            Toast.makeText(context.getApplicationContext(), "Your report has been submitted!", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFail() {
                            view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                            btn_report.setVisibility(View.VISIBLE);
                            Toast.makeText(context.getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {

                }
            }
        });

        dialog.show();


    }

    public static void showFollowersDialog(final Activity mContext, final String userId) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        final DialogFollowersBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_followers, null, false);
        dialogBuilder.setView(binding.getRoot());

        final AlertDialog dialog = dialogBuilder.create();
        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        final LikeAdapter likeAdapter = new LikeAdapter(mContext, Constants.FOLLOWERS_LIST);
        binding.recyclerView.setAdapter(likeAdapter);
        binding.recyclerView.addReachListBottomListener(new PagingRecyclerView.ReachListBottomListener() {
            private boolean isReqRunning;
            private int count = 2;

            @Override
            public void onReachListBottom() {

                if (!isReqRunning) {
                    isReqRunning = true;
                    binding.progressBar.setVisibility(View.VISIBLE);
                    AppDataManager.getInstance().getmApiHelper().getFollowersList(userId, count).getAsObject(FollowersModel.class, new ParsedRequestListener<FollowersModel>() {
                        @Override
                        public void onResponse(FollowersModel followersModel) {
                            NetworkUtils.parseResponse(TAG, followersModel);
                            isReqRunning = false;
                            count++;
                            if (dialog.isShowing()) {
                                binding.progressBar.setVisibility(View.INVISIBLE);
                                likeAdapter.addMore(followersModel.getLikes());
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            NetworkUtils.parseError(TAG, anError);
                            isReqRunning = false;
                            if (dialog.isShowing()) {
                                binding.progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });
        AppDataManager.getInstance().getmApiHelper().getFollowersList(userId, 1).getAsObject(FollowersModel.class, new ParsedRequestListener<FollowersModel>() {

            @Override
            public void onResponse(FollowersModel followersModel) {
                NetworkUtils.parseResponse(TAG, followersModel);
                if (dialog.isShowing())
                    likeAdapter.add(followersModel.getLikes());
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                ToastUtils.showNoInternetToast(mContext);

            }
        });

    }

    public static void showFollowingDialog(final Activity mContext, final String userId) {


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        final DialogFollowersBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_followers, null, false);
        dialogBuilder.setView(binding.getRoot());

        final AlertDialog dialog = dialogBuilder.create();
        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        final LikeAdapter likeAdapter = new LikeAdapter(mContext, Constants.FOLLOWING_LIST);
        binding.recyclerView.setAdapter(likeAdapter);
        binding.recyclerView.addReachListBottomListener(new PagingRecyclerView.ReachListBottomListener() {
            private boolean isReqRunning;
            private int count = 2;

            @Override
            public void onReachListBottom() {

                if (!isReqRunning) {
                    isReqRunning = true;
                    binding.progressBar.setVisibility(View.VISIBLE);
                    AppDataManager.getInstance().getmApiHelper().getFollowingList(userId, count).getAsObject(FollowingModel.class, new ParsedRequestListener<FollowingModel>() {
                        @Override
                        public void onResponse(FollowingModel followingModel) {
                            NetworkUtils.parseResponse(TAG, followingModel);
                            isReqRunning = false;
                            count++;
                            if (dialog.isShowing()) {
                                binding.progressBar.setVisibility(View.INVISIBLE);
                                likeAdapter.addMore(followingModel.getLikes());
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            NetworkUtils.parseError(TAG, anError);
                            isReqRunning = false;
                            if (dialog.isShowing()) {
                                binding.progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });
        AppDataManager.getInstance().getmApiHelper().getFollowingList(userId, 1).getAsObject(FollowingModel.class, new ParsedRequestListener<FollowingModel>() {

            @Override
            public void onResponse(FollowingModel followingModel) {
                NetworkUtils.parseResponse(TAG, followingModel);
                if (dialog.isShowing())
                    likeAdapter.add(followingModel.getLikes());
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                ToastUtils.showNoInternetToast(mContext);

            }
        });

    }

}
