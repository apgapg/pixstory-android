package com.jullae.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.jullae.R;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.LikesModel;
import com.jullae.utils.GlideUtils;
import com.jullae.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Abrol on 12/20/17.
 * <p>
 * Class @{@link LikeAdapter} used as a adapter of
 * the class @{@link com.jullae.ui.fragments.LikeDialogFragment}
 * to hold the elements of dialog fragment to show the users
 * who like the story.
 */

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.FeedHolder> {

    private static final String TAG = LikeAdapter.class.getName();
    List<LikesModel.Like> messagelist = new ArrayList();
    private Context context;


    public LikeAdapter(final Context context) {
        this.context = context;

    }


    @Override
    public LikeAdapter.FeedHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new LikeAdapter.FeedHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_like, parent, false));
    }

    @Override
    public void onBindViewHolder(final LikeAdapter.FeedHolder holder, final int position) {

        holder.user_name.setText(messagelist.get(position).getUser_name());
        holder.user_penname.setText(messagelist.get(position).getUser_penname());

        GlideUtils.loadImagefromUrl(context, messagelist.get(position).getUser_avatar(), holder.user_image);
        if (messagelist.get(position).getUser_followed().equals("true")) {
            holder.user_followed.setTextColor(Color.parseColor("#ffffff"));
            holder.user_followed.setBackground(context.getResources().getDrawable(R.drawable.button_active));
        } else {
            holder.user_followed.setTextColor(context.getResources().getColor(R.color.black75));
            holder.user_followed.setBackground(context.getResources().getDrawable(R.drawable.button_border));

        }

    }

    @Override
    public void onBindViewHolder(@NonNull LikeAdapter.FeedHolder holder, int position, @NonNull List<Object> payloads) {

        if (payloads.contains("follow") || payloads.contains("unfollow")) {
            holder.progress_bar.setVisibility(View.VISIBLE);
            holder.user_followed.setVisibility(View.INVISIBLE);

        } else if (payloads.contains("follow_true")) {
            holder.progress_bar.setVisibility(View.INVISIBLE);
            holder.user_followed.setVisibility(View.VISIBLE);
            holder.user_followed.setTextColor(Color.parseColor("#ffffff"));
            holder.user_followed.setBackground(context.getResources().getDrawable(R.drawable.button_active));

        } else if (payloads.contains("unfollow_true")) {
            holder.progress_bar.setVisibility(View.INVISIBLE);
            holder.user_followed.setVisibility(View.VISIBLE);
            holder.user_followed.setTextColor(context.getResources().getColor(R.color.black75));
            holder.user_followed.setBackground(context.getResources().getDrawable(R.drawable.button_border));
        } else
            super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<LikesModel.Like> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }


    public interface FollowReqListener {
        void onSuccess();

        void onFail();

    }

    class FeedHolder extends RecyclerView.ViewHolder {

        private ImageView user_image;
        private TextView user_followed, user_name, user_penname;
        private ProgressBar progress_bar;

        /**
         * Constructor to initialize the view Attribute.
         *
         * @param itemView itemview
         */
        FeedHolder(final View itemView) {
            super(itemView);
            user_image = itemView.findViewById(R.id.image_avatar);
            user_name = itemView.findViewById(R.id.text_name);
            user_penname = itemView.findViewById(R.id.text_penname);
            user_followed = itemView.findViewById(R.id.user_followed);
            progress_bar = itemView.findViewById(R.id.progress_bar);

            user_followed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String isFollowed = messagelist.get(getAdapterPosition()).getUser_followed();

                    if (isFollowed.equals("false"))
                        notifyItemChanged(getAdapterPosition(), "follow");
                    else notifyItemChanged(getAdapterPosition(), "unfollow");

                    Boolean is_followed;
                    is_followed = messagelist.get(getAdapterPosition()).getUser_followed().equals("true");

                    AppDataManager.getInstance().getmApiHelper().makeFollowReq(messagelist.get(getAdapterPosition()).getUser_id(), is_followed).getAsString(new StringRequestListener() {
                        @Override
                        public void onResponse(String response) {
                            NetworkUtils.parseResponse(TAG, response);
                            if (messagelist.get(getAdapterPosition()).getUser_followed().equals("false")) {
                                notifyItemChanged(getAdapterPosition(), "follow_true");
                                messagelist.get(getAdapterPosition()).setUser_followed("true");
                            } else {
                                notifyItemChanged(getAdapterPosition(), "unfollow_true");
                                messagelist.get(getAdapterPosition()).setUser_followed("false");
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            NetworkUtils.parseError(TAG, anError);
                            if (messagelist.get(getAdapterPosition()).getUser_followed().equals("true")) {
                                notifyItemChanged(getAdapterPosition(), "follow_true");
                            } else {
                                notifyItemChanged(getAdapterPosition(), "unfollow_true");
                            }
                        }
                    });


                }
            });
        }

    }
}
