package com.jullae.ui.profileSelf;

import com.jullae.model.FeedModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

public interface CommonTabView extends MvpView {
    void onStoriesFetchSuccess(List<FeedModel> storyModelList);

    void onStoriesFetchFail();
}
