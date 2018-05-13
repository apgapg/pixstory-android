package com.jullae.ui.home.homeFeed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jullae.R;
import com.jullae.data.db.model.LikesModel;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.custom.ItemOffTBsetDecoration;
import com.jullae.ui.home.homeFeed.freshfeed.HomeFeedAdapter;

public class HomeFeedFragment extends BaseFragment implements HomeFeedView {

    private static final String TAG = HomeFeedFragment.class.getName();
    private View view;
    private HomeFeedPresentor mPresentor;
    private HomeFeedAdapter homeFeedAdapter;
    private SwipeRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.fragment_home_feed, container, false);

        mPresentor = new HomeFeedPresentor();

        swipeRefresh = view.findViewById(R.id.swiperefresh);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        homeFeedAdapter = new HomeFeedAdapter(getmContext(), mPresentor);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ItemOffTBsetDecoration itemDecoration = new ItemOffTBsetDecoration(getmContext(), R.dimen.item_offset_4dp);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(homeFeedAdapter);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresentor.loadFeeds();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresentor.attachView(this);
        mPresentor.loadFeeds();

    }

    @Override
    public void onDestroyView() {
        mPresentor.detachView();

        super.onDestroyView();
    }

    @Override
    public void onFetchFeedSuccess(HomeFeedModel homeFeedModel) {
        for (int i = 0; i < homeFeedModel.getFeedList().size(); i++) {
            for (int j = 0; j < homeFeedModel.getFeedList().get(i).getStories().size(); j++) {
                if (homeFeedModel.getFeedList().get(i).getStories().get(j).getStory_id().equals(homeFeedModel.getFeedList().get(i).getNav_story_id()))
                    homeFeedModel.getFeedList().get(i).setHighlightStoryIndex(j);
            }
        }
        homeFeedAdapter.add(homeFeedModel.getFeedList());
    }

    @Override
    public void onFetchFeedFail() {

    }

    @Override
    public void showProgress() {
        //view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        //view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
        swipeRefresh.setRefreshing(false);

    }

    @Override
    public void onLikesListFetchSuccess(LikesModel likesModel) {
        homeFeedAdapter.onLikesListFetchSuccess(likesModel);
    }

    @Override
    public void onLikesListFetchFail() {
        homeFeedAdapter.onLikesListFetchFail();

    }

    public void refreshFeeds() {
        mPresentor.loadFeeds();
    }
}
