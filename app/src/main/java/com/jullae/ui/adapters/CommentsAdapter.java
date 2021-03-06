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
import com.jullae.utils.DialogUtils;
import com.jullae.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private static final String TAG = CommentsAdapter.class.getName();
    List<CommentModel> messagelist = new ArrayList();
    private Context context;


    public CommentsAdapter(final Context context) {
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        GlideUtils.loadImagefromUrl(context, messagelist.get(position).getUser_avatar(), holder.user_image);
        holder.text.setText(messagelist.get(position).getSpannable_text(context));

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


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView user_image;
        private TextView text;

        /**
         * Constructor to initialize the view Attribute.
         *
         * @param itemView itemview
         */
        ViewHolder(final View itemView) {
            super(itemView);
            user_image = itemView.findViewById(R.id.user_avatar);
            text = itemView.findViewById(R.id.text);

            user_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showVisitorProfile(context, messagelist.get(getAdapterPosition()).getUser_penname());
                }
            });
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showVisitorProfile(context, messagelist.get(getAdapterPosition()).getUser_penname());

                }
            });

            itemView.findViewById(R.id.container_comment).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (messagelist.get(getAdapterPosition()).isIs_self()) {
                        DialogUtils.showCommentDeleteWarning(context, String.valueOf(messagelist.get(getAdapterPosition()).getId()));
                        return true;
                    } else
                        return false;
                }
            });
        }

    }

}
