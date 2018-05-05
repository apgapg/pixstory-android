package com.jullae.ui.storydetails;

import android.app.AlertDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jullae.ApplicationClass;
import com.jullae.R;
import com.jullae.data.db.model.LikesModel;
import com.jullae.data.db.model.StoryCommentModel;
import com.jullae.data.db.model.StoryModel;
import com.jullae.ui.adapters.CommentsAdapter;
import com.jullae.ui.adapters.LikeAdapter;
import com.jullae.ui.base.BaseFragment;
import com.jullae.utils.Constants;

public class StoryDetailFragment extends BaseFragment implements StoryDetailView {

    private static final int ACTIVATE = 1;
    private static final int IN_ACTIVATE = 0;
    private ImageView btn_like;
    private TextView like_count;
    private StoryModel storyModel;
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
    private View view;
    private StoryDetailPresentor mPresentor;
    private StoryCommentModel.StoryDetailModel storyDetailModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.content_story_details, container, false);

        Gson gson = new Gson();
        storyModel = gson.fromJson(getArguments().getString("storymodel"), StoryModel.class);

        mPresentor = new StoryDetailPresentor(((ApplicationClass) getmContext().getApplication()).getmAppDataManager());


        TextView story_tag = view.findViewById(R.id.title);
        ImageView btn_close = view.findViewById(R.id.tvClose);
        TextView user_name = view.findViewById(R.id.text_name);
        ImageView user_image = view.findViewById(R.id.image_avatar);
        TextView user_penname = view.findViewById(R.id.text_penname);
        TextView story_text = view.findViewById(R.id.story_text);
        like_count = view.findViewById(R.id.like_count);
        comment_count = view.findViewById(R.id.comment_count);


        btn_more = view.findViewById(R.id.btn_more);

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setupMoreBottomSheet();
        setupAddComment();



        story_tag.setText(storyModel.getStory_title());
        user_name.setText(storyModel.getWriter_name());
        user_penname.setText(storyModel.getWriter_name());
        story_text.setText(storyModel.getStory_text());

        comment_count.setText(storyModel.getComment_count() + " comments");

        Glide.with(this).load(storyModel.getWriter_avatar()).into(user_image);


        return view;
    }

    private void setUpFollowedButton() {
        user_followed = view.findViewById(R.id.user_followed);
        user_followed.setVisibility(View.VISIBLE);
        if (storyDetailModel.getIs_followed().equals("true")) {
            updateUIFollowed();
        } else {
            updatUIUnFollowed();
        }

        user_followed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReqProgress();
                makeUserFollowReq();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresentor.attachView(this);
        setupComments();

    }

    @Override
    public void onDestroyView() {
        mPresentor.detachView();
        super.onDestroyView();
    }


    private void setupMoreBottomSheet() {
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED)
                    view.findViewById(R.id.bg).setVisibility(View.GONE);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                view.findViewById(R.id.bg).setVisibility(View.VISIBLE);
                view.findViewById(R.id.bg).setAlpha(slideOffset);
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

        view.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });
        view.findViewById(R.id.bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        view.findViewById(R.id.report_story).setOnClickListener(new View.OnClickListener() {
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

        view.findViewById(R.id.text_save_story).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                mPresentor.saveStory(storyModel.getStory_id());
            }
        });
    }

    private void showReportStoryDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getmContext());
        final View view3 = getLayoutInflater().inflate(R.layout.dialog_report_story, null);
        dialogBuilder.setView(view3);

        final AlertDialog dialog = dialogBuilder.create();

        dialog.show();
        field_report = view3.findViewById(R.id.field_report);
        btn_report = view3.findViewById(R.id.report);
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String report = field_report.getText().toString().trim();
                if (report.length() != 0) {

                    view3.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                    btn_report.setVisibility(View.INVISIBLE);

                    mPresentor.reportStory(report, storyModel.getStory_id(), new StoryDetailPresentor.StringReqListener() {
                        @Override
                        public void onSuccess() {
                            view3.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                            dialog.dismiss();
                            Toast.makeText(getmContext().getApplicationContext(), "Your report has been submitted!", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFail() {
                            view3.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                            btn_report.setVisibility(View.VISIBLE);
                            Toast.makeText(getmContext().getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {

                }
            }
        });

    }

    private void setupAddComment() {
        addCommentField = view.findViewById(R.id.field_add_comment);
        btn_add_comment = view.findViewById(R.id.btn_add_comment);
        progressBarComment = view.findViewById(R.id.progress_bar_comment);
        btn_add_comment.setEnabled(false);

        btn_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateCommentUI(0);

                mPresentor.sendcommentReq(addCommentField.getText().toString().trim(), storyModel.getStory_id(), new StoryDetailPresentor.ReqListener() {
                    @Override
                    public void onSuccess(StoryCommentModel.Comment commentModel) {
                        updateCommentUI(1);
                        commentsAdapter.addComment(commentModel);
                    }

                    @Override
                    public void onFail() {
                        updateCommentUI(2);
                        Toast.makeText(getmContext().getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();

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
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        commentsAdapter = new CommentsAdapter(getmContext());
        recyclerView.setAdapter(commentsAdapter);

        mPresentor.loadComments(storyModel.getStory_id(), new StoryDetailPresentor.CommentsListener() {
            @Override
            public void onSuccess(StoryCommentModel storyCommentModel) {
                storyDetailModel = storyCommentModel.getStoryDetailModel();
                setUpFollowedButton();
                setupLike(storyCommentModel.getStoryDetailModel());
                commentsAdapter.add(storyCommentModel.getStoryDetailModel().getComments());
            }

            @Override
            public void onFail() {
                Toast.makeText(getmContext().getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void makeUserFollowReq() {
        Boolean is_followed;
        is_followed = !storyDetailModel.getIs_followed().equals("false");

        mPresentor.makeFollowUserReq(storyModel.getWriter_id(), new FollowReqListener() {
            @Override
            public void onSuccess() {

                stopReqProgress();


                if (storyDetailModel.getIs_followed().equals("false")) {
                    storyDetailModel.setIs_followed("true");
                    updateUIFollowed();
                } else {
                    storyDetailModel.setIs_followed("false");

                    updatUIUnFollowed();
                }
            }

            @Override
            public void onFail() {
                stopReqProgress();
                Toast.makeText(getmContext().getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        }, is_followed);


    }

    private void showReqProgress() {
        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        user_followed.setVisibility(View.INVISIBLE);

    }

    private void updatUIUnFollowed() {
        user_followed.setText("Follow");
        user_followed.setTextColor(getResources().getColor(R.color.black75));
        user_followed.setBackground(getResources().getDrawable(R.drawable.button_border));

    }

    private void updateUIFollowed() {
        user_followed.setText("Followed");
        user_followed.setTextColor(Color.parseColor("#ffffff"));
        user_followed.setBackground(getResources().getDrawable(R.drawable.button_active));

    }

    private void stopReqProgress() {
        view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
        user_followed.setVisibility(View.VISIBLE);
    }

    private void setupLike(final StoryCommentModel.StoryDetailModel storyDetailModel) {
        btn_like = view.findViewById(R.id.btn_like);
        btn_like.setVisibility(View.VISIBLE);
        like_count.setText(storyModel.getLike_count() + " likes");
        if (storyDetailModel.getIs_liked().equals("false")) {
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
                String isLiked = storyDetailModel.getIs_liked();
                makeLikeRequest(isLiked, storyDetailModel);
                if (isLiked.equals("false")) {
                    updateToLike(storyDetailModel);
                } else {
                    updatetoUnlike(storyDetailModel);
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

    private void updatetoUnlike(StoryCommentModel.StoryDetailModel storyDetailModel) {
        storyDetailModel.setIs_liked("false");
        btn_like.setImageResource(R.drawable.ic_unlike);
        if (Integer.parseInt(storyModel.getLike_count()) != 0) {
            like_count.setText(String.valueOf(Integer.parseInt(storyModel.getLike_count()) - 1) + " likes");
            storyModel.setLike_count(String.valueOf(Integer.parseInt(storyModel.getLike_count()) - 1));
        }
        like_count.setTextColor(Color.parseColor("#9e9e9e"));
        like_count.setTypeface(Typeface.DEFAULT);

    }

    private void updateToLike(StoryCommentModel.StoryDetailModel storyDetailModel) {

        storyDetailModel.setIs_liked("true");
        btn_like.setImageResource(R.drawable.ic_like);
        like_count.setText(String.valueOf(Integer.parseInt(storyModel.getLike_count()) + 1) + " likes");
        storyModel.setLike_count(String.valueOf(Integer.parseInt(storyModel.getLike_count()) + 1));
        like_count.setTextColor(Color.parseColor("#424242"));
        like_count.setTypeface(Typeface.DEFAULT_BOLD);

    }

    private void makeLikeRequest(String isLiked, final StoryCommentModel.StoryDetailModel storyDetailModel) {
        mPresentor.setLike(storyModel.getStory_id(), new StoryDetailPresentor.StringReqListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFail() {
                if (storyDetailModel.getIs_liked().equals("false"))
                    updateToLike(storyDetailModel);
                else updatetoUnlike(storyDetailModel);
                Toast.makeText(getmContext().getApplicationContext(), "couldn't connect!", Toast.LENGTH_SHORT).show();
            }
        }, isLiked);
    }

    private void showLikesDialog(String id) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getmContext());
        View view2 = getLayoutInflater().inflate(R.layout.dialog_likes, null);

        setupRecyclerView(view2, id);
        dialogBuilder.setView(view2);

        final AlertDialog dialog = dialogBuilder.create();
        view2.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void setupRecyclerView(View view2, String id) {
        RecyclerView recyclerView = view2.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        likeAdapter = new LikeAdapter(getmContext(), mPresentor);
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
    public void onSaveStorySuccess() {
        Toast.makeText(getmContext().getApplicationContext(), "Story added to bookmark!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveStoryFail() {
        Toast.makeText(getmContext().getApplicationContext(), R.string.something_wrong, Toast.LENGTH_SHORT).show();

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
