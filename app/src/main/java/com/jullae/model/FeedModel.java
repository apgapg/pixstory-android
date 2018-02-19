package com.jullae.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul Abrol on 12/21/17.
 * <p>
 * Feed Model.
 */
public class FeedModel {

    @SerializedName("feed")
    @Expose
    public List<Feed> feed = null;

    public List<Feed> getFeed() {
        return feed;
    }

    public class Feed {

        @SerializedName("id")
        @Expose
        public int id;
        @SerializedName("picture_id")
        @Expose
        public int pictureId;
        @SerializedName("photographer_name")
        @Expose
        public String photographerName;
        @SerializedName("photographer_dp_url")
        @Expose
        public Object photographerDpUrl;
        @SerializedName("picture_title")
        @Expose
        public String pictureTitle;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("picture_url")
        @Expose
        public String pictureUrl;
        @SerializedName("like_count")
        @Expose
        public int likeCount;
        @SerializedName("story_count")
        @Expose
        public int storyCount;
        @SerializedName("nav_story_id")
        @Expose
        public int navStoryId;
        @SerializedName("stories")
        @Expose
        public List<Story> stories = null;

        public int getId() {
            return id;
        }

        public int getPictureId() {
            return pictureId;
        }

        public String getPhotographerName() {
            return photographerName;
        }

        public Object getPhotographerDpUrl() {
            return photographerDpUrl;
        }

        public String getPictureTitle() {
            return pictureTitle;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public int getStoryCount() {
            return storyCount;
        }

        public int getNavStoryId() {
            return navStoryId;
        }

        public List<Story> getStories() {
            return stories;
        }
    }

    public static class Story {

        @SerializedName("id")
        @Expose
        public int id;
        @SerializedName("writer_name")
        @Expose
        public String writerName;
        @SerializedName("story_created_at")
        @Expose
        public String storyCreatedAt;
        @SerializedName("content")
        @Expose
        public String content;
        @SerializedName("story_like_count")
        @Expose
        public int storyLikeCount;
        @SerializedName("story_comment_count")
        @Expose
        public int storyCommentCount;
        @SerializedName("story_title")
        @Expose
        public String storyTitle;
        @SerializedName("writer_dp_url")
        @Expose
        public String writerDpUrl;

        public int getId() {
            return id;
        }

        public String getWriterName() {
            return writerName;
        }

        public String getStoryCreatedAt() {
            return storyCreatedAt;
        }

        public String getContent() {
            return content;
        }

        public int getStoryLikeCount() {
            return storyLikeCount;
        }

        public int getStoryCommentCount() {
            return storyCommentCount;
        }

        public String getStoryTitle() {
            return storyTitle;
        }

        public String getWriterDpUrl() {
            return writerDpUrl;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setWriterName(String writerName) {
            this.writerName = writerName;
        }
    }
}


