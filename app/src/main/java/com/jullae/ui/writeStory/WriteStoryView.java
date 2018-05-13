package com.jullae.ui.writeStory;

import com.jullae.ui.base.MvpView;

public interface WriteStoryView extends MvpView {
    void onStoryPublishFail();

    void onStoryPublishSuccess();

    void hideProgressBar();

    void showProgressBar();

    void onStoryDraftFail();

    void onStoryDraftSuccess();

    void onTitleEmpty();

    void onContentEmpty();
}
