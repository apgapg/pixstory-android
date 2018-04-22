package com.jullae.ui.storydetails;

import com.jullae.BasePresentor;
import com.jullae.helpers.ApiHelper;
import com.jullae.helpers.AppDataManager;
import com.jullae.model.CommentModel;
import com.jullae.model.LikesModel;
import com.jullae.ui.homefeed.HomeFeedPresentor;
import com.jullae.utils.Constants;

public class StoryDetailPresentor extends BasePresentor {
    private StoryDetailContract.View view;

    public StoryDetailPresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }


    public void makeFollowUserReq(String user_id, final StoryDetailsActivity.FollowReqListener followReqListener) {
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

    public void setlike(String id, final StoryDetailsActivity.ReqListener reqListener, String isLiked) {
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
        }, isLiked, Constants.LIKE_TYPE_STORY);
    }

    public void getLikeslist(String id, final HomeFeedPresentor.LikesFetchListener likesFetchListener, int likesStory) {
        getmAppDataManager().getLikeslist(id, new HomeFeedPresentor.LikesFetchListener() {
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
        }, likesStory);
    }

    public void setView(StoryDetailContract.View view) {
        this.view = view;
    }

    public void removeView() {

        view = null;
    }

    public void loadComments(String story_id, final CommentsListener commentsListener) {
        getmAppDataManager().loadComments(story_id, new CommentsListener() {
            @Override
            public void onSuccess(CommentModel commentModel) {
                if (view != null)
                    commentsListener.onSuccess(commentModel);
            }

            @Override
            public void onFail() {
                if (view != null)

                    commentsListener.onFail();
            }
        });


    }

    public void sendcommentReq(String comment, String story_id, final StoryDetailPresentor.ReqListener reqListener) {
        getmAppDataManager().sendCommentReq(comment, story_id, new ReqListener() {
            @Override
            public void onSuccess(CommentModel.Comment commentModel) {
                if (view != null)
                    reqListener.onSuccess(commentModel);
            }

            @Override
            public void onFail() {
                if (view != null)
                    reqListener.onFail();

            }
        });
    }

    public void reportStory(String report, String story_id, final StringReqListener stringReqListener) {
        getmAppDataManager().reportStory(report, story_id, Constants.REPORT_TYPE_STORY, new ApiHelper.ReqListener.StringReqListener() {
            @Override
            public void onSuccess(String string) {
                stringReqListener.onSuccess();
            }

            @Override
            public void onFail() {
                stringReqListener.onFail();
            }
        });

    }


    public interface CommentsListener {
        void onSuccess(CommentModel commentModel);

        void onFail();
    }

    public interface ReqListener {
        void onSuccess(CommentModel.Comment commentModel);

        void onFail();
    }

    public interface StringReqListener {
        void onSuccess();

        void onFail();
    }
}
