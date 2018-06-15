package com.jullae.ui.comment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.Toast;

import com.jullae.R;
import com.jullae.data.db.model.CommentModel;
import com.jullae.databinding.FragmentCommentBinding;
import com.jullae.ui.adapters.CommentsAdapter;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.storydetails.StoryDetailActivity;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;
import com.jullae.utils.ToastUtils;

import java.util.List;

public class CommentFragment extends BaseFragment implements CommentView {


    private View view;
    private FragmentCommentBinding binding;
    private CommentPresentor mPresentor;
    private CommentsAdapter mAdapter;
    private EditText addCommentField;
    private ImageView btn_add_comment;
    private View progressBarComment;
    private String storyid;
    private RecyclerView mRecyclerView;
    private int visibleItemCount, totalItemCount, getVisibleItemCount, pastVisiblesItems;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int refreshMode = intent.getIntExtra(Constants.REFRESH_MODE, -1);
            Log.d("receiver", "Got message: " + refreshMode);
            switch (refreshMode) {
                case Constants.REFRESH_COMMENT:
                    mPresentor.loadComments(storyid);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment, container, false);
        view = binding.getRoot();

        mPresentor = new CommentPresentor();
        storyid = AppUtils.checkforNull(getArguments().getString("story_id"));

        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((StoryDetailActivity) getContext()).onBackPressed();
            }
        });
        binding.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresentor.loadComments(storyid);
            }
        });
        setupComments();
        setupAddCommentContainer();

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresentor.attachView(this);
        mPresentor.loadComments(AppUtils.checkforNull(storyid));

        setupRefreshBroadcastListener();


    }


    private void setupRefreshBroadcastListener() {
        LocalBroadcastManager.getInstance(getmContext()).registerReceiver(mMessageReceiver,
                new IntentFilter(Constants.REFRESH_INTENT_FILTER));


    }


    @Override
    public void onDestroyView() {
        mPresentor.detachView();
        LocalBroadcastManager.getInstance(getmContext()).unregisterReceiver(mMessageReceiver);

        super.onDestroyView();
    }


    private void setupAddCommentContainer() {
        addCommentField = view.findViewById(R.id.field_add_comment);
        btn_add_comment = view.findViewById(R.id.btn_add_comment);
        progressBarComment = view.findViewById(R.id.progress_bar_comment);
        btn_add_comment.setEnabled(false);

        btn_add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateCommentUI(0);

                mPresentor.sendcommentReq(addCommentField.getText().toString().trim(), storyid);

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
        mRecyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new CommentsAdapter(getmContext());
        mRecyclerView.setAdapter(mAdapter);
        setScrollListener();
    }

    private void setScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                    totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    pastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        Log.v("...", "Last Item Wow !");

                        //Do pagination.. i.e. fetch new data
                        mPresentor.loadMoreComments(storyid);

                    }
                }
            }
        });

    }


    @Override
    public void showLoading() {
        binding.setIsLoading(true);
    }

    @Override
    public void onCommentAddSuccess(CommentModel commentModel) {
        updateCommentUI(1);
        mAdapter.addComment(commentModel);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.scrollToPosition(0);
            }
        });
        AppUtils.sendRefreshBroadcast(getmContext(), Constants.REFRESH_HOME_FEEDS);

    }

    @Override
    public void onCommentAddFail() {
        updateCommentUI(2);
        Toast.makeText(getmContext().getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        binding.setIsLoading(false);
    }

    @Override
    public void onCommentListFetch(List<CommentModel> commentModelList) {
        mAdapter.add(commentModelList);
    }

    @Override
    public void onCommentListFail() {
        ToastUtils.showNoInternetToast(getmContext());

    }

    @Override
    public void onMoreCommentListFetch(List<CommentModel> commentModelList) {
        mRecyclerView.stopScroll();
        mAdapter.addMore(commentModelList);
    }

    @Override
    public void hideLoadingMore() {
        binding.setIsLoadingMore(false);
    }

    @Override
    public void showLoadingMore() {
        binding.setIsLoadingMore(true);
    }
}
