package com.jullae.ui.homefeed.freshfeed;

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
import com.jullae.model.FreshFeedModel;
import com.jullae.ui.base.BaseFragment;

import java.util.List;

public class FreshFeedFragment extends BaseFragment implements FreshFeedContract.View {

    private FreshFeedAdapter freshFeedAdapter;
    private FreshFeedPresentor freshFeedPresentor;
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
        //ItemOffTBsetDecoration itemDecoration = new ItemOffTBsetDecoration(mContext, R.dimen.item_offset);
        ///recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(freshFeedAdapter);

        freshFeedPresentor = new FreshFeedPresentor(((AppController) getmContext().getApplication()).getmAppDataManager());
        freshFeedPresentor.setView(this);
        freshFeedPresentor.loadFeeds(position);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        freshFeedPresentor.removeView();
    }

    @Override
    public void onFetchFeeds(List<FreshFeedModel> list) {
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
