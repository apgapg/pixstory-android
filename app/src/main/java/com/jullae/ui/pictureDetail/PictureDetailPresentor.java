package com.jullae.ui.pictureDetail;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.AllStoriesModel;
import com.jullae.data.db.model.HomeFeedSingleModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.NetworkUtils;

public class PictureDetailPresentor extends BasePresentor<PictureDetailView> {

    private static final String TAG = PictureDetailPresentor.class.getName();

    public PictureDetailPresentor() {
    }


    public void loadStories(String picture_id) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().loadAllStories(picture_id).getAsObject(AllStoriesModel.class, new ParsedRequestListener<AllStoriesModel>() {

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

    public void loadPictureDetails(String picture_id) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().loadPictureDetail(picture_id).getAsObject(HomeFeedSingleModel.class, new ParsedRequestListener<HomeFeedSingleModel>() {
            @Override
            public void onResponse(HomeFeedSingleModel homeFeedSingleModel) {
                NetworkUtils.parseResponse(TAG, homeFeedSingleModel);
                if (isViewAttached()) {
                    getMvpView().onFetchFeedSuccess(homeFeedSingleModel.getFeedModel());
                }
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().onFetchFeedFail();
                }
            }
        });
    }
}
