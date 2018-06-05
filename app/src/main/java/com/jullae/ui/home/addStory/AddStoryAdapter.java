package com.jullae.ui.home.addStory;

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
import com.jullae.data.db.model.AddStoryModel;
import com.jullae.databinding.ItemAddStoryBinding;
import com.jullae.ui.home.homeFeed.freshfeed.HomeFeedAdapter;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class AddStoryAdapter extends RecyclerView.Adapter<AddStoryAdapter.AddStoryViewHolder> {

    private static final String TAG = AddStoryAdapter.class.getName();
    private final Activity mContext;
    private final AddStoryPresentor mPresentor;

    List<AddStoryModel.PictureModel> messagelist = new ArrayList<AddStoryModel.PictureModel>();

    public AddStoryAdapter(Activity activity, AddStoryPresentor mPresentor) {
        this.mContext = activity;
        this.mPresentor = mPresentor;


    }


    @NonNull
    @Override
    public AddStoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAddStoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_add_story, parent, false);
        return new AddStoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddStoryViewHolder holder, int position) {
        holder.binding.setPictureModel(messagelist.get(position));
        holder.binding.executePendingBindings();
    }


    @Override
    public void onBindViewHolder(@NonNull AddStoryViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty())
            holder.binding.executePendingBindings();
        else
            super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<AddStoryModel.PictureModel> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }


    class AddStoryViewHolder extends RecyclerView.ViewHolder {

        private final ItemAddStoryBinding binding;

        public AddStoryViewHolder(ItemAddStoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.imageAddStory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showWriteStoryDialog(mContext, messagelist.get(getAdapterPosition()).getPicture_id());
                }
            });
            binding.likeCount.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (messagelist.get(getAdapterPosition()).getPicture_likes() != 0)
                        AppUtils.showLikesDialog(mContext, messagelist.get(getAdapterPosition()).getPicture_id(), Constants.LIKE_TYPE_PICTURE);

                }
            });

            binding.buttonLike.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    changeLike(getAdapterPosition());
                    mPresentor.setLike(messagelist.get(getAdapterPosition()).getPicture_id(), new HomeFeedAdapter.ReqListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFail() {
                            Toast.makeText(mContext.getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
                            changeLike(getAdapterPosition());
                        }


                    }, !messagelist.get(getAdapterPosition()).getPic_liked());


                }
            });


        }

        private void changeLike(int adapterPosition) {
            if (messagelist.get(adapterPosition).getPic_liked()) {
                messagelist.get(adapterPosition).setPic_liked(false);
                messagelist.get(adapterPosition).setDecrementLikeCount();
            } else {
                messagelist.get(adapterPosition).setPic_liked(true);
                messagelist.get(adapterPosition).setIncrementLikeCount();
            }
            notifyItemChanged(adapterPosition, "like");

        }


    }

}
