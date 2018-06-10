package com.jullae.ui.home.homeFeed.freshfeed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.data.db.model.FreshFeedModel;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.custom.ItemOffTBsetDecoration;
import com.jullae.utils.Constants;

import java.util.List;

public class FreshFeedFragment extends BaseFragment implements FreshFeedContract.View {

    private FreshFeedAdapter mAdapter;
    private FreshFeedPresentor mPresentor;
    private View view;
    private int position;
    private SwipeRefreshLayout swipeRefresh;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int refreshMode = intent.getIntExtra(Constants.REFRESH_MODE, -1);
            Log.d("receiver", "Got message: " + refreshMode);
            switch (refreshMode) {
                case Constants.REFRESH_HOME_FEEDS:
                    mPresentor.loadFeeds(position);
                    break;
            }
        }
    };
    private RecyclerView recyclerView;
    private int visibleItemCount, pastVisiblesItems, getVisibleItemCount, getPastVisiblesItems, totalItemCount;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }


        view = inflater.inflate(R.layout.fragment_fresh_feed, container, false);


        position = getArguments().getInt("position");
        swipeRefresh = view.findViewById(R.id.swiperefresh);
        recyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new FreshFeedAdapter(getmContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ItemOffTBsetDecoration itemDecoration = new ItemOffTBsetDecoration(getmContext(), R.dimen.item_offset_4dp);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresentor.loadFeeds(position);
            }
        });
        setScrollListener();
        mPresentor = new FreshFeedPresentor();

        return view;
    }

    private void setScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        mPresentor.loadMoreFeeds(position);

                    }
                }
            }
        });

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresentor.attachView(this);
        mPresentor.loadFeeds(position);
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
    public void onFetchFeeds(List<FreshFeedModel.FreshFeed> list) {
        mAdapter.add(list);
    }

    @Override
    public void onFetchFeedsFail() {
        Toast.makeText(getmContext().getApplicationContext(), "No internet connectivity", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        //  progressBar.setVisibility(View.VISIBLE);
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        //     progressBar.setVisibility(View.INVISIBLE);
        swipeRefresh.setRefreshing(false);

    }

    @Override
    public void showMoreProgress() {
        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMoreProgress() {
        view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);

    }

    @Override
    public void onFetchMoreFeeds(List<FreshFeedModel.FreshFeed> list) {
        mAdapter.addMore(list);
    }
}
