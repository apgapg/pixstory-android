package com.jullae.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jullae.R;
import com.jullae.data.db.model.StoryCommentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Abrol on 12/20/17.
 * <p>
 * Class @{@link CommentsAdapter} used as a adapter of
 * the class @{@link com.jullae.ui.fragments.LikeDialogFragment}
 * to hold the elements of dialog fragment to show the users
 * who like the story.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.FeedHolder> {

    private static final String TAG = CommentsAdapter.class.getName();
    private final RequestOptions picOptions;
    List<StoryCommentModel.Comment> messagelist = new ArrayList();
    private Context context;


    public CommentsAdapter(final Context context) {
        this.context = context;
        picOptions = new RequestOptions();
        picOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    }


    @Override
    public CommentsAdapter.FeedHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new CommentsAdapter.FeedHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(final CommentsAdapter.FeedHolder holder, final int position) {

        holder.user_name.setText(messagelist.get(position).getUser_name());

        Glide.with(context).load(messagelist.get(position).getUser_dp_url()).apply(picOptions).into(holder.user_image);
        holder.comment_text.setText(messagelist.get(position).getComment());

    }


    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<StoryCommentModel.Comment> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }

    public void addComment(StoryCommentModel.Comment commentModel) {
        messagelist.add(commentModel);
        notifyItemInserted(messagelist.size() - 1);
    }


    class FeedHolder extends RecyclerView.ViewHolder {

        private ImageView user_image;
        private TextView user_name, comment_text;

        /**
         * Constructor to initialize the view Attribute.
         *
         * @param itemView itemview
         */
        FeedHolder(final View itemView) {
            super(itemView);
            user_image = itemView.findViewById(R.id.image_avatar);
            user_name = itemView.findViewById(R.id.text_name);
            comment_text = itemView.findViewById(R.id.comment_text);

        }

    }

}
