package com.jullae.ui.homefeed;

import android.app.Activity;
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
import com.jullae.R;

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

            ivMore = itemView.findViewById(R.id.ivMore);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivEditStory = itemView.findViewById(R.id.ivEditStory);
            user_name = itemView.findViewById(R.id.user_name);
            user_image = itemView.findViewById(R.id.user_image);
            story_text = itemView.findViewById(R.id.story_text);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvTimeInDays = itemView.findViewById(R.id.tvTimeInDays);
            like_count = itemView.findViewById(R.id.like_count);
            comment_count = itemView.findViewById(R.id.comment_count);

        }
    }
}
