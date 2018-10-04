package com.jullae.ui.writeStory;

import android.text.TextUtils;
import android.util.Log;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.StoryResponseModel;
import com.jullae.data.db.model.WriteStoryCategoryModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.ui.base.BaseResponseModel;
import com.jullae.utils.NetworkUtils;

public class WriteStoryPresentor extends BasePresentor<WriteStoryView> {

    private static final String TAG = WriteStoryPresentor.class.getName();

    public WriteStoryPresentor() {
    }

    public void sendStoryPublishReq(String title, String content, String picture_id, String categoryId) {
        checkViewAttached();

        getMvpView().showProgressBar();
        AppDataManager.getInstance().getmApiHelper().publishStory(title, content, picture_id,categoryId).getAsObject(StoryResponseModel.class, new ParsedRequestListener<StoryResponseModel>() {

            @Override
            public void onResponse(StoryResponseModel storyResponseModel) {
                NetworkUtils.parseResponse(TAG, storyResponseModel);
                if (isViewAttached()) {
                    getMvpView().hideProgressBar();
                    getMvpView().onStoryPublishSuccess();
                }
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().onStoryPublishFail();
                    getMvpView().hideProgressBar();
                }
            }
        });

    }

    public boolean checkNonEmptyFields(String title, String content) {
        if (TextUtils.isEmpty(title)) {
            getMvpView().onTitleEmpty();
            return false;
        } else if (TextUtils.isEmpty(content)) {
            getMvpView().onContentEmpty();
            return false;
        } else return true;
    }

    public void sendStoryDraftReq(String title, String content, String picture_id) {
        checkViewAttached();
        if (checkNonEmptyFields(title, content)) {
            getMvpView().showProgressBar();
            AppDataManager.getInstance().getmApiHelper().draftStory(title, content, picture_id).getAsObject(BaseResponseModel.class, new ParsedRequestListener<BaseResponseModel>() {

                @Override
                public void onResponse(BaseResponseModel baseResponseModel) {
                    NetworkUtils.parseResponse(TAG, baseResponseModel);
                    if (isViewAttached()) {
                        getMvpView().hideProgressBar();
                        getMvpView().onStoryDraftSuccess();
                    }
                }

                @Override
                public void onError(ANError anError) {
                    NetworkUtils.parseResponse(TAG, anError);
                    if (isViewAttached()) {
                        getMvpView().onStoryPublishFail();
                        getMvpView().hideProgressBar();
                    }
                }
            });
        }
    }

    public void loadCategories() {

        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().fetchWriteStoryCategory().getAsObject(WriteStoryCategoryModel.class, new ParsedRequestListener<WriteStoryCategoryModel>() {

            @Override
            public void onResponse(WriteStoryCategoryModel response) {
                NetworkUtils.parseResponse(TAG, response);
                if (isViewAttached())
                    getMvpView().onFetchCategories(response.getCategoryItemList());
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);

            }
        });
    }
}
