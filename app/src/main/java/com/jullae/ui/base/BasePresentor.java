package com.jullae.ui.base;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.ui.home.homeFeed.freshfeed.HomeFeedAdapter;
import com.jullae.ui.storydetails.StoryDetailPresentor;
import com.jullae.utils.Constants;
import com.jullae.utils.NetworkUtils;

public class BasePresentor<T extends MvpView> implements Presentor<T> {


    private static final String TAG = BasePresentor.class.getName();
    private T mMvpView;


    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }


    public T getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

    public void setLike(String id, final HomeFeedAdapter.ReqListener reqListener, boolean isLiked) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().setlikeReq(id, isLiked, Constants.LIKE_TYPE_PICTURE).getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                NetworkUtils.parseResponse(TAG, response);
                if (isViewAttached())
                    reqListener.onSuccess();

            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached())
                    reqListener.onFail();
            }
        });
    }

    public void reportStory(String report, String story_id, final StoryDetailPresentor.StringReqListener stringReqListener) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().report(report, story_id, Constants.REPORT_TYPE_STORY).getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                NetworkUtils.parseResponse(TAG, response);
                if (isViewAttached())
                    stringReqListener.onSuccess();
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached())
                    stringReqListener.onFail();
            }
        });


    }

    public void reportPicture(String report, String picture_id, final StoryDetailPresentor.StringReqListener stringReqListener) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().report(report, picture_id, Constants.REPORT_TYPE_PICTURE).getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                NetworkUtils.parseResponse(TAG, response);
                if (isViewAttached())
                    stringReqListener.onSuccess();
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached())
                    stringReqListener.onFail();
            }
        });


    }

    public void reportUser(String report, String userId, final StoryDetailPresentor.StringReqListener stringReqListener) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().reportuser(report, userId).getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                NetworkUtils.parseResponse(TAG, response);
                if (isViewAttached())
                    stringReqListener.onSuccess();
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached())
                    stringReqListener.onFail();
            }
        });


    }

}
