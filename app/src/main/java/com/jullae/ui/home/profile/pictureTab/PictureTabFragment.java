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

import com.jullae.R;
import com.jullae.data.db.model.PictureModel;
import com.jullae.ui.adapters.PicturesAdapter;
import com.jullae.ui.base.BaseFragment;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;

import java.util.List;

public class PictureTabFragment extends BaseFragment implements PictureTabView {

    private View view;
    private RecyclerView recyclerView;
    private PicturesAdapter picturesAdapter;
    private PictureTabPresentor mPresentor;
    private String penname;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int refreshMode = intent.getIntExtra(Constants.REFRESH_MODE, -1);
            Log.d("receiver", "Got message: " + refreshMode);
            switch (refreshMode) {
                case Constants.REFRESH_PICTURES_TAB:
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
        return view;
    }

    private void setuprecyclerView() {
        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        picturesAdapter = new PicturesAdapter(getmContext());
        recyclerView.setAdapter(picturesAdapter);
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
        picturesAdapter.add(pictureModelList);
    }

    @Override
    public void onPicturesFetchFail() {

    }

    /*public void refreshfeeds() {
        mPresentor.loadFeeds(penname);
    }*/
}
