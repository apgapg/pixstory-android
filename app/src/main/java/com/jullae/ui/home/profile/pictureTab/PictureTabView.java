package com.jullae.ui.home.profile.pictureTab;

import com.jullae.data.db.model.PictureModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

public interface PictureTabView extends MvpView {
    void onPicturesFetchSuccess(List<PictureModel> pictureModelList);

    void onPicturesFetchFail();

    void showLoadMoreProgess();

    void hideLoadMoreProgess();

    void onMorePicturesFetchSuccess(List<PictureModel> pictureModelList);
}
