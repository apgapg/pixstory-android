package com.jullae.ui.homefeed.freshfeed;

import android.os.Handler;

import com.jullae.BasePresentor;
import com.jullae.helpers.AppDataManager;
import com.jullae.model.FreshFeedModel;

import java.util.List;

public class FreshFeedPresentor extends BasePresentor {


    private FreshFeedContract.View view;

    public FreshFeedPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }

    public void loadFeeds(int position) {
        view.showProgressBar();
        getmAppDataManager().loadFreshFeeds(position, new FeedListener() {
            @Override
            public void onSuccess(final List<FreshFeedModel> list) {
                if (view != null) {
                    view.hideProgressBar();
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            view.onFetchFeeds(list);

                        }
                    });
                }
            }

            @Override
            public void onFail() {
                if (view != null) {
                    view.hideProgressBar();
                    view.onFetchFeedsFail();
                }
            }
        });
    }

    public void setView(FreshFeedContract.View view) {
        this.view = view;
    }

    public void removeView() {
        view = null;
    }

    public interface FeedListener {
        void onSuccess(List<FreshFeedModel> list);

        void onFail();
    }
}
