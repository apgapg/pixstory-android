package com.jullae.data.db.model;

import android.content.Context;
import android.text.style.TextAppearanceSpan;

import com.binaryfork.spanny.Spanny;
import com.jullae.R;

public class NotificationModel {
    /*{
       "notifications": [
           {
               "actor_id": 9,
               "actor_avatar": "https://jullaepictures.s3.amazonaws.com/avatars/5/image/thumb/cropped674604553.jpg?AWSAccessKeyId=AKIAITZE3X6NSNXDCDSQ&Expires=1524909459&Signature=VeS0ndzB9R%2FDkK384K9XF6QL3gs%3D",
               "actor_name": "Ayush p gupta",
               "actor_penname": "@coddu",
               "text": "started following you",
               "story_id": null,
               "picture_id": null,
               "picture_url_thumb": null,
               "notification_type_id": 1
           }
       ]
   }*/
    private String actor_id;
    private String actor_avatar;
    private String actor_name;
    private String actor_penname;
    private String text;
    private String story_id;
    private String picture_id;
    private String picture_url_thumb;
    private int notification_type_id;

    private Spanny spannable_text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;

    }

    public int getNotification_type_id() {
        return notification_type_id;
    }

    public String getActor_id() {
        return actor_id;
    }

    public String getActor_avatar() {
        return actor_avatar;
    }

    public String getActor_name() {
        return actor_name;
    }

    public String getActor_penname() {
        return actor_penname;
    }

    public String getStory_id() {
        return story_id;
    }

    public String getPicture_id() {
        return picture_id;
    }

    public String getPicture_url_thumb() {
        return picture_url_thumb;
    }

    public Spanny getSpannable_text() {
        return spannable_text;
    }

    public void setSpannable_text(Context context) {
        if (notification_type_id == 1 || notification_type_id == 3 || notification_type_id == 2) {
            Spanny spanny = new Spanny(actor_name + " ", new TextAppearanceSpan(context, R.style.text_14_medium_primary))
                    .append(text, new TextAppearanceSpan(context, R.style.text_14_regular_secondary));
            this.spannable_text = spanny;
        } else {
            ///temp
            Spanny spanny = new Spanny(text, new TextAppearanceSpan(context, R.style.text_14_regular_secondary));
            this.spannable_text = spanny;
        }
    }
}
