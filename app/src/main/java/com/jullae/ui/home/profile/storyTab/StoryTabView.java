package com.jullae.ui.home.profile.storyTab;

import com.jullae.data.db.model.FeedModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

public interface StoryTabView extends MvpView {
    void onStoriesFetchSuccess(List<FeedModel> storyModelList);

    void onStoriesFetchFail();

    void showLoadMoreProgess();

    void hideLoadMoreProgess();

    void onMoreStoriesFetch(List<FeedModel> storyMainModelList);
}
