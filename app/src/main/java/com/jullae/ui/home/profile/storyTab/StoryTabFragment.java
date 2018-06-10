package com.jullae.ui.home.profile.storyTab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jullae.R;
import com.jullae.data.db.model.FeedModel;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.search.SearchFeedAdapter;
import com.jullae.utils.AppUtils;

import java.util.List;

public class StoryTabFragment extends BaseFragment implements StoryTabView {

    private static final String TAG = StoryTabFragment.class.getName();
    private View view;
    private RecyclerView mRecyclerView;
    private SearchFeedAdapter mAdapter;
    private StoryTabPresentor mPresentor;
    private String penname;
    private int visibleItemCount, pastVisiblesItems, getVisibleItemCount, getPastVisiblesItems, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.fragment_story_tab_profile, container, false);
        penname = getArguments().getString("penname");
        AppUtils.checkforNullPenname(penname);


        mPresentor = new StoryTabPresentor();

        setuprecyclerView();
        return view;
    }

    private void setuprecyclerView() {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new SearchFeedAdapter(getmContext(), mPresentor);
        mRecyclerView.setAdapter(mAdapter);
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
                        mPresentor.loadMoreFeeds(penname);

                    }
                }
            }
        });

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresentor.attachView(this);
        mPresentor.loadFeeds(penname);
    }

    @Override
    public void onDestroyView() {
        mPresentor.detachView();
        super.onDestroyView();

    }

    @Override
    public void onStoriesFetchSuccess(List<FeedModel> storyModelList) {
        mAdapter.add(storyModelList);
    }

    @Override
    public void onStoriesFetchFail() {

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
    public void onMoreStoriesFetch(List<FeedModel> storyMainModelList) {
        mRecyclerView.stopScroll();
        mAdapter.addAll(storyMainModelList);
    }
}
