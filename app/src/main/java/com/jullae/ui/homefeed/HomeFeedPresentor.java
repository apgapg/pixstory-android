package com.jullae.ui.homefeed;

import com.jullae.BasePresentor;
import com.jullae.helpers.AppDataManager;

public class HomeFeedPresentor extends BasePresentor {


    private HomeFeedView view;

    public HomeFeedPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }

    public void setView(HomeFeedView view) {
        this.view = view;
    }

    public void loadFeeds() {
        getmAppDataManager().loadHomeFeeds(new FeedFetchListener() {
            @Override
            public void onSuccess(HomeFeedModel homeFeedModel) {
                if (view != null) {
                    view.onFetchFeedSuccess(homeFeedModel);
                }
            }

            @Override
            public void onFail() {
                if (view != null) {
                    view.onFetchFeedFail();
                }
            }
        });
    }

    public void removeView() {
        view = null;
    }

    public interface FeedFetchListener {
        void onSuccess(HomeFeedModel homeFeedModel);

        void onFail();
    }

}
