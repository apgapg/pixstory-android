package com.jullae.ui.storydetails;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jullae.R;
import com.jullae.app.AppController;
import com.jullae.model.CommentModel;
import com.jullae.model.LikesModel;
import com.jullae.ui.adapters.CommentsAdapter;
import com.jullae.ui.adapters.LikeAdapter;
import com.jullae.ui.base.BaseActivity;
import com.jullae.ui.homefeed.HomeFeedModel;
import com.jullae.utils.Constants;

/**
 * Created by Rahul Abrol on 12/20/17.
 * <p>
 * Class @{@link StoryDetailActivity} used to show the
 * details of the comments and story that user wrote on the
 * picture.
 */

public class StoryDetailActivity extends BaseActivity implements StoryDetailView {


    private static final int ACTIVATE = 1;
    private static final int IN_ACTIVATE = 0;
    private StoryDetailPresentor mPresentor;
    private ImageView btn_like;
    private TextView like_count;
    private HomeFeedModel.Story storyModel;
    private TextView user_followed;
    private TextView comment_count;
    private CommentsAdapter commentsAdapter;
    private EditText addCommentField;
    private ImageView btn_add_comment;
    private View progressBarComment;
    private View btn_more;
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private EditText field_report;
    private TextView btn_report;
    private LikeAdapter likeAdapter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_story_details);

        mPresentor = new StoryDetailPresentor(((AppController) getApplication()).getmAppDataManager());
        mPresentor.attachView(this);

        TextView story_tag = findViewById(R.id.tvYourTitle);
        ImageView btn_close = findViewById(R.id.tvClose);
        TextView user_name = findViewById(R.id.user_name);
        ImageView user_image = findViewById(R.id.user_image);
        TextView user_penname = findViewById(R.id.user_penname);
        TextView story_text = findViewById(R.id.story_text);
        like_count = findViewById(R.id.like_count);
        comment_count = findViewById(R.id.comment_count);

        user_followed = findViewById(R.id.user_followed);

        btn_more = findViewById(R.id.btn_more);

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setupMoreBottomSheet();
        setupAddComment();


        ImageView ivEditStory = findViewById(R.id.ivEditStory);
        btn_like = findViewById(R.id.btn_like);
        ImageView ivLikeUserPic = findViewById(R.id.user_image);

        if (getIntent() != null) {
            Intent i = getIntent();
            Gson gson = new Gson();
            storyModel = gson.fromJson(i.getStringExtra("object"), HomeFeedModel.Story.class);
        }

        story_tag.setText(storyModel.getStory_title());
        user_name.setText(storyModel.getWriter_name());
        user_penname.setText(storyModel.getWriter_name());
        story_text.setText(storyModel.getContent());

        comment_count.setText(storyModel.getStory_comment_count() + " comments");

        Glide.with(this).load(storyModel.getWriter_dp_Url()).into(user_image);

        if (storyModel.getIs_followed().equals("true")) {
            user_followed.setTextColor(Color.parseColor("#ffffff"));
            user_followed.setBackground(getResources().getDrawable(R.drawable.button_active));
        } else {
            user_followed.setTextColor(getResources().getColor(R.color.black75));
            user_followed.setBackground(getResources().getDrawable(R.drawable.button_border));
        }

        user_followed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReqProgress();
                makeUserFollowReq();
            }
        });

        setupLike();

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setupComments();
    }

    private void setupMoreBottomSheet() {
        View bottomSheet = findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                    findViewById(R.id.bg).setVisibility(View.GONE);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                findViewById(R.id.bg).setVisibility(View.VISIBLE);
                findViewById(R.id.bg).setAlpha(slideOffset);
            }
        });

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });
        findViewById(R.id.bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        findViewById(R.id.report_story).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        showReportStoryDialog();

                    }
                });
            }
        });
    }

    private void showReportStoryDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_report_story, null);
        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();

        dialog.show();
        field_report = view.findViewById(R.id.field_report);
        btn_report = view.findViewById(R.id.report);
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
                            Toast.makeText(getApplicationContext(), "Your report has been submitted!", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFail() {
                            view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                            btn_report.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {

                }
            }
        });

    }

    private void setupAddComment() {
        addCommentField = findViewById(R.id.field_add_comment);
        btn_add_comment = findViewById(R.id.btn_add_comment);
        progressBarComment = findViewById(R.id.progress_bar_comment);
        btn_add_comment.setEnabled(false);

        btn_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateCommentUI(0);

                mPresentor.sendcommentReq(addCommentField.getText().toString().trim(), storyModel.getStory_id(), new StoryDetailPresentor.ReqListener() {
                    @Override
                    public void onSuccess(CommentModel.Comment commentModel) {
                        updateCommentUI(1);
                        commentsAdapter.addComment(commentModel);
                    }

                    @Override
                    public void onFail() {
                        updateCommentUI(2);
                        Toast.makeText(getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


        addCommentField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() != 0) {
                    btn_add_comment.setImageResource(R.drawable.ic_send_active_24dp);
                    btn_add_comment.setEnabled(true);
                } else {
                    btn_add_comment.setImageResource(R.drawable.ic_send_inactive_24dp);
                    btn_add_comment.setEnabled(false);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateCommentUI(int mode) {
        if (mode == 0) {
            progressBarComment.setVisibility(View.VISIBLE);
            addCommentField.setEnabled(false);
            btn_add_comment.setVisibility(View.INVISIBLE);
        } else if (mode == 1) {
            progressBarComment.setVisibility(View.INVISIBLE);

            addCommentField.setText("");
            addCommentField.setEnabled(true);
            btn_add_comment.setVisibility(View.VISIBLE);
        } else {
            progressBarComment.setVisibility(View.INVISIBLE);

            addCommentField.setEnabled(true);
            btn_add_comment.setVisibility(View.VISIBLE);
        }
    }


    private void setupComments() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        commentsAdapter = new CommentsAdapter(this);
        recyclerView.setAdapter(commentsAdapter);

        mPresentor.loadComments(storyModel.getStory_id(), new StoryDetailPresentor.CommentsListener() {
            @Override
            public void onSuccess(CommentModel commentModel) {
                commentsAdapter.add(commentModel.getComments());
            }

            @Override
            public void onFail() {
                Toast.makeText(getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void makeUserFollowReq() {
        mPresentor.makeFollowUserReq(storyModel.getWriter_id(), new FollowReqListener() {
            @Override
            public void onSuccess() {

                stopReqProgress();
                if (storyModel.getIs_followed().equals("true")) {
                    updateToFollowed();
                } else {
                    updateToUnFollowed();
                }
            }

            @Override
            public void onFail() {
                stopReqProgress();
                Toast.makeText(getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void showReqProgress() {
        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        user_followed.setVisibility(View.INVISIBLE);

    }

    private void updateToUnFollowed() {
        storyModel.setIs_followed("false");

        user_followed.setTextColor(getResources().getColor(R.color.black75));
        user_followed.setBackground(getResources().getDrawable(R.drawable.button_border));

    }

    private void updateToFollowed() {
        storyModel.setIs_followed("true");
        user_followed.setTextColor(Color.parseColor("#ffffff"));
        user_followed.setBackground(getResources().getDrawable(R.drawable.button_active));

    }

    private void stopReqProgress() {
        findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
        user_followed.setVisibility(View.VISIBLE);
    }

    private void setupLike() {
        like_count.setText(storyModel.getStory_like_count() + " likes");
        if (storyModel.getStory_is_liked().equals("false")) {
            btn_like.setImageResource(R.drawable.ic_unlike);
            like_count.setTextColor(Color.parseColor("#9e9e9e"));
            like_count.setTypeface(Typeface.DEFAULT);
        } else {
            btn_like.setImageResource(R.drawable.ic_like);
            like_count.setTextColor(Color.parseColor("#424242"));
            like_count.setTypeface(Typeface.DEFAULT_BOLD);
        }

        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isLiked = storyModel.getStory_is_liked();
                makeLikeRequest(isLiked);
                if (isLiked.equals("false")) {
                    updateToLike();
                } else {
                    updatetoUnlike();
                }


            }
        });

        like_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLikesDialog(storyModel.getStory_id());
            }
        });
    }

    private void updatetoUnlike() {
        storyModel.setStory_is_liked("false");
        btn_like.setImageResource(R.drawable.ic_unlike);
        if (Integer.parseInt(storyModel.getStory_like_count()) != 0) {
            like_count.setText(String.valueOf(Integer.parseInt(storyModel.getStory_like_count()) - 1) + " likes");
            storyModel.setStory_like_count(String.valueOf(Integer.parseInt(storyModel.getStory_like_count()) - 1));
        }
        like_count.setTextColor(Color.parseColor("#9e9e9e"));
        like_count.setTypeface(Typeface.DEFAULT);

    }

    private void updateToLike() {

        storyModel.setStory_is_liked("true");
        btn_like.setImageResource(R.drawable.ic_like);
        like_count.setText(String.valueOf(Integer.parseInt(storyModel.getStory_like_count()) + 1) + " likes");
        storyModel.setStory_like_count(String.valueOf(Integer.parseInt(storyModel.getStory_like_count()) + 1));
        like_count.setTextColor(Color.parseColor("#424242"));
        like_count.setTypeface(Typeface.DEFAULT_BOLD);

    }

    private void makeLikeRequest(String isLiked) {
        mPresentor.setLike(storyModel.getStory_id(), new ReqListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFail() {
                if (storyModel.getStory_is_liked().equals("false"))
                    updateToLike();
                else updatetoUnlike();
                Toast.makeText(getApplicationContext(), "couldn't connect!", Toast.LENGTH_SHORT).show();
            }
        }, isLiked);
    }

    private void showLikesDialog(String id) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_likes, null);

        setupRecyclerView(view, id);
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

    private void setupRecyclerView(View view, String id) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        likeAdapter = new LikeAdapter(this, mPresentor);
        recyclerView.setAdapter(likeAdapter);

        mPresentor.getLikeslist(id, Constants.LIKE_TYPE_STORY);
    }


    @Override
    public void onLikesListFetchSuccess(LikesModel likesModel) {
        likeAdapter.add(likesModel.getLikes());
    }

    @Override
    public void onLikesListFetchFail() {

    }

    @Override
    public void onBackPressed() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mPresentor.detachView();
        super.onDestroy();
    }


    public interface FollowReqListener {
        void onSuccess();

        void onFail();
    }


    public interface ReqListener {
        void onSuccess();

        void onFail();
    }
}
