package com.jullae.model;

public class UserPrefsModel {


    private String user_name;
    private String user_penname;
    private String user_bio;
    private String user_dp_url;

    public UserPrefsModel(String keyName, String keyPenname, String keyBio, String keyDpUrl) {
        user_name = keyName;
        user_bio = keyBio;
        user_dp_url = keyDpUrl;
        user_penname = keyPenname;
    }

    public String getUser_bio() {
        return user_bio;
    }

    public String getUser_dp_url() {
        return user_dp_url;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_penname() {
        return user_penname;
    }
}
