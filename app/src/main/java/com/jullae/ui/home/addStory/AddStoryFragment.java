package com.jullae.ui.home.addStory;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.data.db.model.AddStoryModel;
import com.jullae.ui.base.BaseFragment;

import java.util.List;

public class AddStoryFragment extends BaseFragment implements AddStoryView {

    private View view;
    private SwipeRefreshLayout swipeRefresh;
    private AddStoryPresentor mPresentor;
    private AddStoryAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }


        view = inflater.inflate(R.layout.fragment_add_story, container, false);

        mPresentor = new AddStoryPresentor();

        swipeRefresh = view.findViewById(R.id.swiperefresh);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new AddStoryAdapter(getmContext(), mPresentor);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getmContext(), 2, GridLayoutManager.VERTICAL, false);


     /* ItemOffTBsetDecoration itemDecoration = new ItemOffTBsetDecoration(getmContext(), R.dimen.item_offset_4dp);

        recyclerView.addItemDecoration(itemDecoration);*/
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mAdapter);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresentor.loadData();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresentor.attachView(this);
        mPresentor.loadData();
    }

    @Override
    public void onDestroyView() {
        mPresentor.detachView();

        super.onDestroyView();
    }

    @Override
    public void showProgressBar() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgressBar() {
        swipeRefresh.setRefreshing(false);

    }

    @Override
    public void onListFetch(List<AddStoryModel.PictureModel> picturesList) {
        mAdapter.add(picturesList);
    }

    @Override
    public void onListFetchFail() {
        Toast.makeText(getmContext().getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();

    }
}
