package com.jullae.ui.profileSelf;

import com.jullae.ui.base.MvpView;

import java.util.List;

public interface CommonTabView extends MvpView {
    void onStoriesFetchSuccess(List<StoryListModel.StoryMainModel> storyModelList);

    void onStoriesFetchFail();
}
