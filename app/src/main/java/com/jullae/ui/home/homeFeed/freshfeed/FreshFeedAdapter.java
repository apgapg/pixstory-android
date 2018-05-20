package com.jullae.ui.home.homeFeed.freshfeed;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jullae.R;
import com.jullae.data.db.model.FreshFeedModel;
import com.jullae.data.db.model.PictureModel;
import com.jullae.data.db.model.StoryModel;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;
import com.jullae.utils.DateUtils;
import com.jullae.utils.GlideUtils;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class FreshFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = FreshFeedAdapter.class.getName();
    private final Activity mContext;

    List<FreshFeedModel.FreshFeed> messagelist = new ArrayList<>();

    public FreshFeedAdapter(Activity activity) {
        this.mContext = activity;


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FreshFeedsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fresh_feeds, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FreshFeedsViewHolder viewHolder = (FreshFeedsViewHolder) holder;
        PictureModel pictureModel = messagelist.get(position).getPictureModel();
        StoryModel storyModel = messagelist.get(position).getStoryModel();

        GlideUtils.loadImagefromUrl(mContext, pictureModel.getPicture_url_small(), viewHolder.image);
        if (pictureModel.getPicture_title() != null && pictureModel.getPicture_title().isEmpty()) {
            viewHolder.pic_text_by.setText("by");
            viewHolder.pic_title.setVisibility(View.VISIBLE);
            viewHolder.pic_title.setText(pictureModel.getPicture_title().trim().concat(" "));

        } else {
            viewHolder.pic_title.setVisibility(View.GONE);
            viewHolder.pic_text_by.setText("By");
        }

        viewHolder.time.setText(DateUtils.getTimeAgo(messagelist.get(position).getPictureModel().getCreated_at()));
        viewHolder.story_title.setText(storyModel.getStory_title());
        viewHolder.story_text.setHtml(storyModel.getStory_text());
        setUserNamesandAvatar(viewHolder, pictureModel, storyModel);

        setLikeCommentCount(viewHolder, pictureModel, storyModel);
        viewHolder.text_view_more_story.setText("view all " + messagelist.get(position).getPictureModel().getStory_count() + " stories");
    }

    private void setUserNamesandAvatar(FreshFeedsViewHolder viewHolder, PictureModel pictureModel, StoryModel storyModel) {
        viewHolder.user_name.setText(pictureModel.getPhotographer_penname().trim());
        viewHolder.writer_name.setText(storyModel.getWriter_penname().trim());
        GlideUtils.loadImagefromUrl(mContext, pictureModel.getPhotographer_avatar(), viewHolder.user_image);
        GlideUtils.loadImagefromUrl(mContext, storyModel.getWriter_avatar(), viewHolder.writer_photo);

    }

    private void setLikeCommentCount(FreshFeedsViewHolder viewHolder, PictureModel pictureModel, StoryModel storyModel) {
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
        private ImageView image, user_image, writer_photo;
        private TextView user_name, pic_title;
        private TextView writer_name, story_title;
        private HtmlTextView story_text;
        private TextView pic_like_count, story_like_count;
        private TextView pic_story_count, story_comment_count, text_view_more_story;
        private TextView pic_text_by, write_story;
        private TextView time;

        public FreshFeedsViewHolder(View inflate) {
            super(inflate);

            image = inflate.findViewById(R.id.image);
            user_image = inflate.findViewById(R.id.user_photo);
            writer_photo = inflate.findViewById(R.id.writer_photo);
            user_name = inflate.findViewById(R.id.text_name);
            pic_title = inflate.findViewById(R.id.pic_title);
            story_title = inflate.findViewById(R.id.story_title);
            story_text = inflate.findViewById(R.id.story_text);
            text_view_more_story = inflate.findViewById(R.id.text_view_more_story);
            time = inflate.findViewById(R.id.text_time);


            write_story = inflate.findViewById(R.id.write_story);

            writer_name = inflate.findViewById(R.id.writer_name);
            pic_like_count = inflate.findViewById(R.id.pic_like_count);
            pic_story_count = inflate.findViewById(R.id.pic_comment_count);
            story_like_count = inflate.findViewById(R.id.story_like_count);
            story_comment_count = inflate.findViewById(R.id.story_comment_count);

            write_story.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showWriteStoryDialog(mContext, messagelist.get(getAdapterPosition()).getPictureModel().getPicture_id());
                }
            });
            pic_text_by = inflate.findViewById(R.id.pic_text_by);

            text_view_more_story.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showPictureDetailActivity(mContext, messagelist.get(getAdapterPosition()).getPictureModel().getPicture_id());

                }
            });
            user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showVisitorProfile(mContext, messagelist.get(getAdapterPosition()).getPictureModel().getPhotographer_penname());
                }
            });
            user_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showVisitorProfile(mContext, messagelist.get(getAdapterPosition()).getPictureModel().getPhotographer_penname());
                }
            });
            writer_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showVisitorProfile(mContext, messagelist.get(getAdapterPosition()).getPictureModel().getPhotographer_penname());
                }
            });


            pic_like_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showLikesDialog(mContext, messagelist.get(getAdapterPosition()).getPictureModel().getPicture_id(), Constants.LIKE_TYPE_PICTURE);
                }
            });

            story_like_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showLikesDialog(mContext, messagelist.get(getAdapterPosition()).getStoryModel().getStory_id(), Constants.LIKE_TYPE_STORY);
                }
            });


        }
    }
}
