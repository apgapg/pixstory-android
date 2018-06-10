package com.jullae.ui.home.profile.pictureTab;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.PictureListModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.NetworkUtils;

public class PictureTabPresentor extends BasePresentor<PictureTabView> {
    private static final String TAG = PictureTabPresentor.class.getName();
    private boolean isLoadingMore;
    private int count = 2;

    public PictureTabPresentor() {
    }

    public void loadFeeds(String penname) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().loadPictureTabFeeds(penname, 1).getAsObject(PictureListModel.class, new ParsedRequestListener<PictureListModel>() {


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


    public void loadMoreFeeds(String penname) {
        checkViewAttached();
        if (!isLoadingMore) {
            isLoadingMore = true;
            getMvpView().showLoadMoreProgess();
            AppDataManager.getInstance().getmApiHelper().loadPictureTabFeeds(penname, count).getAsObject(PictureListModel.class, new ParsedRequestListener<PictureListModel>() {
                @Override
                public void onResponse(PictureListModel pictureListModel) {
                    NetworkUtils.parseResponse(TAG, pictureListModel);
                    count++;
                    isLoadingMore = false;
                    if (isViewAttached()) {
                        getMvpView().hideLoadMoreProgess();

                        getMvpView().onMorePicturesFetchSuccess(pictureListModel.getPictureModelList());
                    }
                }

                @Override
                public void onError(ANError anError) {

                    NetworkUtils.parseError(TAG, anError);

                    isLoadingMore = false;
                    if (isViewAttached()) {
                        getMvpView().hideLoadMoreProgess();


                    }
                }
            });
        }
    }
}
