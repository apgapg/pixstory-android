package com.jullae.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.R;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.LikesModel;
import com.jullae.data.prefs.SharedPrefsHelper;
import com.jullae.databinding.ItemLikeBinding;
import com.jullae.ui.base.BaseResponseModel;
import com.jullae.utils.AppUtils;
import com.jullae.utils.NetworkUtils;
import com.jullae.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Abrol on 12/20/17.
 */

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.LikeViewHolder> {

    private static final String TAG = LikeAdapter.class.getName();
    private final int mode;
    List<LikesModel.Like> messagelist = new ArrayList();
    private Context context;


    public LikeAdapter(final Context context, int mode) {
        this.context = context;
        this.mode = mode;

    }


    @Override
    public LikeViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        ItemLikeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_like, parent, false);
        return new LikeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeViewHolder holder, int position, @NonNull List<Object> payloads) {

        if (!payloads.isEmpty()) {
            holder.binding.executePendingBindings();
        } else
            super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(final LikeViewHolder holder, final int position) {
        holder.binding.setLikeModel(messagelist.get(position));
        if (messagelist.get(position).getUser_penname().equals(SharedPrefsHelper.getInstance().getKeyPenname()))
            holder.binding.userFollowed.setVisibility(View.INVISIBLE);
        else holder.binding.userFollowed.setVisibility(View.VISIBLE);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<LikesModel.Like> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }

    public void addMore(List<LikesModel.Like> likes) {
        int initialSize = messagelist.size();
        messagelist.addAll(likes);
        int finalSize = messagelist.size();
        notifyItemRangeInserted(initialSize, finalSize - initialSize);
    }


    public interface FollowReqListener {
        void onSuccess();

        void onFail();

    }


    class LikeViewHolder extends RecyclerView.ViewHolder {
        private final ItemLikeBinding binding;

        LikeViewHolder(ItemLikeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        /*    if (mode == Constants.FOLLOWING_LIST)
                binding.userFollowed.setVisibility(View.INVISIBLE);*/

            binding.getRoot().findViewById(R.id.rootview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showVisitorProfile(context, messagelist.get(getAdapterPosition()).getUser_penname());
                }
            });
            binding.userFollowed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!messagelist.get(getAdapterPosition()).getUser_followed()) {
                        messagelist.get(getAdapterPosition()).setUser_followed(true);
                    } else {
                        messagelist.get(getAdapterPosition()).setUser_followed(false);
                    }
                    notifyItemChanged(getAdapterPosition(), "follow");

                    AppDataManager.getInstance().getmApiHelper().makeFollowReq(messagelist.get(getAdapterPosition()).getUser_id(), !messagelist.get(getAdapterPosition()).getUser_followed()).getAsObject(BaseResponseModel.class, new ParsedRequestListener<BaseResponseModel>() {

                        @Override
                        public void onResponse(BaseResponseModel response) {
                            NetworkUtils.parseResponse(TAG, response);
                            if (AppDataManager.getInstance().getmUserProfile(null).getValue() != null)
                                AppDataManager.getInstance().getmUserProfile(null).getValue().data.incrementFollowingCount();
                        }

                        @Override
                        public void onError(ANError anError) {
                            NetworkUtils.parseError(TAG, anError);
                            ToastUtils.showNoInternetToast(context);
                            if (!messagelist.get(getAdapterPosition()).getUser_followed()) {
                                messagelist.get(getAdapterPosition()).setUser_followed(true);
                            } else {
                                messagelist.get(getAdapterPosition()).setUser_followed(false);
                            }
                            notifyItemChanged(getAdapterPosition(), "follow");
                        }
                    });
                }
            });
        }
    }
}
