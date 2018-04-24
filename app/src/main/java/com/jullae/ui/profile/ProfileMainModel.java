package com.jullae.ui.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileMainModel {
    /*
     "profileList": {
        "id": 9,
                "name": "Ayush p gupta",
                "user_dp_url": null,
                "penname": "@coddu",
                "is_followed": false,
                "bio": "IIT Roorkee || Android Developer || Educator at Unacadmey || Bike Lover",
                "account_type": "custom",
                "follower_count": 1,
                "following_count": 0,
                "stories_count": 0,
                "pictures_count": 0,
                "is_self": false
    }*/


    @SerializedName("info")
    @Expose
    private ProfileModel profileModel;

    private class ProfileModel {

        private String account_type;
        private String follower_count;
        private String following_count;
        private String stories_count;
        private String pictures_count;

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
