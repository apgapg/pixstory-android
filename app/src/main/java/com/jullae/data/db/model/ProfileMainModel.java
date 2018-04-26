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

    public class ProfileModel {

        private String id;
        private String name;
        private String user_avatar;
        private String penname;
        private String is_followed;
        private String bio;
        private String account_type;
        private String follower_count;
        private String following_count;
        private String stories_count;
        private String pictures_count;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getUser_dp_url() {
            return user_avatar;
        }

        public String getPenname() {
            return penname;
        }

        public String getIs_followed() {
            return is_followed;
        }

        public String getBio() {
            return bio;
        }

        public String getAccount_type() {
            return account_type;
        }

        public String getFollower_count() {
            return follower_count;
        }

        public String getFollowing_count() {
            return following_count;
        }

        public String getStories_count() {
            return stories_count;
        }

        public String getPictures_count() {
            return pictures_count;
        }
    }
}
