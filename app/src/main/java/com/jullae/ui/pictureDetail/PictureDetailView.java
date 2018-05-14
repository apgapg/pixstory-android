package com.jullae.ui.pictureDetail;

import com.jullae.data.db.model.HomeFeedSingleModel;
import com.jullae.data.db.model.StoryModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

public interface PictureDetailView extends MvpView {
    void onStoriesFetchSuccess(List<StoryModel> storyModelList);

    void onStoriesFetchFail();

    void onFetchFeedSuccess(HomeFeedSingleModel.Feed homeFeedModel);


    void onFetchFeedFail();
}
