package com.jullae.ui.homefeed.freshfeed;

import com.jullae.model.FreshFeedModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

public class FreshFeedContract {
    public interface View extends MvpView{
        void onFetchFeeds(List<FreshFeedModel.FreshFeed> list);

        void onFetchFeedsFail();

        void showProgressBar();

        void hideProgressBar();
    }
}
