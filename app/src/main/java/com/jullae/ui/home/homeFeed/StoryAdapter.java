package com.jullae.ui.home.homeFeed;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.jullae.R;
import com.jullae.data.db.model.StoryModel;
import com.jullae.databinding.ItemStoryBinding;
import com.jullae.ui.home.HomeActivity;
import com.jullae.ui.storydetails.StoryDetailActivity;
import com.jullae.ui.writeStory.WriteStoryActivity;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;

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
    private String pictureUrl;

    public StoryAdapter(Activity activity, String picture_id) {
        this.mContext = activity;
        picOptions = new RequestOptions();
        picOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        this.picture_id = picture_id;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1)
            return new HomeFeedViewHolder((ItemStoryBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_story, parent, false));

            //  return new HomeFeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false));
        else if (viewType == 2)
            return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_story_list, parent, false));
        else
            return new AddStoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_story_list, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (messagelist.get(position) instanceof StoryModel) {
            ((HomeFeedViewHolder) holder).binding.setStorymodel((StoryModel) messagelist.get(position));
            ((HomeFeedViewHolder) holder).binding.executePendingBindings();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messagelist.get(position) instanceof StoryModel)
            return 1;
        else if (messagelist.get(position).equals("empty"))
            return 2;
        else
            return 3;
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<StoryModel> list) {
        messagelist.clear();
        messagelist.addAll(list);
        messagelist.add("new_story");
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }

    public void addEmptyMessage(String picture_id, String picture_url) {
        this.picture_id = picture_id;
        this.pictureUrl = picture_url;
        messagelist.clear();
        messagelist.add("empty");
        notifyDataSetChanged();
    }

    public void setPictureId(String picture_id) {
        this.picture_id = picture_id;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }


    private class HomeFeedViewHolder extends RecyclerView.ViewHolder {


        private final ItemStoryBinding binding;

        public HomeFeedViewHolder(ItemStoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.rootview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, StoryDetailActivity.class);
                    Gson gson = new Gson();
                    String object = gson.toJson(messagelist.get(getAdapterPosition()));
                    i.putExtra("storymodel", object);
                    mContext.startActivityForResult(i, AppUtils.REQUEST_CODE_SEARCH_TAG);
                }
            });

            binding.storyText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, StoryDetailActivity.class);
                    Gson gson = new Gson();
                    String object = gson.toJson(messagelist.get(getAdapterPosition()));
                    i.putExtra("storymodel", object);
                    mContext.startActivityForResult(i, AppUtils.REQUEST_CODE_SEARCH_TAG);
                }
            });
            binding.userName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivity) mContext).showVisitorProfile(((StoryModel) messagelist.get(getAdapterPosition())).getWriter_penname());
                }
            });
            binding.userAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivity) mContext).showVisitorProfile(((StoryModel) messagelist.get(getAdapterPosition())).getWriter_penname());
                }
            });

            binding.likeCount.findViewById(R.id.like_count).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((StoryModel) messagelist.get(getAdapterPosition())).getLike_count() != 0)
                        AppUtils.showLikesDialog(mContext, ((StoryModel) messagelist.get(getAdapterPosition())).getStory_id(), Constants.LIKE_TYPE_STORY);

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
                    i.putExtra("image", pictureUrl);
                    mContext.startActivity(i);
                }
            });
        }
    }

    private class AddStoryViewHolder extends RecyclerView.ViewHolder {

        public AddStoryViewHolder(View inflate) {
            super(inflate);


            inflate.findViewById(R.id.text_add_story).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, WriteStoryActivity.class);
                    i.putExtra("picture_id", picture_id);
                    i.putExtra("image", pictureUrl);

                    mContext.startActivity(i);
                }
            });
        }
    }


}
