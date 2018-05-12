package com.jullae.ui.storydetails;

import com.jullae.data.db.model.LikesModel;
import com.jullae.data.db.model.StoryModel;
import com.jullae.ui.base.MvpView;

public interface StoryDetailView extends MvpView {
    void onLikesListFetchSuccess(LikesModel likesModel);

    void onLikesListFetchFail();

    void onSaveStorySuccess();

    void onSaveStoryFail();

    void onStoryDetailFetchSuccess(StoryModel storyModel);

    void onStoryDetailFetchFail();
}
