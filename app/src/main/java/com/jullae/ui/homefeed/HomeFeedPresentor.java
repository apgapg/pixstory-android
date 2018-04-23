package com.jullae.ui.homefeed;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.jullae.helpers.AppDataManager;
import com.jullae.model.LikesModel;
import com.jullae.ui.adapters.LikeAdapter;
import com.jullae.ui.base.BasePresentor;
import com.jullae.ui.homefeed.freshfeed.HomeFeedAdapter;
import com.jullae.utils.Constants;
import com.jullae.utils.NetworkUtils;

public class HomeFeedPresentor extends BasePresentor<HomeFeedView> {


    private static final String TAG = HomeFeedPresentor.class.getName();
    private HomeFeedView view;

    public HomeFeedPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }

    public void setView(HomeFeedView view) {
        this.view = view;
    }

    public void loadFeeds() {
        checkViewAttached();
        getMvpView().showProgress();
        getmAppDataManager().getmApiHelper().loadHomeFeeds().getAsObject(HomeFeedModel.class, new ParsedRequestListener<HomeFeedModel>() {
            @Override
            public void onResponse(HomeFeedModel homeFeedModel) {
                NetworkUtils.parseResponse(TAG, homeFeedModel);
                if (isViewAttached()) {
                    getMvpView().hideProgress();
                    getMvpView().onFetchFeedSuccess(homeFeedModel);
                }
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().hideProgress();
                    getMvpView().onFetchFeedFail();
                }
            }
        });
    }


    public void setLike(String id, final HomeFeedAdapter.ReqListener reqListener, String isLiked) {
        checkViewAttached();
        getmAppDataManager().getmApiHelper().setlikeReq(id, isLiked, Constants.LIKE_TYPE_PICTURE).getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                if (isViewAttached())
                    reqListener.onSuccess();

            }

            @Override
            public void onError(ANError anError) {
                if (isViewAttached())
                    reqListener.onFail();
            }
        });
    }

    public void getLikeslist(String id, int pictureLike) {
        checkViewAttached();
        getmAppDataManager().getmApiHelper().getLikesList(id, pictureLike).getAsObject(LikesModel.class, new ParsedRequestListener<LikesModel>() {

            @Override
            public void onResponse(LikesModel likesModel) {
                NetworkUtils.parseResponse(TAG, likesModel);
                if (isViewAttached()) {
                    getMvpView().onLikesListFetchSuccess(likesModel);
                }
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().onLikesListFetchFail();
                }
            }
        });

    }

    public void makeFollowUserReq(String user_id, final LikeAdapter.FollowReqListener followReqListener) {
        checkViewAttached();
        getmAppDataManager().getmApiHelper().makeFollowReq(user_id).getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                NetworkUtils.parseResponse(TAG, response);
                if (isViewAttached())
                    followReqListener.onSuccess();
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached())
                    followReqListener.onFail();
            }
        });

    }

}
