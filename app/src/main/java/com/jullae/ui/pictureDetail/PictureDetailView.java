package com.jullae.ui.pictureDetail;

import com.jullae.data.db.model.StoryModel;
import com.jullae.ui.base.MvpView;
import com.jullae.ui.home.homeFeed.HomeFeedModel;

import java.util.List;

public interface PictureDetailView extends MvpView {
    void onStoriesFetchSuccess(List<StoryModel> storyModelList);

    void onStoriesFetchFail();

    void onFetchFeedSuccess(HomeFeedModel.Feed feedModel);


    void onFetchFeedFail();
}
