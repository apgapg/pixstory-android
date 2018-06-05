package com.jullae.data.db.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jullae.BR;

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

    public class PictureModel extends BaseObservable {
        private String picture_id;
        private String picture_url;
        private int picture_likes;
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

        @Bindable
        public int getPicture_likes() {
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

        @Bindable
        public boolean getPic_liked() {
            return pic_liked;
        }

        public void setPic_liked(boolean pic_liked) {
            this.pic_liked = pic_liked;
            notifyPropertyChanged(BR.pic_liked);
        }

        public void setDecrementLikeCount() {
            this.picture_likes--;
            notifyPropertyChanged(BR.picture_likes);
        }

        public void setIncrementLikeCount() {
            this.picture_likes++;
            notifyPropertyChanged(BR.picture_likes);
        }
    }
}
