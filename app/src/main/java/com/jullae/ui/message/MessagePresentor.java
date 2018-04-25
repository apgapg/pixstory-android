package com.jullae.ui.message;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.helpers.AppDataManager;
import com.jullae.model.MessageMainModel;
import com.jullae.model.MessageResponseModel;
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
        getmAppDataManager().getmApiHelper().loadMessageList(user_id).getAsObject(MessageMainModel.class, new ParsedRequestListener<MessageMainModel>() {

            @Override
            public void onResponse(MessageMainModel messageMainModel) {
                NetworkUtils.parseResponse(TAG, messageMainModel);

                if (isViewAttached()) {
                    getMvpView().hideProgress();
                    getMvpView().onMessageListFetchSuccess(messageMainModel.getMessageList());

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

    public void sendMessageReq(String message, String user_id, final MessageActivity.ReqListener reqListener) {
        checkViewAttached();
        getmAppDataManager().getmApiHelper().sendMessageReq(message, user_id).getAsObject(MessageResponseModel.class, new ParsedRequestListener<MessageResponseModel>() {

            @Override
            public void onResponse(MessageResponseModel messageResponseModel) {
                NetworkUtils.parseResponse(TAG, messageResponseModel);
                if (isViewAttached())
                    reqListener.onSuccess(messageResponseModel.getMessageModel());
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
