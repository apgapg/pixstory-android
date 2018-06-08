package com.jullae.ui.home.homeFeed;

import com.jullae.ui.base.MvpView;

public interface HomeFeedView extends MvpView {
    void onFetchFeedSuccess(HomeFeedModel homeFeedModel);

    void onFetchFeedFail();

    void showProgress();

    void hideProgress();



    void onPictureDeleteSuccess();

    void onPictureDeleteFail(String message);

    void showLoadMoreFeedProgress();

    void hideLoadMoreFeedProgress();

    void onFetchMoreFeedFail();

    void onFetchMoreFeedSuccess(HomeFeedModel homeFeedModel);
}
