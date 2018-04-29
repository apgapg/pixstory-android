package com.jullae.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileMainModel {
    /*
     {
    "info": {
        "id": 8,
        "name": "Ankit sharma",
        "user_avatar": "https://jullaepictures.s3.amazonaws.com/avatars/4/image/small/WhatsApp_Image_2018-03-16_at_14.38.42.jpeg?AWSAccessKeyId=AKIAITZE3X6NSNXDCDSQ&Expires=1524595322&Signature=F5WyGl4mN%2B6biOPtZ8Q%2FEuPfg%2F4%3D",
        "penname": "ankitsharma",
        "is_followed": false,
        "bio": null,
        "account_type": "custom",
        "follower_count": 2,
        "following_count": 0,
        "stories_count": 10,
        "pictures_count": 12,
        "is_self": true
    }
}*/


    @SerializedName("info")
    @Expose
    private ProfileModel profileModel;

    public ProfileModel getProfileModel() {
        return profileModel;
    }
}
