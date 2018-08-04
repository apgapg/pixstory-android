package com.jullae.ui.storydetails;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.CommentModel;
import com.jullae.data.db.model.LikesModel;
import com.jullae.data.db.model.StoryMainModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.ui.base.BaseResponseModel;
import com.jullae.utils.Constants;
import com.jullae.utils.ErrorResponseModel;
import com.jullae.utils.NetworkUtils;

public class StoryDetailPresentor extends BasePresentor<StoryDetailView> {
    private static final String TAG = StoryDetailPresentor.class.getName();
    private StoryDetailView view;

    public StoryDetailPresentor() {
    }


    public void makeFollowUserReq(String user_id, final StoryDetailFragment.FollowReqListener followReqListener, Boolean is_followed) {
        checkViewAttached();

        AppDataManager.getInstance().getmApiHelper().makeFollowReq(user_id, is_followed).getAsString(new StringRequestListener() {
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

    public void setLike(String id, final StringReqListener reqListener, boolean isLiked) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().setlikeReq(id, isLiked, Constants.LIKE_TYPE_STORY).getAsString(new StringRequestListener() {
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

    public void getLikeslist(String id, int storyLike) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().getLikesList(id, storyLike).getAsObject(LikesModel.class, new ParsedRequestListener<LikesModel>() {

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


    public void sendcommentReq(final String comment, String story_id, final StoryDetailPresentor.ReqListener reqListener) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().sendCommentReq(comment, story_id).getAsObject(CommentModel.class, new ParsedRequestListener<CommentModel>() {

            @Override
            public void onResponse(CommentModel commentModel) {
                NetworkUtils.parseResponse(TAG, commentModel);
                if (isViewAttached())
                    reqListener.onSuccess(commentModel);
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached())
                    reqListener.onFail();
            }
        });


    }


    public void saveStory(String story_id) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().sendStorySaveReq(story_id).getAsObject(BaseResponseModel.class, new ParsedRequestListener<BaseResponseModel>() {

            @Override
            public void onResponse(BaseResponseModel response) {
                NetworkUtils.parseResponse(TAG, response);

                if (isViewAttached()) {
                    getMvpView().onSaveStorySuccess();
                }

            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);

                if (isViewAttached()) {
                    getMvpView().onSaveStoryFail();
                }
            }
        });
    }

    public void loadStoryDetails(final String story_id) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().fetchStoryDetails(story_id).getAsObject(StoryMainModel.class, new ParsedRequestListener<StoryMainModel>() {

            @Override
            public void onResponse(StoryMainModel storyMainModel) {
                NetworkUtils.parseResponse(TAG, storyMainModel);
                if (isViewAttached())
                    getMvpView().onStoryDetailFetchSuccess(storyMainModel.getStoryModel());
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached())
                    getMvpView().onStoryDetailFetchFail();

            }
        });

    }

    public void sendStoryDeleteReq(String story_id) {
        checkViewAttached();
        getMvpView().showProgress();
        AppDataManager.getInstance().getmApiHelper().makeStoryDeleteReq(story_id).getAsObject(BaseResponseModel.class, new ParsedRequestListener<BaseResponseModel>() {

            @Override
            public void onResponse(BaseResponseModel response) {
                NetworkUtils.parseResponse(TAG, response);
                if (isViewAttached()) {
                    getMvpView().hideProgress();
                    getMvpView().onStoryDeleteSuccess();

                }
            }

            @Override
            public void onError(ANError anError) {
                ErrorResponseModel errorResponseModel = NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().hideProgress();

                    getMvpView().onStoryDeleteFail(errorResponseModel.getMessage());
                }


            }
        });
    }


    public interface CommentsListener {
        void onSuccess(CommentModel storyCommentModel);

        void onFail();
    }

    public interface ReqListener {
        void onSuccess(CommentModel commentModel);

        void onFail();
    }

    public interface StringReqListener {
        void onSuccess();

        void onFail();
    }
}
