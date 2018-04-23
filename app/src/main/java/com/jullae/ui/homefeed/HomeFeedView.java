package com.jullae.ui.homefeed;

import com.jullae.model.LikesModel;
import com.jullae.ui.base.MvpView;

public interface HomeFeedView extends MvpView {
    void onFetchFeedSuccess(HomeFeedModel homeFeedModel);

    void onFetchFeedFail();

    void showProgress();

    void hideProgress();

    void onLikesListFetchSuccess(LikesModel likesModel);

    void onLikesListFetchFail();
}
