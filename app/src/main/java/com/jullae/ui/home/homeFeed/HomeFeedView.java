package com.jullae.ui.home.homeFeed;

import com.jullae.data.db.model.LikesModel;
import com.jullae.ui.base.MvpView;

public interface HomeFeedView extends MvpView {
    void onFetchFeedSuccess(HomeFeedModel homeFeedModel);

    void onFetchFeedFail();

    void showProgress();

    void hideProgress();

    void onLikesListFetchSuccess(LikesModel likesModel);

    void onLikesListFetchFail();

    void onPictureDeleteSuccess();

    void onPictureDeleteFail(String message);
}
