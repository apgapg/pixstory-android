package com.jullae.ui.message;

import com.jullae.model.MessageModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

public interface MessageView extends MvpView {
    void onMessageListFetchSuccess(List<MessageModel> messageList);

    void onMessageListFetchFail();

    void showProgressBar();

    void hideProgress();
}
