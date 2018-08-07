package com.jullae.ui.home.homeFeed.freshfeed;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.jullae.utils.DialogUtils;
import com.jullae.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class FreshFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = FreshFeedAdapter.class.getName();
    private final Activity mContext;
    private final FreshFeedPresentor mPresentor;

    List<FreshFeedModel.FreshFeed> messagelist = new ArrayList<>();

    public FreshFeedAdapter(Activity activity, FreshFeedPresentor mPresentor) {
        this.mContext = activity;
        this.mPresentor = mPresentor;


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

        viewHolder.story_text.setText(Html.fromHtml(storyModel.getStory_text()));
        setUserNamesandAvatar(viewHolder, pictureModel, storyModel);

        setLikeCommentCount(viewHolder, pictureModel, storyModel);
    }

    private void setUserNamesandAvatar(FreshFeedsViewHolder viewHolder, PictureModel pictureModel, StoryModel storyModel) {
        viewHolder.user_name.setText(pictureModel.getPhotographer_penname().trim());
        viewHolder.writer_name.setText(storyModel.getWriter_penname().trim());
        GlideUtils.loadImagefromUrl(mContext, pictureModel.getPhotographer_avatar(), viewHolder.user_image);

    }

    private void setLikeCommentCount(FreshFeedsViewHolder viewHolder, PictureModel pictureModel, StoryModel storyModel) {
        viewHolder.story_like_count.setText(storyModel.getLike_count() + " likes");
        viewHolder.story_comment_count.setText(storyModel.getComment_count() + " comments");

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

    public void addMore(List<FreshFeedModel.FreshFeed> list) {
        int initialSize = messagelist.size();
        messagelist.addAll(list);
        int finalSize = messagelist.size();
        notifyItemRangeInserted(initialSize, finalSize - initialSize);
    }


    private class FreshFeedsViewHolder extends RecyclerView.ViewHolder {


        private View rootview;
        private ImageView image, user_image;
        private TextView user_name, pic_title;
        private TextView writer_name, story_title;
        private TextView story_text;
        private TextView pic_like_count, story_like_count;
        private TextView pic_story_count, story_comment_count;
        private TextView pic_text_by, write_story;

        public FreshFeedsViewHolder(final View inflate) {
            super(inflate);

            image = inflate.findViewById(R.id.image);
            user_image = inflate.findViewById(R.id.user_photo);
            user_name = inflate.findViewById(R.id.text_name);
            pic_title = inflate.findViewById(R.id.pic_title);
            story_title = inflate.findViewById(R.id.story_title);
            story_text = inflate.findViewById(R.id.story_text);


            write_story = inflate.findViewById(R.id.write_story);

            writer_name = inflate.findViewById(R.id.writer_name);
            pic_like_count = inflate.findViewById(R.id.pic_like_count);
            pic_story_count = inflate.findViewById(R.id.pic_comment_count);
            story_like_count = inflate.findViewById(R.id.story_like_count);
            story_comment_count = inflate.findViewById(R.id.story_comment_count);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (inflate.findViewById(R.id.container_story).getVisibility() == View.VISIBLE)
                        inflate.findViewById(R.id.container_story).setVisibility(View.INVISIBLE);
                    else inflate.findViewById(R.id.container_story).setVisibility(View.VISIBLE);

                }
            });


          /*  write_story.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showWriteStoryDialog(mContext, messagelist.get(getAdapterPosition()).getPictureModel().getPicture_id());
                }
            });*/
            pic_text_by = inflate.findViewById(R.id.pic_text_by);
            inflate.findViewById(R.id.ivMore).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (messagelist.get(getAdapterPosition()).getPictureModel().getLike_count() != 0)
                        DialogUtils.showPictureMoreOptions(mContext, mPresentor, messagelist.get(getAdapterPosition()).getPictureModel());
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



          /*  pic_like_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (messagelist.get(getAdapterPosition()).getPictureModel().getLike_count() != 0)
                        AppUtils.showLikesDialog(mContext, messagelist.get(getAdapterPosition()).getPictureModel().getPicture_id(), Constants.LIKE_TYPE_PICTURE);
                }
            });*/

           /* story_like_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!messagelist.get(getAdapterPosition()).getStoryModel().getLike_count().equals("0"))

                        AppUtils.showLikesDialog(mContext, messagelist.get(getAdapterPosition()).getStoryModel().getStory_id(), Constants.LIKE_TYPE_STORY);
                }
            });
*/

        }
    }
}
