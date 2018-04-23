package com.jullae.ui.search;

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
import com.jullae.model.FreshFeedModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class SearchFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = SearchFeedAdapter.class.getName();
    private final Activity mContext;
    private final RequestOptions picOptions;

    List<FreshFeedModel> messagelist = new ArrayList<>();

    public SearchFeedAdapter(Activity activity) {
        this.mContext = activity;

        picOptions = new RequestOptions();
        picOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchFeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_feeds, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      /*  SearchFeedViewHolder viewHolder = (SearchFeedViewHolder) holder;

        Glide.with(mContext).load(messagelist.get(position).pictureModel.getPicture_url_small()).apply(picOptions).into(viewHolder.image);

        viewHolder.story_title.setText(messagelist.get(position).story.getStory_title());
        viewHolder.story_text.setText(messagelist.get(position).story.getStory_text());
        setUserNamesandAvatar(viewHolder, position);

        setLikeCommentCount(viewHolder, position);*/
    }

  /*  private void setUserNamesandAvatar(SearchFeedViewHolder viewHolder, int position) {
        viewHolder.writer_name.setText(messagelist.get(position).story.getWriter_penname().trim());

    }

    private void setLikeCommentCount(SearchFeedViewHolder viewHolder, int position) {
        viewHolder.story_like_count.setText(messagelist.get(position).story.getLike_count() + " likes");
        viewHolder.story_comment_count.setText(messagelist.get(position).story.getComment_count() + " comments");
    }*/

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<FreshFeedModel> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }


    private class SearchFeedViewHolder extends RecyclerView.ViewHolder {


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
