package com.jullae.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jullae.R;
import com.jullae.data.db.model.CommentModel;
import com.jullae.utils.AppUtils;
import com.jullae.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.FeedHolder> {

    private static final String TAG = CommentsAdapter.class.getName();
    List<CommentModel> messagelist = new ArrayList();
    private Context context;


    public CommentsAdapter(final Context context) {
        this.context = context;

    }


    @NonNull
    @Override
    public CommentsAdapter.FeedHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        return new CommentsAdapter.FeedHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentsAdapter.FeedHolder holder, final int position) {

        holder.user_name.setText(messagelist.get(position).getUser_name());

        GlideUtils.loadImagefromUrl(context, messagelist.get(position).getUser_avatar(), holder.user_image);
        holder.comment_text.setText(messagelist.get(position).getComment());

    }


    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<CommentModel> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }

    public void addComment(CommentModel commentModel) {
        messagelist.add(0, commentModel);
        notifyItemInserted(0);
    }

    public void addMore(List<CommentModel> commentModelList) {
        int intialSize = messagelist.size();
        messagelist.addAll(commentModelList);
        int finalSize = messagelist.size();
        notifyItemRangeInserted(intialSize, finalSize - intialSize);
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

            user_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showVisitorProfile(context, messagelist.get(getAdapterPosition()).getUser_penname());
                }
            });
            user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showVisitorProfile(context, messagelist.get(getAdapterPosition()).getUser_penname());

                }
            });
        }

    }

}
