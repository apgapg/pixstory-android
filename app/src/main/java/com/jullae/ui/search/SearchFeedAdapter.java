package com.jullae.ui.search;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jullae.R;
import com.jullae.data.db.model.FeedModel;
import com.jullae.data.db.model.PictureModel;
import com.jullae.data.db.model.StoryModel;
import com.jullae.ui.base.BasePresentor;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;
import com.jullae.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class SearchFeedAdapter extends RecyclerView.Adapter<SearchFeedAdapter.SearchFeedViewHolder> {

    private static final String TAG = SearchFeedAdapter.class.getName();
    private final Activity mContext;
    private final RequestOptions picOptions;
    private final BasePresentor mPresentor;

    List<FeedModel> messagelist = new ArrayList<FeedModel>();

    public SearchFeedAdapter(Activity activity, BasePresentor mPresentor) {
        this.mContext = activity;
        this.mPresentor = mPresentor;
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

        GlideUtils.loadImagefromUrl(mContext, pictureModel.getPicture_url_small(), viewHolder.image);
        viewHolder.story_like_count.setText(storyModel.getLike_count() + " likes");
        viewHolder.story_comment_count.setText(storyModel.getComment_count() + " comments");
        viewHolder.story_title.setText(storyModel.getStory_title());
        viewHolder.story_text.setText(Html.fromHtml(storyModel.getStory_text()));

    }


    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<FeedModel> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }

    public void addAll(List<FeedModel> storyMainModelList) {
        int initialSize = messagelist.size();
        messagelist.addAll(storyMainModelList);
        int finalSize = messagelist.size();
        notifyItemRangeInserted(initialSize, finalSize - initialSize);
    }


    public class SearchFeedViewHolder extends RecyclerView.ViewHolder {


        private View rootview;
        private ImageView image;
        private TextView story_title;
        private TextView story_text;
        private TextView story_like_count;
        private TextView story_comment_count;

        public SearchFeedViewHolder(View inflate) {
            super(inflate);

            image = inflate.findViewById(R.id.image);
            story_title = inflate.findViewById(R.id.story_title);
            story_text = inflate.findViewById(R.id.story_text);
            story_like_count = inflate.findViewById(R.id.story_like_count);
            story_comment_count = inflate.findViewById(R.id.story_comment_count);


            inflate.findViewById(R.id.rootview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showStoryDetailActivity(mContext, messagelist.get(getAdapterPosition()).getStoryModel().getStory_id());

                }
            });

            inflate.findViewById(R.id.story_like_count).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (messagelist.get(getAdapterPosition()).getStoryModel().getLike_count() != 0)
                        AppUtils.showLikesDialog(mContext, messagelist.get(getAdapterPosition()).getStoryModel().getStory_id(), Constants.LIKE_TYPE_STORY);

                }
            });


            inflate.findViewById(R.id.text_view_other).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showPictureDetailActivity(mContext, messagelist.get(getAdapterPosition()).getPictureModel().getPicture_id());
                }
            });
            inflate.findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showPictureDetailActivity(mContext, messagelist.get(getAdapterPosition()).getPictureModel().getPicture_id());
                }
            });

        }
    }
}
