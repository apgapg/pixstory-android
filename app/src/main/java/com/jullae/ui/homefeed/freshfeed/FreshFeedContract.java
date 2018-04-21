package com.jullae.ui.homefeed.freshfeed;

import com.jullae.model.FreshFeedModel;

import java.util.List;

public class FreshFeedContract {
    public interface View {
        void onFetchFeeds(List<FreshFeedModel> list);

        void onFetchFeedsFail();

        void showProgressBar();

        void hideProgressBar();
    }
}
