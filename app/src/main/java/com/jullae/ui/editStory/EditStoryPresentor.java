package com.jullae.ui.editStory;

import android.text.TextUtils;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.ui.base.BasePresentor;
import com.jullae.ui.base.BaseResponseModel;
import com.jullae.utils.ErrorResponseModel;
import com.jullae.utils.NetworkUtils;

public class EditStoryPresentor extends BasePresentor<EditStoryView> {
    private static final String TAG = EditStoryPresentor.class.getName();

    public void updateStory(String story_id, String title, String story) {
        checkViewAttached();

        if (TextUtils.isEmpty(title)) {
            getMvpView().onStoryTitleEmpty();
        } else if (TextUtils.isEmpty(story)) {
            getMvpView().onStoryTextEmpty();
        } else {
            makeUpdateStoryReq(story_id, title, story);
        }

    }

    private void makeUpdateStoryReq(String story_id, String title, String story) {
        checkViewAttached();
        getMvpView().showProgress();
        AppDataManager.getInstance().getmApiHelper().updateStoryReq(story_id, title, story).getAsObject(BaseResponseModel.class, new ParsedRequestListener<BaseResponseModel>() {
            @Override
            public void onResponse(BaseResponseModel response) {
                NetworkUtils.parseResponse(TAG, response);
                if (isViewAttached()) {
                    getMvpView().hideProgress();
                    getMvpView().onStoryUpdateSuccess();
                }
            }

            @Override
            public void onError(ANError anError) {
                ErrorResponseModel errorResponseModel = NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().hideProgress();
                    getMvpView().onStoryUpdateFail(errorResponseModel.getMessage());
                }

            }
        });

    }
}
