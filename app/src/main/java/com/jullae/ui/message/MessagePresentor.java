package com.jullae.ui.message;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.helpers.AppDataManager;
import com.jullae.model.MessageModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.NetworkUtils;

public class MessagePresentor extends BasePresentor<MessageView> {
    private static final String TAG = MessagePresentor.class.getName();

    public MessagePresentor(AppDataManager appDataManager) {
        super(appDataManager);
    }

    public void loadMessage(String user_id) {
        checkViewAttached();
        getMvpView().showProgressBar();
        getmAppDataManager().getmApiHelper().loadMessageList(user_id).getAsObject(MessageModel.class, new ParsedRequestListener<MessageModel>() {

            @Override
            public void onResponse(MessageModel messageModel) {
                NetworkUtils.parseResponse(TAG, messageModel);

                if (isViewAttached()) {
                    getMvpView().hideProgress();
                    getMvpView().onMessageListFetchSuccess(messageModel.getMessageList());

                }
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().hideProgress();
                    getMvpView().onMessageListFetchFail();
                }
            }
        });
    }

    public String getCurrentUserId() {
        return getmAppDataManager().getmAppPrefsHelper().getKeyUserId();
    }

    public void sendMessageReq(String message, String conversation_id, final MessageActivity.ReqListener reqListener) {
        checkViewAttached();
        getmAppDataManager().getmApiHelper().sendMessageReq(message, conversation_id).getAsObject(MessageModel.Message.class, new ParsedRequestListener<MessageModel.Message>() {

            @Override
            public void onResponse(MessageModel.Message messageModel) {
                NetworkUtils.parseResponse(TAG, messageModel);
                if (isViewAttached())
                    reqListener.onSuccess(messageModel);
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached())
                    reqListener.onFail();
            }
        });
    }
}
