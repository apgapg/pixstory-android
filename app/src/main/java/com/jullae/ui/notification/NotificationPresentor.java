package com.jullae.ui.notification;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.NotificationMainModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.NetworkUtils;

public class NotificationPresentor extends BasePresentor<NotificationView> {

    private static final String TAG = NotificationPresentor.class.getName();

    public NotificationPresentor() {
    }

    public void loadNotifications() {
        checkViewAttached();
        getMvpView().showProgressBar();
        AppDataManager.getInstance().getmApiHelper().loadNotificationList(AppDataManager.getInstance().getmSharedPrefsHelper().getKeyUserId()).getAsObject(NotificationMainModel.class, new ParsedRequestListener<NotificationMainModel>() {
            @Override
            public void onResponse(NotificationMainModel notificationMainModel) {
                NetworkUtils.parseResponse(TAG, notificationMainModel);
                if (isViewAttached()) {
                    getMvpView().hideProgressBar();
                    getMvpView().onNotificationFetchSuccess(notificationMainModel.getNotificationModelList());
                }

            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                if (isViewAttached()) {
                    getMvpView().hideProgressBar();
                    getMvpView().onNotificationFetchFail();
                }

            }
        });
    }

    public void sendReadNotiReq() {
        checkViewAttached();
        AppDataManager.getInstance().getmApiHelper().sendNotiReadReq(AppDataManager.getInstance().getmSharedPrefsHelper().getKeyUserId()).getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                NetworkUtils.parseResponse(TAG, response);
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);

            }
        });
    }
}
