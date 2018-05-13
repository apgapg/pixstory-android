package com.jullae.ui.home;

import com.jullae.ui.base.MvpView;

public interface HomeActivityView extends MvpView {

    void hideProgressBar();

    void showProgressBar();

    void onPictureUploadSuccess();
}
