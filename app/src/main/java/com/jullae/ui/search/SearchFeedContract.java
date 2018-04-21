package com.jullae.ui.search;

import com.jullae.model.FreshFeedModel;

import java.util.List;

public class SearchFeedContract {
    public interface View {
        void onFetchFeeds(List<FreshFeedModel> list);

        void onFetchFeedsFail();
    }
}
