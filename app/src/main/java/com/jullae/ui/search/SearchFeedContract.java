package com.jullae.ui.search;

import com.jullae.data.db.model.FeedModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

public class SearchFeedContract {
    public interface View extends MvpView {
        void onFetchFeedsSuccess(List<FeedModel> list);

        void onFetchFeedsFail();
    }
}
