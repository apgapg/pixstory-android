package com.jullae.ui.homefeed.freshfeed;

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

public class FreshFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = FreshFeedAdapter.class.getName();
    private final Activity mContext;
    private final RequestOptions picOptions;

    List<FreshFeedModel.FreshFeed> messagelist = new ArrayList<FreshFeedModel.FreshFeed>();

    public FreshFeedAdapter(Activity activity) {
        this.mContext = activity;

        picOptions = new RequestOptions();
        picOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FreshFeedsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fresh_feeds, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FreshFeedsViewHolder viewHolder = (FreshFeedsViewHolder) holder;

        FreshFeedModel.PictureModel pictureModel = messagelist.get(position).getPictureModel();
        FreshFeedModel.Story storyModel = messagelist.get(position).getStory();

        Glide.with(mContext).load(pictureModel.getPicture_url_small()).apply(picOptions).into(viewHolder.image);
        if (pictureModel.getPicture_title() != null && pictureModel.getPicture_title().isEmpty()) {
            viewHolder.pic_text_buy.setText("by");
            viewHolder.pic_title.setVisibility(View.VISIBLE);
            viewHolder.pic_title.setText(pictureModel.getPicture_title().trim());

        } else {
            viewHolder.pic_title.setVisibility(View.GONE);
            viewHolder.pic_text_buy.setText(" By");
        }

        viewHolder.story_title.setText(storyModel.getStory_title());
        viewHolder.story_text.setText(storyModel.getStory_text());
        setUserNamesandAvatar(viewHolder, pictureModel, storyModel);

        setLikeCommentCount(viewHolder, pictureModel, storyModel);
    }

    private void setUserNamesandAvatar(FreshFeedsViewHolder viewHolder, FreshFeedModel.PictureModel pictureModel, FreshFeedModel.Story storyModel) {
        viewHolder.user_name.setText(pictureModel.getPhotographer_penname().trim());
        viewHolder.writer_name.setText(storyModel.getWriter_penname().trim());
        Glide.with(mContext).load(pictureModel.getPhotographer_avatar()).into(viewHolder.user_photo);
        Glide.with(mContext).load(storyModel.getWriter_avatar()).into(viewHolder.writer_photo);

    }

    private void setLikeCommentCount(FreshFeedsViewHolder viewHolder, FreshFeedModel.PictureModel pictureModel, FreshFeedModel.Story storyModel) {
        viewHolder.story_like_count.setText(storyModel.getLike_count() + " likes");
        viewHolder.story_comment_count.setText(storyModel.getComment_count() + " comments");
        viewHolder.pic_like_count.setText(pictureModel.getLike_count() + " likes");
        viewHolder.pic_story_count.setText(pictureModel.getStory_count() + " stories");
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<FreshFeedModel.FreshFeed> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }


    private class FreshFeedsViewHolder extends RecyclerView.ViewHolder {


        private View rootview;
        private ImageView image, user_photo, writer_photo;
        private TextView user_name, pic_title;
        private TextView writer_name, story_title, story_text;
        private TextView pic_like_count, story_like_count;
        private TextView pic_story_count, story_comment_count;
        private TextView pic_text_buy;

        public FreshFeedsViewHolder(View inflate) {
            super(inflate);

            image = inflate.findViewById(R.id.image);
            user_photo = inflate.findViewById(R.id.user_photo);
            writer_photo = inflate.findViewById(R.id.writer_photo);
            user_name = inflate.findViewById(R.id.user_name);
            pic_title = inflate.findViewById(R.id.pic_title);
            story_title = inflate.findViewById(R.id.story_title);
            story_text = inflate.findViewById(R.id.story_text);


            writer_name = inflate.findViewById(R.id.writer_name);
            pic_like_count = inflate.findViewById(R.id.pic_like_count);
            pic_story_count = inflate.findViewById(R.id.pic_comment_count);
            story_like_count = inflate.findViewById(R.id.story_like_count);
            story_comment_count = inflate.findViewById(R.id.story_comment_count);

            pic_text_buy = inflate.findViewById(R.id.pic_text_by);

        }
    }
}
