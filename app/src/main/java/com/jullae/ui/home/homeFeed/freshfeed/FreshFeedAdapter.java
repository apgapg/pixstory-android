package com.jullae.ui.home.homeFeed.freshfeed;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.data.db.model.FreshFeedModel;
import com.jullae.databinding.ItemFreshFeedsBinding;
import com.jullae.utils.AppUtils;
import com.jullae.utils.DialogUtils;

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
        ItemFreshFeedsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fresh_feeds, parent, false);
        return new FreshFeedsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((FreshFeedsViewHolder) viewHolder).binding.setModel(messagelist.get(position));
        ((FreshFeedsViewHolder) viewHolder).binding.executePendingBindings();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty())
            ((FreshFeedsViewHolder) holder).binding.executePendingBindings();
        else
            super.onBindViewHolder(holder, position, payloads);
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


        private  ItemFreshFeedsBinding binding;

        FreshFeedsViewHolder(final ItemFreshFeedsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


            binding.getRoot().findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (binding.getRoot().findViewById(R.id.container_story).getVisibility() == View.VISIBLE)
                        binding.getRoot().findViewById(R.id.container_story).setVisibility(View.INVISIBLE);
                    else
                        binding.getRoot().findViewById(R.id.container_story).setVisibility(View.VISIBLE);

                }
            });

            binding.getRoot().findViewById(R.id.ivMore).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        DialogUtils.showPictureMoreOptions(mContext, mPresentor, messagelist.get(getAdapterPosition()).getPictureModel());
                }
            });


            binding.getRoot().findViewById(R.id.text_name).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showVisitorProfile(mContext, messagelist.get(getAdapterPosition()).getPictureModel().getPhotographer_penname());
                }
            });
            binding.getRoot().findViewById(R.id.user_photo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showVisitorProfile(mContext, messagelist.get(getAdapterPosition()).getPictureModel().getPhotographer_penname());
                }
            });
            binding.buttonLike.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    changeLike(getAdapterPosition());
                    mPresentor.setLike(messagelist.get(getAdapterPosition()).getPictureModel().getPicture_id(), new HomeFeedAdapter.ReqListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFail() {
                            Toast.makeText(mContext.getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
                            if (messagelist.get(getAdapterPosition()).getPictureModel().getIs_liked()) {
                                messagelist.get(getAdapterPosition()).getPictureModel().setIs_liked(false);
                                messagelist.get(getAdapterPosition()).getPictureModel().setDecrementLikeCount();
                            } else {
                                messagelist.get(getAdapterPosition()).getPictureModel().setIs_liked(true);
                                messagelist.get(getAdapterPosition()).getPictureModel().setIncrementLikeCount();
                            }
                        }
                    }, !messagelist.get(getAdapterPosition()).getPictureModel().getIs_liked());
                }
            });


        }

        private void changeLike(int adapterPosition) {
            if (messagelist.get(adapterPosition).getPictureModel().getIs_liked()) {
                messagelist.get(adapterPosition).getPictureModel().setIs_liked(false);
                messagelist.get(adapterPosition).getPictureModel().setDecrementLikeCount();
            } else {
                messagelist.get(adapterPosition).getPictureModel().setIs_liked(true);
                messagelist.get(adapterPosition).getPictureModel().setIncrementLikeCount();
            }
            notifyItemChanged(adapterPosition, "like");

        }
    }
}
