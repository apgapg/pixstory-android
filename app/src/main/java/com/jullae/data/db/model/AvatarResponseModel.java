package com.jullae.data.db.model;

import com.jullae.ui.base.BaseResponseModel;

public class AvatarResponseModel extends BaseResponseModel {

    private String avatar;

    public AvatarResponseModel(boolean b, int errorCode, String errorMessage) {
        super(b, errorCode, errorMessage);
    }

    public String getProfile_dp_url() {
        return avatar;
    }
}
