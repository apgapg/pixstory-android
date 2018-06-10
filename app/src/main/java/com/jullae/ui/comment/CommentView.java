package com.jullae.ui.comment;

import com.jullae.data.db.model.CommentModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

/**
 * Created by master on 8/6/18.
 */
public interface CommentView extends MvpView {
    void showLoading();

    void onCommentAddSuccess(CommentModel commentModel);

    void onCommentAddFail();

    void hideLoading();

    void onCommentListFetch(List<CommentModel> commentModelList);

    void onCommentListFail();

    void onMoreCommentListFetch(List<CommentModel> commentModelList);

    void hideLoadingMore();

    void showLoadingMore();
}
