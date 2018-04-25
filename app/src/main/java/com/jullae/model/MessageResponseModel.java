package com.jullae.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jullae.ui.base.BaseResponseModel;

public class MessageResponseModel extends BaseResponseModel {
    @SerializedName("messageModel")
    @Expose
    private MessageModel messageModel;

    public MessageResponseModel(String b, int errorCode, String errorMessage) {
        super(b, errorCode, errorMessage);
    }

    public MessageModel getMessageModel() {
        return messageModel;
    }
}
