package com.jullae.ui.profileSelf;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jullae.R;
import com.jullae.app.AppController;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.search.SearchFeedAdapter;

import java.util.List;

public class CommonTabFragment extends BaseFragment implements CommonTabView {

    private static final String TAG = CommonTabFragment.class.getName();
    private View view;
    private RecyclerView recyclerView;
    private SearchFeedAdapter searchFeedAdapter;
    private CommonTabPresentor mPresentor;
    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.fragment_story_tab_profile, container, false);

        position = getArguments().getInt("position");

        mPresentor = new CommonTabPresentor(((AppController) getmContext().getApplication()).getmAppDataManager());

        setuprecyclerView();
        return view;
    }

    private void setuprecyclerView() {
        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        searchFeedAdapter = new SearchFeedAdapter(getmContext());
        recyclerView.setAdapter(searchFeedAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresentor.attachView(this);
        mPresentor.loadFeeds(position);
    }

    @Override
    public void onDestroyView() {
        mPresentor.detachView();
        super.onDestroyView();

    }

    @Override
    public void onStoriesFetchSuccess(List<StoryListModel.StoryMainModel> storyModelList) {
        searchFeedAdapter.add(storyModelList);
    }

    @Override
    public void onStoriesFetchFail() {

    }
}
