package com.jullae.ui.home.profile.bookmarkTab;

import com.jullae.data.db.model.FeedModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

public interface BookmarkTabView extends MvpView {
    void onBookmarkFetchSuccess(List<FeedModel> storyModelList);

    void onBookmarkFetchFail();
}
