package com.jullae.ui;

import com.jullae.ui.base.MvpView;

public interface DialogActivityView extends MvpView {
    void onStoryPublishFail();

    void onStoryPublishSuccess();

    void hideProgressBar();

    void showProgressBar();

    void onStoryDraftFail();

    void onStoryDraftSuccess();
}
