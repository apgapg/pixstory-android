package com.jullae.ui.homefeed.freshfeed;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
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
import com.jullae.customView.ItemOffLRsetDecoration;
import com.jullae.ui.homefeed.HomeFeedModel;
import com.jullae.ui.homefeed.StoryAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class HomeFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = HomeFeedAdapter.class.getName();
    private final Activity mContext;
    private final RequestOptions picOptions;

    List<HomeFeedModel.Feed> messagelist = new ArrayList<HomeFeedModel.Feed>();

    public HomeFeedAdapter(Activity activity) {
        this.mContext = activity;

        picOptions = new RequestOptions();
        picOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeFeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeFeedViewHolder viewHolder = (HomeFeedViewHolder) holder;

        Glide.with(mContext).load(messagelist.get(position).getPicture_url()).apply(picOptions).into(viewHolder.image);

        viewHolder.user_name.setText(messagelist.get(position).getPhotographer_name());
        Glide.with(mContext).load(messagelist.get(position).getPhotographer_dp_url()).apply(picOptions).into(viewHolder.user_image);

        viewHolder.like_count.setText(messagelist.get(position).getLike_count() + " likes");
        viewHolder.story_count.setText(messagelist.get(position).getStory_count() + " stories");
        viewHolder.storyAdapter.add(messagelist.get(position).getStories());

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


    private class HomeFeedViewHolder extends RecyclerView.ViewHolder {


        private ImageView user_image, image, ivStoryPic, ivEditStory, ivLike, ivMore;
        private TextView user_name, tvLocation, tvTimeInDays, like_count, story_count;
        private RecyclerView recycler_view_story;
        private StoryAdapter storyAdapter;

        public HomeFeedViewHolder(View inflate) {
            super(inflate);

            user_image = itemView.findViewById(R.id.user_image);
            image = itemView.findViewById(R.id.image);
            ivMore = itemView.findViewById(R.id.ivMore);
            ivStoryPic = itemView.findViewById(R.id.image);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivEditStory = itemView.findViewById(R.id.ivEditStory);
            user_name = itemView.findViewById(R.id.user_name);
            tvLocation = itemView.findViewById(R.id.tvLocation);
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

        }
    }
}
