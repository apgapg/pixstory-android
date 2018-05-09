package com.jullae.ui.home.homeFeed.freshfeed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jullae.R;
import com.jullae.data.db.model.FreshFeedModel;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.custom.ItemOffTBsetDecoration;

import java.util.List;

public class FreshFeedFragment extends BaseFragment implements FreshFeedContract.View {

    private FreshFeedAdapter freshFeedAdapter;
    private FreshFeedPresentor mPresentor;
    private View view;
    private int position;
    private View progressBar;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }


        view = inflater.inflate(R.layout.fragment_fresh_feed, container, false);


        position = getArguments().getInt("position");

        progressBar = view.findViewById(R.id.progress_bar);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        freshFeedAdapter = new FreshFeedAdapter(getmContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ItemOffTBsetDecoration itemDecoration = new ItemOffTBsetDecoration(getmContext(), R.dimen.item_offset_4dp);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(freshFeedAdapter);

        mPresentor = new FreshFeedPresentor();

        return view;
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
    public void onFetchFeeds(List<FreshFeedModel.FreshFeed> list) {
        freshFeedAdapter.add(list);
    }

    @Override
    public void onFetchFeedsFail() {

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);


    }
}
