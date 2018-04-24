package com.jullae.ui.search;

import android.app.Activity;
import android.support.annotation.NonNull;
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
import com.jullae.model.PictureModel;
import com.jullae.model.StoryModel;
import com.jullae.ui.profile.StoryListModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class SearchFeedAdapter extends RecyclerView.Adapter<SearchFeedAdapter.SearchFeedViewHolder> {

    private static final String TAG = SearchFeedAdapter.class.getName();
    private final Activity mContext;
    private final RequestOptions picOptions;

    List<StoryListModel.StoryMainModel> messagelist = new ArrayList<StoryListModel.StoryMainModel>();

    public SearchFeedAdapter(Activity activity) {
        this.mContext = activity;

        picOptions = new RequestOptions();
        picOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    }

    @NonNull
    @Override
    public SearchFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchFeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_feeds, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFeedViewHolder viewHolder, int position) {
        PictureModel pictureModel = messagelist.get(position).getPictureModel();
        StoryModel storyModel = messagelist.get(position).getStoryModel();

        Glide.with(mContext).load(pictureModel.getPicture_url_small()).apply(picOptions).into(viewHolder.image);
        viewHolder.writer_name.setText(storyModel.getWriter_penname().trim());
        viewHolder.story_like_count.setText(storyModel.getLike_count() + " likes");
        viewHolder.story_comment_count.setText(storyModel.getComment_count() + " comments");
        viewHolder.story_title.setText(storyModel.getStory_title());
        viewHolder.story_text.setText(storyModel.getStory_text());

    }


    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<StoryListModel.StoryMainModel> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }


    public class SearchFeedViewHolder extends RecyclerView.ViewHolder {


        private View rootview;
        private ImageView image;
        private TextView writer_name, story_title, story_text;
        private TextView story_like_count;
        private TextView story_comment_count;

        public SearchFeedViewHolder(View inflate) {
            super(inflate);

            image = inflate.findViewById(R.id.image);
            story_title = inflate.findViewById(R.id.story_title);
            story_text = inflate.findViewById(R.id.story_text);
            writer_name = inflate.findViewById(R.id.writer_name);
            story_like_count = inflate.findViewById(R.id.story_like_count);
            story_comment_count = inflate.findViewById(R.id.story_comment_count);


        }
    }
}
