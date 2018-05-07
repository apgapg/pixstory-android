package com.jullae.ui.loginscreen;

import com.jullae.ui.base.BaseResponseModel;

public class SignUpResponseModel extends BaseResponseModel {
    private String name;
    private String penname;
    private String bio;
    private String avatar;
    private String user_id;
    private String token;

    public SignUpResponseModel(String b, int errorCode, String errorMessage) {
        super(b, errorCode, errorMessage);
    }


    public String getToken() {
        return token;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPenname() {
        return penname;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

}
