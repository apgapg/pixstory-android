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
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public class Comment {


        private int id;

        private int story_id;

        private String comment;

        private String user_name;
        private String user_penname;

        private String user_dp_url;

        private String created_at;

        public int getId() {
            return id;
        }

        public int getStory_id() {
            return story_id;
        }

        public String getComment() {
            return comment;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getUser_penname() {
            return user_penname;
        }

        public String getUser_dp_url() {
            return user_dp_url;
        }

        public String getCreated_at() {
            return created_at;
        }
    }

}
