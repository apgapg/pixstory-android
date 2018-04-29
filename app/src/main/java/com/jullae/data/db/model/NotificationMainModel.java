package com.jullae.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationMainModel {

    @SerializedName("notifications")
    @Expose
    private List<NotificationModel> notificationModelList;

    public List<NotificationModel> getNotificationModelList() {
        return notificationModelList;
    }
}
