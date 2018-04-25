package com.jullae.ui.profileSelf;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.helpers.AppDataManager;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.NetworkUtils;

public class PictureTabPresentor extends BasePresentor<PictureTabView> {
    private static final String TAG = PictureTabPresentor.class.getName();

    public PictureTabPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }

    public void loadFeeds() {
        checkViewAttached();
        getmAppDataManager().getmApiHelper().loadPictureTabFeeds(getmAppDataManager().getmAppPrefsHelper().getKeyPenname()).getAsObject(PictureListModel.class, new ParsedRequestListener<PictureListModel>() {


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
