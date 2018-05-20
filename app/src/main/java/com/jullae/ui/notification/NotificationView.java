package com.jullae.ui.notification;

import com.jullae.data.db.model.NotificationModel;
import com.jullae.ui.base.MvpView;

import java.util.List;

public interface NotificationView extends MvpView {
    void onNotificationFetchFail();

    void onNotificationFetchSuccess(List<NotificationModel> notificationModelList);

    void showProgressBar();

    void hideProgressBar();
}
