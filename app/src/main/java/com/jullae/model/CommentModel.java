package com.jullae.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul Abrol on 12/26/17.
 * <p>
 * model For comments.
 */

public class CommentModel {
    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;

    public class Comment {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("story_id")
        @Expose
        private int storyId;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_dp_url")
        @Expose
        private String userDpUrl;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStoryId() {
            return storyId;
        }

        public void setStoryId(int storyId) {
            this.storyId = storyId;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserDpUrl() {
            return userDpUrl;
        }

        public void setUserDpUrl(String userDpUrl) {
            this.userDpUrl = userDpUrl;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }

}
