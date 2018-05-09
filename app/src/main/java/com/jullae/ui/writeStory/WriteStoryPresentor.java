package com.jullae.ui.writeStory;

import android.text.TextUtils;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.StoryResponseModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.ui.base.BaseResponseModel;
import com.jullae.utils.NetworkUtils;

public class WriteStoryPresentor extends BasePresentor<WriteStoryView> {

    private static final String TAG = WriteStoryPresentor.class.getName();

    public WriteStoryPresentor() {
    }

    public void sendStoryPublishReq(String title, String content, String picture_id) {
        checkViewAttached();
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
            getMvpView().showProgressBar();
            AppDataManager.getInstance().getmApiHelper().publishStory(title, content, picture_id).getAsObject(StoryResponseModel.class, new ParsedRequestListener<StoryResponseModel>() {

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
                    NetworkUtils.parseResponse(TAG, anError);
                    if (isViewAttached()) {
                        getMvpView().onStoryPublishFail();
                        getMvpView().hideProgressBar();
                    }
                }
            });
        }
    }

    public void sendStoryDraftReq(String title, String content, String picture_id) {
        checkViewAttached();
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
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
}
