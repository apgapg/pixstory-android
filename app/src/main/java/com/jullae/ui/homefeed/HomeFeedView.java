package com.jullae.ui.homefeed;

import com.jullae.BaseView;

public interface HomeFeedView extends BaseView {
    void onFetchFeedSuccess(HomeFeedModel homeFeedModel);

    void onFetchFeedFail();


    void showProgress();

    void hideProgress();
}
