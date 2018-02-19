package com.jullae.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jullae.R;
import com.jullae.constant.AppConstant;
import com.jullae.model.AllLikeModel;
import com.jullae.ui.fragments.LikeDialogFragment;

import java.util.ArrayList;

/**
 * Created by Rahul Abrol on 12/20/17.
 * <p>
 * Class @{@link LikeAdapter} used as a adapter of
 * the class @{@link com.jullae.ui.fragments.LikeDialogFragment}
 * to hold the elements of dialog fragment to show the users
 * who like the story.
 */

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.FeedHolder> {

    private final LayoutInflater inflater;
    private ArrayList<AllLikeModel.Like> list;
    private Context context;
    private LikeAdapter.LikeListener listner;

    /**
     * Constructor with two parameters one is the
     * for the calling class context and other is
     * list of feeds.
     *
     * @param context            context
     * @param feedList           list items;
     * @param likeDialogFragment listner
     */
    public LikeAdapter(final Context context, final ArrayList<AllLikeModel.Like> feedList, final LikeDialogFragment likeDialogFragment) {
        this.list = feedList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        listner = (LikeAdapter.LikeListener) likeDialogFragment;
    }


    @Override
    public LikeAdapter.FeedHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new LikeAdapter.FeedHolder(inflater
                .inflate(R.layout.adapter_likes, parent, false));
    }

    @Override
    public void onBindViewHolder(final LikeAdapter.FeedHolder holder, final int position) {
        holder.tvLikeUserName.setText(String.format("%s%s", context.getString(R.string.at_the_rate),
                list.get(position).getUserPenName()));
        holder.tvLikeName.setText(list.get(position).getUserName());
        if (list.get(position).getUserDpUrl() != null) {
            Glide.with(context).load(list.get(position).getUserDpUrl()).into(holder.ivUserPic);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    /**
     * Used as a Callback for click.
     */
    public interface LikeListener {
        /**
         * USed as a callback method.
         *
         * @param position  position of clicked item.
         * @param tag       tag
         * @param clickedId clicked item id.
         */
        void onClick(int position, String tag, int clickedId);
    }

    /**
     * Class @{@link LikeAdapter.FeedHolder} used to bind
     * the views with the recyclerview.;
     */
    class FeedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivUserPic;
        private TextView tvLikeFollow, tvLikeName, tvLikeUserName;

        /**
         * Constructor to initialize the view Attribute.
         *
         * @param itemView itemview
         */
        FeedHolder(final View itemView) {
            super(itemView);
            ivUserPic = itemView.findViewById(R.id.ivLikeUserPic);
            tvLikeName = itemView.findViewById(R.id.tvLikeName);
            tvLikeUserName = itemView.findViewById(R.id.tvLikeUserName);
            tvLikeFollow = itemView.findViewById(R.id.tvLikeFollow);
            //Listeners Initializations
//            ivUserPic.setOnClickListener(this);
//            tvLikeName.setOnClickListener(this);
            tvLikeFollow.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            switch (v.getId()) {
                case R.id.tvLikeFollow:
                    listner.onClick(getAdapterPosition(), AppConstant.FOLLOW, list.get(getAdapterPosition()).getUserId());
                    break;
//                case R.id.ivUserPic:
////                    listner.onClick(getAdapterPosition(), AppConstant.FOLLOW);
//                    break;
//                case R.id.tvLikeName:
////                    listner.onClick(getAdapterPosition(), AppConstant.FOLLOW);
//                    break;
                default:
                    break;
            }
        }
    }
}
