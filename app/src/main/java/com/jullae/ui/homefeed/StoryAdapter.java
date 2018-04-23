package com.jullae.ui.homefeed;

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
import com.jullae.ui.storydetails.StoryDetailActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class StoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = StoryAdapter.class.getName();
    private final Activity mContext;
    private final RequestOptions picOptions;

    List<HomeFeedModel.Story> messagelist = new ArrayList<>();

    public StoryAdapter(Activity activity) {
        this.mContext = activity;
        picOptions = new RequestOptions();
        picOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeFeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeFeedViewHolder viewHolder = (HomeFeedViewHolder) holder;

        Glide.with(mContext).load(messagelist.get(position).getWriter_dp_Url()).apply(picOptions).into(viewHolder.user_image);

        viewHolder.user_name.setText(messagelist.get(position).getWriter_name());

        viewHolder.like_count.setText(messagelist.get(position).getStory_like_count() + " likes");
        viewHolder.comment_count.setText(messagelist.get(position).getStory_comment_count() + " comments");
        viewHolder.story_text.setText(messagelist.get(position).getContent());


    }


    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<HomeFeedModel.Story> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
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
            ivEditStory = inflate.findViewById(R.id.ivEditStory);
            user_name = inflate.findViewById(R.id.user_name);
            user_image = inflate.findViewById(R.id.user_image);
            story_text = inflate.findViewById(R.id.story_text);
            tvLocation = inflate.findViewById(R.id.tvLocation);
            tvTimeInDays = inflate.findViewById(R.id.tvTimeInDays);
            like_count = inflate.findViewById(R.id.like_count);
            comment_count = inflate.findViewById(R.id.comment_count);

            inflate.findViewById(R.id.rootview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, StoryDetailActivity.class);
                    Gson gson = new Gson();
                    String object = gson.toJson(messagelist.get(getAdapterPosition()));
                    i.putExtra("object", object);
                    i.putExtra("position", getAdapterPosition());
                    mContext.startActivity(i);
                }
            });
        }
    }
}
