package com.jullae.ui.comment;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.CommentMainModel;
import com.jullae.data.db.model.CommentModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.NetworkUtils;

/**
 * Created by master on 8/6/18.
 */
public class CommentPresentor extends BasePresentor<CommentView> {
    private static final String TAG = CommentPresentor.class.getName();

    public void loadComments(String story_id) {
        checkViewAttached();
        getMvpView().showLoading();
        AppDataManager.getInstance().getmApiHelper().loadComments(story_id).getAsObject(CommentMainModel.class, new ParsedRequestListener<CommentMainModel>() {

            @Override
            public void onResponse(CommentMainModel response) {
                NetworkUtils.parseResponse(TAG, response);
                if (isViewAttached()) {
                    getMvpView().hideLoading();
                    getMvpView().onCommentListFetch(response.getCommentModelList());
                }
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().onCommentListFail();
                }

            }
        });
    }

    public void sendcommentReq(final String comment, String story_id) {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().sendCommentReq(comment, story_id).getAsObject(CommentModel.class, new ParsedRequestListener<CommentModel>() {

            @Override
            public void onResponse(CommentModel commentModel) {
                NetworkUtils.parseResponse(TAG, commentModel);
                if (isViewAttached())
                    getMvpView().onCommentAddSuccess(commentModel);
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached())
                    getMvpView().onCommentAddFail();
            }
        });


    }
}
