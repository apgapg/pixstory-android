package com.jullae.ui.pictureDetail;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.AllStoriesModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.ui.home.homeFeed.HomeFeedModel;
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
        AppDataManager.getInstance().getmApiHelper().loadPictureDetail(picture_id).getAsObject(HomeFeedModel.class, new ParsedRequestListener<HomeFeedModel>() {
            @Override
            public void onResponse(HomeFeedModel homeFeedModel) {
                NetworkUtils.parseResponse(TAG, homeFeedModel);
                if (isViewAttached()) {
                    getMvpView().onFetchFeedSuccess(homeFeedModel);
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
