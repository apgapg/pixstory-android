package com.jullae.ui.profile;

import com.jullae.ui.base.MvpView;

import java.util.List;

public interface StoryTabView extends MvpView {
    void onStoriesFetchSuccess(List<StoryListModel.StoryMainModel> storyModelList);

    void onStoriesFetchFail();
}
