package com.jullae.ui.pictureDetail;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.AllStoriesModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.NetworkUtils;

public class PictureDetailPresentor extends BasePresentor<PictureDetailView> {

    private static final String TAG = PictureDetailPresentor.class.getName();

    public PictureDetailPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }


    public void loadStories(String picture_id) {
        checkViewAttached();
        getmAppDataManager().getmApiHelper().loadAllStories(picture_id).getAsObject(AllStoriesModel.class, new ParsedRequestListener<AllStoriesModel>() {

            @Override
            public void onResponse(AllStoriesModel allStoriesModel) {
                NetworkUtils.parseResponse(TAG, allStoriesModel);
                if (isViewAttached()) {
                    getMvpView().onStoriesFetchSuccess(allStoriesModel.getStoryModelList());
                }
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().onStoriesFetchFail();

                }
            }
        });
    }
}
