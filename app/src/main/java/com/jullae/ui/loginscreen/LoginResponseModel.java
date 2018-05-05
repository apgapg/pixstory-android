package com.jullae.ui.loginscreen;

import com.jullae.ui.base.BaseResponseModel;

public class LoginResponseModel extends BaseResponseModel {
    private String name;
    private String penname;
    private String bio;
    private String avatar;
    private String user_id;
    private String token;
    private String account_status;
    private String email;

    /* {
         "success": true,
             "errorcode": 0,
             "message": "Data Ok.",
             "user_id": 9,
             "token": "xxx
             "name": "APG",
             "penname": "@coddu",
             "bio": "IIT Roorkee || Android Developer || Educator at Unacadmey || Bike Lover",
             "avatar": null
     }*/
    public LoginResponseModel(String b, int errorCode, String errorMessage) {
        super(b, errorCode, errorMessage);
    }

    public String getBio() {
        return bio;
    }

    public String getName() {
        return name;
    }

    public String getPenname() {
        return penname;
    }

    public boolean isLoginSuccess() {
        return isReqSuccess();
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

    public String getAccount_status() {
        return account_status;
    }

    public String getEmail() {
        return email;
    }
}
