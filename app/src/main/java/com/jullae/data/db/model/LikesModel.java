package com.jullae.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul Abrol on 12/25/17.
 * <p>
 * Model class for all likes.
 */
public class LikesModel {
    @SerializedName("likes")
    @Expose
    private List<Like> likes;

    public List<Like> getLikes() {
        return likes;
    }


    public class Like {


        private String user_id;

        private String user_avatar;
        private String user_name;

        private String user_penname;

        private String user_followed;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_avatar() {
            return user_avatar;
        }

        public void setUser_avatar(String user_avatar) {
            this.user_avatar = user_avatar;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_penname() {
            return user_penname;
        }

        public void setUser_penname(String user_penname) {
            this.user_penname = user_penname;
        }

        public String getUser_followed() {
            return user_followed;
        }

        public void setUser_followed(String user_followed) {
            this.user_followed = user_followed;
        }


    }
}
