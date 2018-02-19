package com.jullae.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul Abrol on 12/25/17.
 * <p>
 * Like response model.
 */

public class LikeResponseModel {
    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("errorcode")
    @Expose
    private int errorCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("like_id")
    @Expose
    private int likeId;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getErrorcode() {
        return errorCode;
    }

    public void setErrorcode(int errorcode) {
        this.errorCode = errorcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }
}
