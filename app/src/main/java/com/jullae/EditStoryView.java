package com.jullae;

import com.jullae.ui.base.MvpView;

public interface EditStoryView extends MvpView {
    void showProgress();

    void hideProgress();

    void onStoryUpdateSuccess();

    void onStoryUpdateFail(String message);

    void onStoryTitleEmpty();

    void onStoryTextEmpty();
}
