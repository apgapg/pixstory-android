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
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.PicturesViewHolder> {

    private static final String TAG = PicturesAdapter.class.getName();
    private final Activity mContext;
    private final PictureTabPresentor mPresentor;

    List<PictureModel> messagelist = new ArrayList<>();

    public PicturesAdapter(Activity activity, PictureTabPresentor mPresentor) {
        this.mContext = activity;
        this.mPresentor = mPresentor;

    }

    @NonNull
    @Override
    public PicturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPicturesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_pictures, parent, false);
        return new PicturesViewHolder(binding);
        // return new PicturesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pictures, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PicturesViewHolder viewHolder, int position) {
        viewHolder.binding.setPictureModel(messagelist.get(position));
        viewHolder.binding.executePendingBindings();
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

            binding.getRoot().findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showFullPictureDialog(mContext, messagelist.get(getAdapterPosition()), new AppUtils.LikeClickListener() {
                        @Override
                        public void onLikeClick() {
                            if (messagelist.get(getAdapterPosition()).getIs_liked()) {
                                messagelist.get(getAdapterPosition()).setIs_liked(false);
                                messagelist.get(getAdapterPosition()).setDecrementLikeCount();
                            } else {
                                messagelist.get(getAdapterPosition()).setIs_liked(true);
                                messagelist.get(getAdapterPosition()).setIncrementLikeCount();
                            }
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
                }
            });

            binding.getRoot().findViewById(R.id.text_write_story).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AppUtils.showWriteStoryDialog(mContext, messagelist.get(getAdapterPosition()).getPicture_id());
                }
            });
            binding.getRoot().findViewById(R.id.view_all_stories).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gson = new Gson();
                    String pictureModel = gson.toJson(messagelist.get(getAdapterPosition()));
                    Intent i = new Intent(mContext, PictureDetailActivity.class);
                    i.putExtra("pictureModel", pictureModel);
                    mContext.startActivity(i);
                }
            });
            binding.getRoot().findViewById(R.id.story_like_count).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (messagelist.get(getAdapterPosition()).getLike_count() != 0)
                        AppUtils.showLikesDialog(mContext, messagelist.get(getAdapterPosition()).getPicture_id(), Constants.LIKE_TYPE_PICTURE);
                }
            });
        }
    }
}
