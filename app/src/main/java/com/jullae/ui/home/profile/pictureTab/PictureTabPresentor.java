package com.jullae.ui.home.profile.pictureTab;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.PictureListModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.NetworkUtils;

public class PictureTabPresentor extends BasePresentor<PictureTabView> {
    private static final String TAG = PictureTabPresentor.class.getName();

    public PictureTabPresentor() {
    }

    public void loadFeeds() {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().loadPictureTabFeeds(AppDataManager.getInstance().getmSharedPrefsHelper().getKeyPenname()).getAsObject(PictureListModel.class, new ParsedRequestListener<PictureListModel>() {


            @Override
            public void onResponse(PictureListModel pictureListModel) {
                NetworkUtils.parseResponse(TAG, pictureListModel);
                if (isViewAttached()) {
                    getMvpView().onPicturesFetchSuccess(pictureListModel.getPictureModelList());
                }
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().onPicturesFetchFail();
                }
            }
        });
    }
}
