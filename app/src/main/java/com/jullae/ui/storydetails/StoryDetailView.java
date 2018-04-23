package com.jullae.ui.storydetails;

import com.jullae.model.LikesModel;
import com.jullae.ui.base.MvpView;

public interface StoryDetailView extends MvpView {
    void onLikesListFetchSuccess(LikesModel likesModel);

    void onLikesListFetchFail();
}
