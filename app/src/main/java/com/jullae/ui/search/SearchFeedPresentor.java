package com.jullae.ui.search;

import com.jullae.BasePresentor;
import com.jullae.helpers.AppDataManager;
import com.jullae.model.FreshFeedModel;

import java.util.List;

public class SearchFeedPresentor extends BasePresentor {


    private SearchFeedContract.View view;

    public SearchFeedPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }

    public void loadFeeds() {
        getmAppDataManager().loadSearchFeeds(new FeedListener() {
            @Override
            public void onSuccess(List<FreshFeedModel> list) {
                if (view != null)
                    view.onFetchFeeds(list);
            }

            @Override
            public void onFail() {
                if (view != null)
                    view.onFetchFeedsFail();
            }
        });
    }

    public void setView(SearchFeedContract.View view) {
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
