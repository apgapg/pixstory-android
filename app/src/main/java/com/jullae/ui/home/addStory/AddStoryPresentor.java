package com.jullae.ui.home.addStory;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.AddStoryModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.NetworkUtils;

public class AddStoryPresentor extends BasePresentor<AddStoryView> {


    private static final String TAG = AddStoryPresentor.class.getName();

    public AddStoryPresentor() {
    }

    public void loadData() {
        checkViewAttached();
        getMvpView().showProgressBar();
        AppDataManager.getInstance().getmApiHelper().loadPictures().getAsObject(AddStoryModel.class, new ParsedRequestListener<AddStoryModel>() {
            @Override
            public void onResponse(AddStoryModel response) {
                NetworkUtils.parseResponse(TAG, response);
                if (isViewAttached()) {
                    getMvpView().hideProgressBar();
                    getMvpView().onListFetch(response.getPicturesList());
                }

            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().hideProgressBar();
                    getMvpView().onListFetchFail();
                }


            }
        });
    }


}
