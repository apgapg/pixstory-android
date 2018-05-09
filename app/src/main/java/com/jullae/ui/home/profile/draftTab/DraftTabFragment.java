package com.jullae.ui.home.profile.draftTab;

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
import com.jullae.data.db.model.DraftModel;
import com.jullae.ui.base.BaseFragment;

import java.util.List;

public class DraftTabFragment extends BaseFragment implements DraftTabView {

    private static final String TAG = DraftTabFragment.class.getName();
    private View view;
    private RecyclerView recyclerView;
    private DraftTabPresentor mPresentor;
    private DraftTabAdapter draftTabAdapter;

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
        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        draftTabAdapter = new DraftTabAdapter(getmContext(), mPresentor);
        recyclerView.setAdapter(draftTabAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresentor.attachView(this);
        mPresentor.loadDrafts();
    }

    @Override
    public void onDestroyView() {
        mPresentor.detachView();
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
}
