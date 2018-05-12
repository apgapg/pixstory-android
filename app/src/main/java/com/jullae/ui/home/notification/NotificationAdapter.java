package com.jullae.ui.home.notification;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jullae.GlideApp;
import com.jullae.R;
import com.jullae.data.db.model.NotificationModel;
import com.jullae.ui.home.profile.profileVisitor.ProfileVisitorActivity;
import com.jullae.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int NOTI_TYPE_FOLLOW = 1;
    public static final int NOTI_TYPE_NEW_STORY = 2;
    public static final int NOTI_TYPE_NEW_COMMENT = 3;
    public static final int NOTI_TYPE_STORY_LIKE = 4;
    public static final int NOTI_TYPE_PICTURE_LIKE = 5;
    private static final String TAG = NotificationAdapter.class.getName();
    private final Activity mContext;

    List<NotificationModel> messagelist = new ArrayList<>();

    public NotificationAdapter(Activity activity) {
        this.mContext = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NOTI_TYPE_FOLLOW:
                return new NotificationType1ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_follow, parent, false));
            case NOTI_TYPE_NEW_STORY:
                return new NotificationType4ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_new_story, parent, false));
            case NOTI_TYPE_NEW_COMMENT:
                return new NotificationType1ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_new_story, parent, false));
            case NOTI_TYPE_STORY_LIKE:
                return new NotificationType2ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_story_like, parent, false));
            case NOTI_TYPE_PICTURE_LIKE:
                return new NotificationType3ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification_picture_like, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder) holder).text.setText(messagelist.get(position).getSpannable_text());

        if (holder instanceof NotificationType1ViewHolder) {

            NotificationType1ViewHolder holder1 = (NotificationType1ViewHolder) holder;
            GlideApp.with(mContext).load(messagelist.get(position).getActor_avatar()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder1.user_image);

        } else if (holder instanceof NotificationType3ViewHolder) {
            NotificationType3ViewHolder holder3 = (NotificationType3ViewHolder) holder;
            GlideApp.with(mContext).load(messagelist.get(position).getPicture_url_thumb()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into((holder3).image);
        } else if (holder instanceof NotificationType4ViewHolder) {
            NotificationType4ViewHolder holder4 = (NotificationType4ViewHolder) holder;
            GlideApp.with(mContext).load(messagelist.get(position).getActor_avatar()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder4.user_image);

            GlideApp.with(mContext).load(messagelist.get(position).getPicture_url_thumb()).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into((holder4).image);

        }

    }

    @Override
    public int getItemViewType(int position) {
      /*  switch (position) {
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
               return -1;

        }*/
        return messagelist.get(position).getNotification_type_id();
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

    private void onUserClick(int adapterPosition) {
        switch (messagelist.get(adapterPosition).getNotification_type_id()) {
            case NOTI_TYPE_FOLLOW:
                AppUtils.showVisitorProfile(mContext, messagelist.get(adapterPosition).getActor_penname());
                break;
            case NOTI_TYPE_NEW_STORY:
                AppUtils.showStoryDetailActivity(mContext, messagelist.get(adapterPosition).getStory_id());
                break;
            case NOTI_TYPE_NEW_COMMENT:
                AppUtils.showStoryDetailActivity(mContext, messagelist.get(adapterPosition).getStory_id());
                break;
            case NOTI_TYPE_STORY_LIKE:
                AppUtils.showStoryDetailActivity(mContext, messagelist.get(adapterPosition).getStory_id());
                break;
            case NOTI_TYPE_PICTURE_LIKE:
                AppUtils.showPictureDetailActivity(mContext, messagelist.get(adapterPosition).getPicture_id());
                break;


        }
    }

    private void showStoryDetails() {

    }

    private void showVisitorProfile(String penname) {
        Intent i = new Intent(mContext, ProfileVisitorActivity.class);
        i.putExtra("penname", penname);
        mContext.startActivity(i);
    }

    private class BaseViewHolder extends RecyclerView.ViewHolder {

        private TextView text;

        public BaseViewHolder(View inflate) {
            super(inflate);

            text = inflate.findViewById(R.id.text);
            inflate.findViewById(R.id.rootview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUserClick(getAdapterPosition());
                }
            });
        }
    }

    private class NotificationType1ViewHolder extends BaseViewHolder {
        private ImageView user_image;

        public NotificationType1ViewHolder(View inflate) {
            super(inflate);


            user_image = inflate.findViewById(R.id.image_avatar);


        }
    }

    private class NotificationType2ViewHolder extends BaseViewHolder {


        public NotificationType2ViewHolder(View inflate) {
            super(inflate);


        }
    }

    private class NotificationType3ViewHolder extends BaseViewHolder {
        private ImageView image;

        public NotificationType3ViewHolder(View inflate) {
            super(inflate);

            image = inflate.findViewById(R.id.image);

        }
    }

    private class NotificationType4ViewHolder extends BaseViewHolder {
        private ImageView image, user_image;

        public NotificationType4ViewHolder(View inflate) {
            super(inflate);

            image = inflate.findViewById(R.id.image);
            user_image = inflate.findViewById(R.id.image_avatar);

        }
    }
}
