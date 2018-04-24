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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jullae.R;
import com.jullae.model.LikesModel;
import com.jullae.ui.homefeed.HomeFeedPresentor;
import com.jullae.ui.storydetails.StoryDetailActivity;
import com.jullae.ui.storydetails.StoryDetailPresentor;

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
    private final RequestOptions picOptions;
    List<LikesModel.Like> messagelist = new ArrayList();
    private HomeFeedPresentor mPresentor;
    private Context context;
    private StoryDetailPresentor mStoryPresentor;


    public LikeAdapter(final Context context, HomeFeedPresentor mPresentor) {
        this.context = context;
        picOptions = new RequestOptions();
        picOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        this.mPresentor = mPresentor;
    }

    public LikeAdapter(Context context, StoryDetailPresentor mPresentor) {
        this.context = context;
        picOptions = new RequestOptions();
        picOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        this.mStoryPresentor = mPresentor;
    }


    @Override
    public LikeAdapter.FeedHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new LikeAdapter.FeedHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_like, parent, false));
    }

    @Override
    public void onBindViewHolder(final LikeAdapter.FeedHolder holder, final int position) {

        holder.user_name.setText(messagelist.get(position).getUser_name());
        holder.user_penname.setText(messagelist.get(position).getUser_penname());

        Glide.with(context).load(messagelist.get(position).getUser_dp_url()).apply(picOptions).into(holder.user_image);
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


                    if (mPresentor != null)
                        mPresentor.makeFollowUserReq(messagelist.get(getAdapterPosition()).getUser_id(), new FollowReqListener() {
                            @Override
                            public void onSuccess() {

                                if (messagelist.get(getAdapterPosition()).getUser_followed().equals("false")) {
                                    notifyItemChanged(getAdapterPosition(), "follow_true");
                                    messagelist.get(getAdapterPosition()).setUser_followed("true");
                                } else {
                                    notifyItemChanged(getAdapterPosition(), "unfollow_true");
                                    messagelist.get(getAdapterPosition()).setUser_followed("false");
                                }

                            }

                            @Override
                            public void onFail() {
                                if (messagelist.get(getAdapterPosition()).getUser_followed().equals("true")) {
                                    notifyItemChanged(getAdapterPosition(), "follow_true");
                                } else {
                                    notifyItemChanged(getAdapterPosition(), "unfollow_true");
                                }

                            }
                        });
                    else if (mStoryPresentor != null) {
                        mStoryPresentor.makeFollowUserReq(messagelist.get(getAdapterPosition()).getUser_id(), new StoryDetailActivity.FollowReqListener() {

                            @Override
                            public void onSuccess() {

                                if (messagelist.get(getAdapterPosition()).getUser_followed().equals("false")) {
                                    notifyItemChanged(getAdapterPosition(), "follow_true");
                                    messagelist.get(getAdapterPosition()).setUser_followed("true");
                                } else {
                                    notifyItemChanged(getAdapterPosition(), "unfollow_true");
                                    messagelist.get(getAdapterPosition()).setUser_followed("false");
                                }

                            }

                            @Override
                            public void onFail() {
                                if (messagelist.get(getAdapterPosition()).getUser_followed().equals("true")) {
                                    notifyItemChanged(getAdapterPosition(), "follow_true");
                                } else {
                                    notifyItemChanged(getAdapterPosition(), "unfollow_true");
                                }

                            }
                        });

                    }

                }
            });
        }

    }
}
