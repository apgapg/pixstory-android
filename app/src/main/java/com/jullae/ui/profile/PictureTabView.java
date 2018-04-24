package com.jullae.ui.profile;

import com.jullae.model.PictureModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

public interface PictureTabView extends MvpView {
    void onPicturesFetchSuccess(List<PictureModel> pictureModelList);

    void onPicturesFetchFail();
}
