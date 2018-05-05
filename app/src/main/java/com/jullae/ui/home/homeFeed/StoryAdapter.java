package com.jullae.ui.home.homeFeed;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.jullae.R;
import com.jullae.data.db.model.StoryModel;
import com.jullae.ui.home.HomeActivity;
import com.jullae.ui.storydetails.StoryDetailActivity;
import com.jullae.ui.writeStory.WriteStoryActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = StoryAdapter.class.getName();
    private final Activity mContext;
    private final RequestOptions picOptions;

    List<Object> messagelist = new ArrayList<>();
    private String picture_id;

    public StoryAdapter(Activity activity) {
        this.mContext = activity;
        picOptions = new RequestOptions();
        picOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1)
            return new HomeFeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false));
        else
            return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_story_list, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (messagelist.get(position) instanceof StoryModel) {
            StoryModel storyModel = (StoryModel) messagelist.get(position);
            HomeFeedViewHolder viewHolder = (HomeFeedViewHolder) holder;

            Glide.with(mContext).load(storyModel.getWriter_avatar()).apply(picOptions).into(viewHolder.user_image);

            viewHolder.user_name.setText(storyModel.getWriter_name());

            viewHolder.like_count.setText(storyModel.getLike_count() + " likes");
            viewHolder.comment_count.setText(storyModel.getComment_count() + " comments");
            viewHolder.story_text.setText(storyModel.getStory_text());

        } else {

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messagelist.get(position) instanceof StoryModel)
            return 1;
        else return 2;
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<StoryModel> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }

    public void addEmptyMessage(String picture_id) {
        this.picture_id = picture_id;
        messagelist.clear();
        messagelist.add("empty");
        notifyDataSetChanged();
    }


    private class HomeFeedViewHolder extends RecyclerView.ViewHolder {


        private ImageView user_image, image, ivStoryPic, ivEditStory, ivLike, ivMore;
        private TextView user_name, tvLocation, tvTimeInDays, like_count, comment_count, story_count, story_text;
        private RecyclerView recycler_view_story;

        public HomeFeedViewHolder(View inflate) {
            super(inflate);

            ivMore = inflate.findViewById(R.id.ivMore);
            ivLike = inflate.findViewById(R.id.btn_like);
            user_name = inflate.findViewById(R.id.text_name);
            user_image = inflate.findViewById(R.id.image_avatar);
            story_text = inflate.findViewById(R.id.story_text);
            tvLocation = inflate.findViewById(R.id.penname_field);
            tvTimeInDays = inflate.findViewById(R.id.tvTimeInDays);
            like_count = inflate.findViewById(R.id.like_count);
            comment_count = inflate.findViewById(R.id.comment_count);
            user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivity) mContext).showVisitorProfile(((StoryModel) messagelist.get(getAdapterPosition())).getWriter_penname());
                }
            });
            user_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivity) mContext).showVisitorProfile(((StoryModel) messagelist.get(getAdapterPosition())).getWriter_penname());
                }
            });
            inflate.findViewById(R.id.rootview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, StoryDetailActivity.class);
                    Gson gson = new Gson();
                    String object = gson.toJson(messagelist.get(getAdapterPosition()));
                    i.putExtra("object", object);
                    i.putExtra("profile", false);
                    mContext.startActivity(i);
                }
            });
        }
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View inflate) {
            super(inflate);


            inflate.findViewById(R.id.text_add_story).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, WriteStoryActivity.class);
                    i.putExtra("picture_id", picture_id);
                    mContext.startActivity(i);
                }
            });
        }
    }


}
