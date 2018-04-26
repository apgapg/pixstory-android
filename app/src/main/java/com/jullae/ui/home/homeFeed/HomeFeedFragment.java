package com.jullae.ui.home.homeFeed;

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
import com.jullae.app.AppController;
import com.jullae.data.db.model.LikesModel;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.custom.ItemOffTBsetDecoration;
import com.jullae.ui.home.homeFeed.freshfeed.HomeFeedAdapter;

public class HomeFeedFragment extends BaseFragment implements HomeFeedView {

    private static final String TAG = HomeFeedFragment.class.getName();
    private View view;
    private HomeFeedPresentor homeFeedPresentor;
    private HomeFeedAdapter homeFeedAdapter;

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

        homeFeedPresentor = new HomeFeedPresentor(((AppController) getmContext().getApplication()).getmAppDataManager());


        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        homeFeedAdapter = new HomeFeedAdapter(getmContext(), homeFeedPresentor);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ItemOffTBsetDecoration itemDecoration = new ItemOffTBsetDecoration(getmContext(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(homeFeedAdapter);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeFeedPresentor.attachView(this);
        homeFeedPresentor.loadFeeds();

    }

    @Override
    public void onDestroyView() {
        homeFeedPresentor.detachView();

        super.onDestroyView();
    }

    @Override
    public void onFetchFeedSuccess(HomeFeedModel homeFeedModel) {
        homeFeedAdapter.add(homeFeedModel.getFeedList());
    }

    @Override
    public void onFetchFeedFail() {

    }

    @Override
    public void showProgress() {
        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLikesListFetchSuccess(LikesModel likesModel) {
        homeFeedAdapter.onLikesListFetchSuccess(likesModel);
    }

    @Override
    public void onLikesListFetchFail() {
        homeFeedAdapter.onLikesListFetchFail();

    }
}
