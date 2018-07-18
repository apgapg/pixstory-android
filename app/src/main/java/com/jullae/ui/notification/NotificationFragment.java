package com.jullae.ui.notification;

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
import com.jullae.data.db.model.NotificationModel;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.custom.ItemOffTBsetDecoration;

import java.util.List;

public class NotificationFragment extends BaseFragment implements NotificationView {

    private View view;
    private NotificationPresentor mPresentor;
    private RecyclerView mRecyclerView;
    private NotificationAdapter notificationAdapter;
    private int visibleItemCount, pastVisiblesItems, getVisibleItemCount, getPastVisiblesItems, totalItemCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.fragment_notification, container, false);

        mPresentor = new NotificationPresentor();
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmContext().finish();
            }
        });
        view.findViewById(R.id.close1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmContext().finish();
            }
        });
        setUpRecyclerView();

        return view;
    }

    private void setUpRecyclerView() {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        ItemOffTBsetDecoration itemDecoration = new ItemOffTBsetDecoration(getmContext(), R.dimen.item_offset_1dp);
        mRecyclerView.addItemDecoration(itemDecoration);
        notificationAdapter = new NotificationAdapter(getmContext());
        mRecyclerView.setAdapter(notificationAdapter);
        setScrollListener();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresentor.attachView(this);
        mPresentor.sendReadNotiReq();
        mPresentor.loadNotifications();

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
                        mPresentor.loadMoreNotifications();

                    }
                }
            }
        });

    }


    @Override
    public void onDestroyView() {
        mPresentor.detachView();
        super.onDestroyView();

    }

    @Override
    public void onNotificationFetchFail() {

    }

    @Override
    public void onNotificationFetchSuccess(List<NotificationModel> notificationModelList) {
        if (notificationModelList != null)
            if (notificationModelList.size() != 0) {
                for (int i = 0; i < notificationModelList.size(); i++)
                    notificationModelList.get(i).setSpannable_text(getmContext());
                notificationAdapter.add(notificationModelList);
            } else {
                view.findViewById(R.id.text_empty).setVisibility(View.VISIBLE);
            }
        else {
            view.findViewById(R.id.text_empty).setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void showProgressBar() {
        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        view.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);

    }

    @Override
    public void showLoadMoreProgess() {
        view.findViewById(R.id.progress_bar2).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadMoreProgess() {
        view.findViewById(R.id.progress_bar2).setVisibility(View.INVISIBLE);

    }


    @Override
    public void onMoreNotificationFetch(List<NotificationModel> notificationModelList) {
        mRecyclerView.stopScroll();
        for (int i = 0; i < notificationModelList.size(); i++)
            notificationModelList.get(i).setSpannable_text(getmContext());
        notificationAdapter.addMore(notificationModelList);
    }
}
