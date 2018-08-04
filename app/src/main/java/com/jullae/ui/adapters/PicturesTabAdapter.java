package com.jullae.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jullae.R;
import com.jullae.data.db.model.PictureModel;
import com.jullae.databinding.ItemPicturesBinding;
import com.jullae.ui.home.homeFeed.freshfeed.HomeFeedAdapter;
import com.jullae.ui.home.profile.pictureTab.PictureTabPresentor;
import com.jullae.ui.pictureDetail.PictureDetailActivity;
import com.jullae.ui.writeStory.WriteStoryActivity;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;
import com.jullae.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class PicturesTabAdapter extends RecyclerView.Adapter<PicturesTabAdapter.PicturesViewHolder> {

    private static final String TAG = PicturesTabAdapter.class.getName();
    private final Activity mContext;
    private final PictureTabPresentor mPresentor;

    List<PictureModel> messagelist = new ArrayList<>();

    public PicturesTabAdapter(Activity activity, PictureTabPresentor mPresentor) {
        this.mContext = activity;
        this.mPresentor = mPresentor;

    }

    @NonNull
    @Override
    public PicturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPicturesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_pictures, parent, false);
        return new PicturesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PicturesViewHolder viewHolder, int position) {
        viewHolder.binding.setModel(messagelist.get(position));
        viewHolder.binding.executePendingBindings();
    }


    @Override
    public void onBindViewHolder(@NonNull PicturesViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty())
            holder.binding.executePendingBindings();
        else
            super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<PictureModel> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }

    public void addMore(List<PictureModel> pictureModelList) {
        int initialSize = messagelist.size();
        messagelist.addAll(pictureModelList);
        int finalSize = messagelist.size();
        notifyItemRangeInserted(initialSize, finalSize - initialSize);
    }


    public interface ReqListener {
        void onSuccess();

        void onFail();
    }

    public class PicturesViewHolder extends RecyclerView.ViewHolder {


        private final ItemPicturesBinding binding;

        public PicturesViewHolder(ItemPicturesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


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
                            if (messagelist.get(getAdapterPosition()).getIs_liked()) {
                                messagelist.get(getAdapterPosition()).setIs_liked(false);
                                messagelist.get(getAdapterPosition()).setDecrementLikeCount();
                            } else {
                                messagelist.get(getAdapterPosition()).setIs_liked(true);
                                messagelist.get(getAdapterPosition()).setIncrementLikeCount();
                            }
                        }
                    }, !messagelist.get(getAdapterPosition()).getIs_liked());
                }
            });

            binding.getRoot().findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gson = new Gson();
                    String pictureModel = gson.toJson(messagelist.get(getAdapterPosition()));
                    Intent i = new Intent(mContext, PictureDetailActivity.class);
                    i.putExtra("pictureModel", pictureModel);
                    mContext.startActivity(i);
                }
            });
            binding.getRoot().findViewById(R.id.like_count).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (messagelist.get(getAdapterPosition()).getLike_count() != 0)
                        AppUtils.showLikesDialog(mContext, messagelist.get(getAdapterPosition()).getPicture_id(), Constants.LIKE_TYPE_PICTURE);
                }
            });
            binding.getRoot().findViewById(R.id.ivMore).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (messagelist.get(getAdapterPosition()).getLike_count() != 0)
                        DialogUtils.showPictureMoreOptions(mContext, mPresentor, messagelist.get(getAdapterPosition()));
                }
            });


            binding.getRoot().findViewById(R.id.text_add_story).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, WriteStoryActivity.class);
                    i.putExtra("picture_id", messagelist.get(getAdapterPosition()).getPicture_id());
                    mContext.startActivity(i);
                }
            });


        }

        private void changeLike(int adapterPosition) {
            if (messagelist.get(adapterPosition).getIs_liked()) {
                messagelist.get(adapterPosition).setIs_liked(false);
                messagelist.get(adapterPosition).setDecrementLikeCount();
            } else {
                messagelist.get(adapterPosition).setIs_liked(true);
                messagelist.get(adapterPosition).setIncrementLikeCount();
            }
            notifyItemChanged(adapterPosition, "like");

        }

    }
}
