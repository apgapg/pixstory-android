package com.jullae.ui.homefeed;

import com.jullae.BasePresentor;
import com.jullae.helpers.ApiHelper;
import com.jullae.helpers.AppDataManager;
import com.jullae.model.LikesModel;
import com.jullae.ui.adapters.LikeAdapter;
import com.jullae.ui.homefeed.freshfeed.HomeFeedAdapter;
import com.jullae.utils.Constants;

public class HomeFeedPresentor extends BasePresentor {


    private HomeFeedView view;

    public HomeFeedPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }

    public void setView(HomeFeedView view) {
        this.view = view;
    }

    public void loadFeeds() {
        view.showProgress();
        getmAppDataManager().loadHomeFeeds(new FeedFetchListener() {
            @Override
            public void onSuccess(HomeFeedModel homeFeedModel) {
                if (view != null) {
                    view.hideProgress();
                    view.onFetchFeedSuccess(homeFeedModel);
                }
            }

            @Override
            public void onFail() {
                if (view != null) {
                    view.hideProgress();

                    view.onFetchFeedFail();
                }
            }
        });
    }

    public void removeView() {
        view = null;
    }

    public void setlike(String id, final HomeFeedAdapter.ReqListener reqListener, String isLiked) {
        getmAppDataManager().setlike(id, new ApiHelper.ReqListener.StringReqListener() {
            @Override
            public void onSuccess(String string) {
                if (view != null) {
                    reqListener.onSuccess();
                }
            }

            @Override
            public void onFail() {
                if (view != null) {
                    reqListener.onFail();
                }

            }
        }, isLiked, Constants.LIKE_TYPE_PICTURE);
    }

    public void getLikeslist(String id, final LikesFetchListener likesFetchListener, int pictureLike) {
        getmAppDataManager().getLikeslist(id, new LikesFetchListener() {
            @Override
            public void onSuccess(LikesModel likesModel) {
                if (view != null)

                    likesFetchListener.onSuccess(likesModel);
            }

            @Override
            public void onFail() {
                if (view != null)

                    likesFetchListener.onFail();

            }
        }, pictureLike);
    }

    public void makeFollowUserReq(String user_id, final LikeAdapter.FollowReqListener followReqListener) {
        getmAppDataManager().makeFollowReq(user_id, new ApiHelper.ReqListener.StringReqListener() {
            @Override
            public void onSuccess(String string) {
                if (view != null)
                    followReqListener.onSuccess();
            }

            @Override
            public void onFail() {
                if (view != null)

                    followReqListener.onFail();

            }
        });
    }

    public interface FeedFetchListener {
        void onSuccess(HomeFeedModel homeFeedModel);

        void onFail();
    }

    public interface LikesFetchListener {
        void onSuccess(LikesModel likesModelList);

        void onFail();
    }

}
