package com.jullae.ui.home.profile.draftTab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jullae.R;
import com.jullae.data.db.model.DraftModel;
import com.jullae.ui.base.BaseFragment;
import com.jullae.utils.Constants;

import java.util.List;

public class DraftTabFragment extends BaseFragment implements DraftTabView {

    private static final String TAG = DraftTabFragment.class.getName();
    private View view;
    private RecyclerView mRecyclerView;
    private DraftTabPresentor mPresentor;
    private DraftTabAdapter draftTabAdapter;
    private int visibleItemCount, pastVisiblesItems, getVisibleItemCount, getPastVisiblesItems, totalItemCount;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int refreshMode = intent.getIntExtra(Constants.REFRESH_MODE, -1);
            Log.d("receiver", "Got message: " + refreshMode);
            switch (refreshMode) {
                case Constants.REFRESH_DRAFTS_TAB:
                    mPresentor.loadDrafts();
                    break;

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.fragment_story_tab_profile, container, false);


        mPresentor = new DraftTabPresentor();

        setuprecyclerView();
        return view;
    }

    private void setuprecyclerView() {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        draftTabAdapter = new DraftTabAdapter(getmContext(), mPresentor);
        mRecyclerView.setAdapter(draftTabAdapter);
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
                        mPresentor.loadMoreDrafts();

                    }
                }
            }
        });

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresentor.attachView(this);
        mPresentor.loadDrafts();
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


    @Override
    public void onDraftsFetchSuccess(List<DraftModel.FreshFeed> list) {
        Log.d(TAG, "onDraftsFetchSuccess: ");
        draftTabAdapter.add(list);
    }

    @Override
    public void onDraftsFetchFail() {

    }

    @Override
    public void showLoadMoreProgess() {
        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadMoreProgess() {
        view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);

    }

    @Override
    public void onMoreDraftsFetch(List<DraftModel.FreshFeed> list) {
        mRecyclerView.stopScroll();
        draftTabAdapter.addMore(list);
    }
}
