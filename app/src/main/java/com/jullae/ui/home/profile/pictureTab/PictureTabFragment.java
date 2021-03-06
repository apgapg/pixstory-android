package com.jullae.ui.home.profile.pictureTab;

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
import android.widget.Button;
import android.widget.TextView;

import com.jullae.R;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.PictureModel;
import com.jullae.ui.adapters.PicturesTabAdapter;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.home.HomeActivity;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;

import java.util.List;

public class PictureTabFragment extends BaseFragment implements PictureTabView {

    private View view;
    private RecyclerView mRecyclerView;
    private PicturesTabAdapter mAdapter;
    private PictureTabPresentor mPresentor;
    private String penname;
    private int visibleItemCount, pastVisiblesItems, totalItemCount;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int refreshMode = intent.getIntExtra(Constants.REFRESH_MODE, -1);
            Log.d("receiver", "Got message: " + refreshMode);
            switch (refreshMode) {
                case Constants.REFRESH_PICTURES_TAB:
                    mPresentor.loadFeeds(penname);
                    break;
                case Constants.REFRESH_HOME_FEEDS:
                    mPresentor.loadFeeds(penname);
                    break;
                case Constants.REFRESH_PROFILE1:
                    mPresentor.loadFeeds(penname);
                    break;
                case Constants.REFRESH_PROFILE:
                    mPresentor.loadFeeds(penname);
                    break;

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_story_tab_profile, container, false);
        penname = getArguments().getString("penname");
        AppUtils.checkforNullPenname(penname);

        mPresentor = new PictureTabPresentor();
        setuprecyclerView();
        view.findViewById(R.id.discover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getmContext()).showAddImageOption();
            }
        });
        return view;
    }

    private void setuprecyclerView() {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter = new PicturesTabAdapter(getmContext(), mPresentor);
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
                        mPresentor.loadMoreFeeds(penname);

                    }
                }
            }
        });

    }


    private void setupRefreshBroadcastListener() {
        LocalBroadcastManager.getInstance(getmContext()).registerReceiver(mMessageReceiver,
                new IntentFilter(Constants.REFRESH_INTENT_FILTER));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresentor.attachView(this);
        mPresentor.loadFeeds(penname);
        setupRefreshBroadcastListener();

    }

    @Override
    public void onDestroyView() {
        mPresentor.detachView();
        LocalBroadcastManager.getInstance(getmContext()).unregisterReceiver(mMessageReceiver);

        super.onDestroyView();

    }

    @Override
    public void onPicturesFetchSuccess(List<PictureModel> pictureModelList) {
        if (pictureModelList.size() > 0) {
            view.findViewById(R.id.empty).setVisibility(View.INVISIBLE);

            mAdapter.add(pictureModelList);
        } else {
            view.findViewById(R.id.empty).setVisibility(View.VISIBLE);
            if (!penname.equals(AppDataManager.getInstance().getmSharedPrefsHelper().getKeyPenname())) {
                ((TextView) view.findViewById(R.id.message)).setText("No pictures to show");
                view.findViewById(R.id.discover).setVisibility(View.GONE);
            } else {
                ((TextView) view.findViewById(R.id.message)).setText("You have not shared any picture with the Community. Share one now!");
                ((Button) view.findViewById(R.id.discover)).setText("Add Picture");
            }
        }
    }

    @Override
    public void onPicturesFetchFail() {

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
    public void onMorePicturesFetchSuccess(List<PictureModel> pictureModelList) {
        mRecyclerView.stopScroll();
        mAdapter.addMore(pictureModelList);

    }

}
