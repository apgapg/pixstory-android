package com.jullae.ui.home.homeFeed.freshfeed;

import com.jullae.data.db.model.FreshFeedModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

public class FreshFeedContract {
    public interface View extends MvpView {
        void onFetchFeeds(List<FreshFeedModel.FreshFeed> list);

        void onFetchFeedsFail();

        void showProgressBar();

        void hideProgressBar();

        void showMoreProgress();

        void hideMoreProgress();

        void onFetchMoreFeeds(List<FreshFeedModel.FreshFeed> list);

    }
}
