package com.jullae.data.db.model;

import android.content.Context;
import android.text.style.TextAppearanceSpan;

import com.binaryfork.spanny.Spanny;
import com.jullae.R;

import java.util.Date;

public class CommentModel {

    private int id;
    private String comment;
    private String user_name;
    private String user_penname;
    private String user_avatar;
    private Date created_at;
    private boolean is_self;
    private Spanny spannable_text;

    public int getId() {
        return id;
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

    public String getUser_avatar() {
        return user_avatar;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public boolean isIs_self() {
        return is_self;
    }


    public Spanny getSpannable_text(Context context) {
        return this.spannable_text = new Spanny(user_penname + " ", new TextAppearanceSpan(context, R.style.text_14_medium_primary))
                .append(comment, new TextAppearanceSpan(context, R.style.text_14_regular_secondary));
    }
}
