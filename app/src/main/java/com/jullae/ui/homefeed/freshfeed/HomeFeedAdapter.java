package com.jullae.ui.homefeed.freshfeed;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jullae.R;
import com.jullae.customView.ItemOffLRsetDecoration;
import com.jullae.model.LikesModel;
import com.jullae.ui.adapters.LikeAdapter;
import com.jullae.ui.homefeed.HomeActivity;
import com.jullae.ui.homefeed.HomeFeedModel;
import com.jullae.ui.homefeed.HomeFeedPresentor;
import com.jullae.ui.homefeed.StoryAdapter;
import com.jullae.utils.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class HomeFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = HomeFeedAdapter.class.getName();
    private final Activity mContext;
    private final RequestOptions picOptions;
    private final HomeFeedPresentor mPresentor;

    List<HomeFeedModel.Feed> messagelist = new ArrayList<HomeFeedModel.Feed>();
    private LikeAdapter likeAdapter;

    public HomeFeedAdapter(Activity activity, HomeFeedPresentor homeFeedPresentor) {
        this.mContext = activity;

        this.mPresentor = homeFeedPresentor;

        picOptions = new RequestOptions();
        picOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeFeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomeFeedViewHolder viewHolder = (HomeFeedViewHolder) holder;

        Glide.with(mContext).load(messagelist.get(position).getPicture_url()).apply(picOptions).into(viewHolder.image);

        viewHolder.user_name.setText(messagelist.get(position).getPhotographer_name());
        viewHolder.user_penname.setText(messagelist.get(position).getPhotographer_penname());

        Glide.with(mContext).load(messagelist.get(position).getPhotographer_avatar()).apply(picOptions).into(viewHolder.user_image);

        viewHolder.like_count.setText(messagelist.get(position).getLike_count() + " likes");
        viewHolder.story_count.setText(messagelist.get(position).getStory_count() + " stories");
        viewHolder.storyAdapter.add(messagelist.get(position).getStories());

        if (messagelist.get(position).getIs_liked().equals("false")) {
            viewHolder.btn_like.setImageResource(R.drawable.ic_unlike);
            viewHolder.like_count.setTextColor(Color.parseColor("#9e9e9e"));
            viewHolder.like_count.setTypeface(Typeface.DEFAULT);
        } else {
            viewHolder.btn_like.setImageResource(R.drawable.ic_like);
            viewHolder.like_count.setTextColor(Color.parseColor("#424242"));
            viewHolder.like_count.setTypeface(Typeface.DEFAULT_BOLD);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        HomeFeedViewHolder viewHolder = (HomeFeedViewHolder) holder;

        if (payloads.contains("like")) {
            messagelist.get(position).setIs_liked("true");
            viewHolder.btn_like.setImageResource(R.drawable.ic_like);
            viewHolder.like_count.setText(String.valueOf(Integer.parseInt(messagelist.get(position).getLike_count()) + 1) + " likes");
            messagelist.get(position).setLike_count(String.valueOf(Integer.parseInt(messagelist.get(position).getLike_count()) + 1));
            viewHolder.like_count.setTextColor(Color.parseColor("#424242"));
            viewHolder.like_count.setTypeface(Typeface.DEFAULT_BOLD);


        } else if (payloads.contains("unlike")) {
            messagelist.get(position).setIs_liked("false");
            viewHolder.btn_like.setImageResource(R.drawable.ic_unlike);
            if (Integer.parseInt(messagelist.get(position).getLike_count()) != 0) {
                viewHolder.like_count.setText(String.valueOf(Integer.parseInt(messagelist.get(position).getLike_count()) - 1) + " likes");
                messagelist.get(position).setLike_count(String.valueOf(Integer.parseInt(messagelist.get(position).getLike_count()) - 1));

            }
            viewHolder.like_count.setTextColor(Color.parseColor("#9e9e9e"));
            viewHolder.like_count.setTypeface(Typeface.DEFAULT);


        } else
            super.onBindViewHolder(holder, position, payloads);
    }


    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<HomeFeedModel.Feed> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }

    private void showLikesDialog(String id) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        View view = mContext.getLayoutInflater().inflate(R.layout.dialog_likes, null);

        setupRecyclerView(view, id);
        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    private void setupRecyclerView(View view, String id) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        likeAdapter = new LikeAdapter(mContext, mPresentor);
        recyclerView.setAdapter(likeAdapter);

        mPresentor.getLikeslist(id, Constants.LIKE_TYPE_PICTURE);
    }

    public void onLikesListFetchSuccess(LikesModel likesModel) {
        likeAdapter.add(likesModel.getLikes());
    }

    public void onLikesListFetchFail() {

    }

    public interface ReqListener {
        void onSuccess();

        void onFail();
    }

    private class HomeFeedViewHolder extends RecyclerView.ViewHolder {


        private ImageView user_image, image, ivStoryPic, ivEditStory, btn_like, ivMore;
        private TextView user_name, user_penname, tvTimeInDays, like_count, story_count;
        private RecyclerView recycler_view_story;
        private StoryAdapter storyAdapter;

        public HomeFeedViewHolder(View inflate) {
            super(inflate);

            user_image = itemView.findViewById(R.id.image_avatar);
            image = itemView.findViewById(R.id.image);
            ivMore = itemView.findViewById(R.id.ivMore);
            btn_like = itemView.findViewById(R.id.btn_like);
            ivEditStory = itemView.findViewById(R.id.ivEditStory);
            user_name = itemView.findViewById(R.id.text_name);
            user_penname = itemView.findViewById(R.id.text_penname);
            tvTimeInDays = itemView.findViewById(R.id.tvTimeInDays);
            like_count = itemView.findViewById(R.id.like_count);
            story_count = itemView.findViewById(R.id.story_count);

            recycler_view_story = itemView.findViewById(R.id.recycler_view_story);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            recycler_view_story.setLayoutManager(linearLayoutManager);

            ItemOffLRsetDecoration itemDecoration = new ItemOffLRsetDecoration(mContext, R.dimen.item_offset);
            recycler_view_story.addItemDecoration(itemDecoration);

            storyAdapter = new StoryAdapter(mContext);
            recycler_view_story.setAdapter(storyAdapter);

            user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivity) mContext).showVisitorProfile(messagelist.get(getAdapterPosition()).getPhotographer_penname());
                }
            });
            user_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivity) mContext).showVisitorProfile(messagelist.get(getAdapterPosition()).getPhotographer_penname());
                }
            });

            user_penname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivity) mContext).showVisitorProfile(messagelist.get(getAdapterPosition()).getPhotographer_penname());
                }
            });


            btn_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String isLiked = messagelist.get(getAdapterPosition()).getIs_liked();
                    if (isLiked.equals("false"))
                        notifyItemChanged(getAdapterPosition(), "like");
                    else notifyItemChanged(getAdapterPosition(), "unlike");

                    mPresentor.setLike(messagelist.get(getAdapterPosition()).getPicture_id(), new ReqListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFail() {
                            if (messagelist.get(getAdapterPosition()).getIs_liked().equals("false"))
                                notifyItemChanged(getAdapterPosition(), "like");
                            else notifyItemChanged(getAdapterPosition(), "unlike");
                            Toast.makeText(mContext.getApplicationContext(), "couldn't connect!", Toast.LENGTH_SHORT).show();
                        }
                    }, isLiked);
                }
            });

            like_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLikesDialog(messagelist.get(getAdapterPosition()).getPicture_id());
                }
            });


        }


    }
}
