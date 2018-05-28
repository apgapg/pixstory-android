package com.jullae.ui.storydetails;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jullae.ApplicationClass;
import com.jullae.R;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.CommentModel;
import com.jullae.data.db.model.LikesModel;
import com.jullae.data.db.model.StoryModel;
import com.jullae.databinding.FragmentStoryDetailsBinding;
import com.jullae.ui.adapters.CommentsAdapter;
import com.jullae.ui.adapters.LikeAdapter;
import com.jullae.ui.base.BaseFragment;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;
import com.jullae.utils.DialogUtils;
import com.jullae.utils.GsonUtils;
import com.jullae.utils.MyProgressDialog;
import com.jullae.utils.NetworkUtils;
import com.jullae.utils.ToastUtils;

public class StoryDetailFragment extends BaseFragment implements StoryDetailView {

    private static final int ACTIVATE = 1;
    private static final int IN_ACTIVATE = 0;
    private static final String TAG = StoryDetailFragment.class.getName();
    private ImageView btn_like;
    private TextView like_count;
    private StoryModel storyModel;
    private TextView user_followed;
    private CommentsAdapter commentsAdapter;
    private EditText addCommentField;
    private ImageView btn_add_comment;
    private View progressBarComment;
    private BottomSheetBehavior<View> mBottomSheetBehavior;

    private View view;
    private StoryDetailPresentor mPresentor;
    private View btn_close;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int refreshMode = intent.getIntExtra(Constants.REFRESH_MODE, -1);
            Log.d("receiver", "Got message: " + refreshMode);
            switch (refreshMode) {
                case Constants.REFRESH_STORY:
                    mPresentor.loadStoryDetails(storyModel.getStory_id());

                    break;

            }
        }
    };
    private FragmentStoryDetailsBinding binding;
    private LikeAdapter likeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_story_details, container, false);
        view = binding.getRoot();

        btn_close = view.findViewById(R.id.close);
        like_count = view.findViewById(R.id.like_count);
        setupAddComment();
        getArgumentsData();
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmContext().finish();
            }
        });

        setStoryContainerHeight();
        view.findViewById(R.id.text_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((StoryDetailActivity) getmContext()).showSearchResults(storyModel.getStory_title());

            }
        });
        view.findViewById(R.id.ivMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        mPresentor = new StoryDetailPresentor();

        return view;
    }


    private void setStoryContainerHeight() {
        ((TextView) view.findViewById(R.id.story_text)).setMinHeight((int) AppUtils.convertdpTopx((int) (((ApplicationClass) getmContext().getApplication()).getDpHeight() - 204)));
    }

    private void getArgumentsData() {
        AppUtils.checkforNull(getArguments());
        if (getArguments().getString("story_id") != null) {
            storyModel = new StoryModel();
            storyModel.setStory_id(getArguments().getString("story_id"));
        } else if (getArguments().getString("storymodel") != null) {
            storyModel = GsonUtils.getInstance().fromJson(getArguments().getString("storymodel"), StoryModel.class);
            binding.setStoryModel(this.storyModel);
        }
    }


    private void setUpFollowedButton() {
        user_followed = view.findViewById(R.id.user_followed);
        user_followed.setVisibility(View.VISIBLE);

        if (storyModel.getIs_followed().equals("true")) {
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
        mPresentor.loadStoryDetails(storyModel.getStory_id());
        setupRefreshBroadcastListener();
    }


    private void setupRefreshBroadcastListener() {
        LocalBroadcastManager.getInstance(getmContext()).registerReceiver(mMessageReceiver,
                new IntentFilter(Constants.REFRESH_INTENT_FILTER));
    }

    @Override
    public void onDestroyView() {
        mPresentor.detachView();
        super.onDestroyView();
    }


    private void setupMoreBottomSheet() {
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        if (storyModel.getWriter_id().equals(AppDataManager.getInstance().getmSharedPrefsHelper().getKeyUserId()))
            view.findViewById(R.id.container_extra_options).setVisibility(View.VISIBLE);

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


        view.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });

        view.findViewById(R.id.edit_story).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeBottomSheet();

                AppUtils.showEditStoryActivity(getmContext(), storyModel.getStory_id(), storyModel.getStory_title(), storyModel.getStory_text());
            }
        });
        view.findViewById(R.id.delete_story).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeBottomSheet();
                DialogUtils.showDeleteStoryDialog(getmContext(), mPresentor, storyModel.getStory_id());
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
                        DialogUtils.showReportStoryDialog(getmContext(), mPresentor, storyModel);
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
                    public void onSuccess(CommentModel commentModel) {
                        updateCommentUI(1);

                        commentsAdapter.addComment(commentModel);
                        AppUtils.sendRefreshBroadcast(getmContext(), Constants.REFRESH_HOME_FEEDS);
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


        commentsAdapter.add(storyModel.getCommentModelList());
    }

    private void makeUserFollowReq() {
        Boolean is_followed;
        is_followed = !storyModel.getIs_followed().equals("false");

        mPresentor.makeFollowUserReq(storyModel.getWriter_id(), new FollowReqListener() {
            @Override
            public void onSuccess() {

                stopReqProgress();


                if (storyModel.getIs_followed().equals("false")) {
                    storyModel.setIs_followed("true");
                    updateUIFollowed();
                } else {
                    storyModel.setIs_followed("false");

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

    private void setupLike() {
        btn_like = view.findViewById(R.id.btn_like);
        btn_like.setVisibility(View.VISIBLE);
        like_count.setText(storyModel.getLike_count() + " likes");
        if (storyModel.getIs_liked().equals("false")) {
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
                String isLiked = storyModel.getIs_liked();
                makeLikeRequest(isLiked, storyModel);
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
                if (!storyModel.getLike_count().equals("0"))

                    AppUtils.showLikesDialog(getmContext(), storyModel.getStory_id(), Constants.LIKE_TYPE_STORY);
            }
        });
    }

    private void updatetoUnlike() {
        storyModel.setIs_liked("false");
        btn_like.setImageResource(R.drawable.ic_unlike);
        if (Integer.parseInt(storyModel.getLike_count()) != 0) {
            like_count.setText(String.valueOf(Integer.parseInt(storyModel.getLike_count()) - 1) + " likes");
            storyModel.setLike_count(String.valueOf(Integer.parseInt(storyModel.getLike_count()) - 1));
        }
        like_count.setTextColor(Color.parseColor("#9e9e9e"));
        like_count.setTypeface(Typeface.DEFAULT);

    }

    private void updateToLike() {
        storyModel.setIs_liked("true");
        btn_like.setImageResource(R.drawable.ic_like);
        like_count.setText(String.valueOf(Integer.parseInt(storyModel.getLike_count()) + 1) + " likes");
        storyModel.setLike_count(String.valueOf(Integer.parseInt(storyModel.getLike_count()) + 1));
        like_count.setTextColor(Color.parseColor("#424242"));
        like_count.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private void makeLikeRequest(String isLiked, final StoryModel storyModel) {
        /*mPresentor.setLike(storyModel.getStory_id(), new StoryDetailPresentor.StringReqListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFail() {
                if (storyModel.getIs_liked().equals("false"))
                    updateToLike();
                else updatetoUnlike();
                Toast.makeText(getmContext().getApplicationContext(), "couldn't connect!", Toast.LENGTH_SHORT).show();
            }
        }, isLiked);*/
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
        AppUtils.sendRefreshBroadcast(getmContext(), Constants.REFRESH_BOOKMARKS_TAB);
    }

    @Override
    public void onSaveStoryFail() {
        Toast.makeText(getmContext().getApplicationContext(), R.string.something_wrong, Toast.LENGTH_SHORT).show();

    }

    public boolean closeBottomSheet() {
        if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return true;
        }
        return false;
    }

    @Override
    public void onStoryDetailFetchSuccess(StoryModel storyModel) {
        this.storyModel = storyModel;
        binding.setStoryModel(this.storyModel);

        if (!storyModel.getIs_self())
            setUpFollowedButton();
        setupMoreBottomSheet();
        setupComments();
        setupLike();
    }

    @Override
    public void onStoryDetailFetchFail() {
        ToastUtils.showNoInternetToast(getmContext());
        NetworkUtils.registerNetworkChangeListener(getmContext(), new NetworkUtils.NetworkChangeListener() {
            @Override
            public void onNetworkAvailable() {
                mPresentor.loadStoryDetails(storyModel.getStory_id());
            }
        });

    }

    @Override
    public void showProgress() {
        MyProgressDialog.showProgressDialog(getmContext(), "Please Wait!");
    }

    @Override
    public void hideProgress() {
        MyProgressDialog.dismissProgressDialog();
    }

    @Override
    public void onStoryDeleteSuccess() {
        Toast.makeText(getmContext().getApplicationContext(), "Story deleted successfully!", Toast.LENGTH_SHORT).show();
        getmContext().finish();
    }

    @Override
    public void onStoryDeleteFail(String message) {
        Toast.makeText(getmContext().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
