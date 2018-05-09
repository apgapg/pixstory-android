package com.jullae.ui.storydetails;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.LikesModel;
import com.jullae.data.db.model.StoryCommentModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.ui.base.BaseResponseModel;
import com.jullae.utils.Constants;
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

    public void setLike(String id, final StringReqListener reqListener, String isLiked) {
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


    public void loadComments(String story_id, final CommentsListener commentsListener) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().loadComments(story_id).getAsObject(StoryCommentModel.class, new ParsedRequestListener<StoryCommentModel>() {

            @Override
            public void onResponse(StoryCommentModel storyCommentModel) {
                NetworkUtils.parseResponse(TAG, storyCommentModel);
                if (isViewAttached())
                    commentsListener.onSuccess(storyCommentModel);
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached())
                    commentsListener.onFail();
            }
        });


    }

    public void sendcommentReq(String comment, String story_id, final StoryDetailPresentor.ReqListener reqListener) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().sendCommentReq(comment, story_id).getAsObject(StoryCommentModel.Comment.class, new ParsedRequestListener<StoryCommentModel.Comment>() {

            @Override
            public void onResponse(StoryCommentModel.Comment commentSingleModel) {
                NetworkUtils.parseResponse(TAG, commentSingleModel);
                if (isViewAttached())
                    reqListener.onSuccess(commentSingleModel);
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached())
                    reqListener.onFail();
            }
        });


    }

    public void reportStory(String report, String story_id, final StringReqListener stringReqListener) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().reportStory(report, story_id, Constants.REPORT_TYPE_STORY).getAsString(new StringRequestListener() {
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


    public interface CommentsListener {
        void onSuccess(StoryCommentModel storyCommentModel);

        void onFail();
    }

    public interface ReqListener {
        void onSuccess(StoryCommentModel.Comment commentModel);

        void onFail();
    }

    public interface StringReqListener {
        void onSuccess();

        void onFail();
    }
}
