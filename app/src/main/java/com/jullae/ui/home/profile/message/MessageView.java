package com.jullae.ui.home.profile.message;

import com.jullae.data.db.model.MessageModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

public interface MessageView extends MvpView {
    void onMessageListFetchSuccess(List<MessageModel> messageList);

    void onMessageListFetchFail();

    void showProgressBar();

    void hideProgress();
}
