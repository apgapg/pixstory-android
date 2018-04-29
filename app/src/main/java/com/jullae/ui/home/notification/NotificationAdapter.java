package com.jullae.ui.home.notification;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jullae.GlideApp;
import com.jullae.R;
import com.jullae.data.db.model.NotificationModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = NotificationAdapter.class.getName();
    private static final int NOTI_TYPE_FOLLOW = 1;
    private static final int NOTI_TYPE_NEW_STORY = 2;
    private static final int NOTI_TYPE_NEW_COMMENT = 3;
    private static final int NOTI_TYPE_STORY_LIKE = 4;
    private static final int NOTI_TYPE_PICTURE_LIKE = 5;
    private final Activity mContext;
    private final RequestOptions picOptions;

    List<NotificationModel> messagelist = new ArrayList<>();

    public NotificationAdapter(Activity activity) {
        this.mContext = activity;
        picOptions = new RequestOptions();
        picOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NOTI_TYPE_FOLLOW:
                return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_follow, parent, false));
            case NOTI_TYPE_NEW_STORY:
                return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_new_story, parent, false));
            case NOTI_TYPE_NEW_COMMENT:
                return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_follow, parent, false));
            case NOTI_TYPE_STORY_LIKE:
                return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_story_like, parent, false));
            case NOTI_TYPE_PICTURE_LIKE:
                return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_picture_like, parent, false));
            default:
                return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_follow, parent, false));

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        NotificationViewHolder viewHolder = (NotificationViewHolder) holder;

        GlideApp.with(mContext).load(messagelist.get(position).getActor_avatar()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(viewHolder.user_image);

        viewHolder.text.setText(messagelist.get(position).getSpannable_text());


    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case NOTI_TYPE_FOLLOW:
                return NOTI_TYPE_FOLLOW;
            case NOTI_TYPE_NEW_STORY:
                return NOTI_TYPE_NEW_STORY;
            case NOTI_TYPE_NEW_COMMENT:
                return NOTI_TYPE_NEW_COMMENT;
            case NOTI_TYPE_STORY_LIKE:
                return NOTI_TYPE_STORY_LIKE;
            case NOTI_TYPE_PICTURE_LIKE:
                return NOTI_TYPE_PICTURE_LIKE;
            default:
                return super.getItemViewType(position);

        }
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<NotificationModel> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }


    private class NotificationViewHolder extends RecyclerView.ViewHolder {


        private ImageView user_image;
        private TextView text;

        public NotificationViewHolder(View inflate) {
            super(inflate);


            user_image = inflate.findViewById(R.id.image_avatar);
            text = inflate.findViewById(R.id.text);

        }
    }
}
