package com.jullae.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul Abrol on 12/25/17.
 * <p>
 * Model class for all likes.
 */
public class AllLikeModel {
    @SerializedName("likes")
    @Expose
    private List<Like> likes = null;

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public class Like {

        @SerializedName("user_id")
        @Expose
        private int userId;
        @SerializedName("user_dp_url")
        @Expose
        private String userDpUrl;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_penname")
        @Expose
        private String userPenName;
        @SerializedName("user_followed")
        @Expose
        private boolean userFollowed;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public Object getUserDpUrl() {
            return userDpUrl;
        }

        public void setUserDpUrl(String userDpUrl) {
            this.userDpUrl = userDpUrl;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPenName() {
            return userPenName;
        }

        public void setUserPenName(String userPenName) {
            this.userPenName = userPenName;
        }

        public boolean isUserFollowed() {
            return userFollowed;
        }

        public void setUserFollowed(boolean userFollowed) {
            this.userFollowed = userFollowed;
        }
    }
}
