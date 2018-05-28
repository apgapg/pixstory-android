package com.jullae.data.db.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by master on 28/5/18.
 */
public class AddStoryModel {
    @SerializedName("pictures")
    @Expose
    private List<PictureModel> picturesList;

    public List<PictureModel> getPicturesList() {
        return picturesList;
    }

    public class PictureModel {
        private String picture_id;
        private String picture_url;
        private String picture_likes;
        private String user_id;
        private String user_name;
        private String user_penname;
        private boolean pic_liked;

        public String getPicture_id() {
            return picture_id;
        }

        public String getPicture_url() {
            return picture_url;
        }

        public String getPicture_likes() {
            return picture_likes;
        }

        public String getUser_id() {
            return user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getUser_penname() {
            return user_penname;
        }

        public boolean getPic_liked() {
            return pic_liked;
        }
    }
}
