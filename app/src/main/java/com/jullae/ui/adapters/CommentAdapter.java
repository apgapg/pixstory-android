package com.jullae.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jullae.R;
import com.jullae.model.CommentModel;

import java.util.ArrayList;

/**
 * Created by Rahul Abrol on 03/01/18.
 * <p>
 * Class used as an adapter classs for comment.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_COMMENT = 1;
    private static final int VIEW_NO_COMMENT = 2;
    private final LayoutInflater inflater;
    private ArrayList<CommentModel> list;
    private Context context;
//    private GridAdapter.StoryClickListener listener;

    /**
     * Constructor with two parameters one is the
     * for the calling class context and other is
     * list of feeds.
     *
     * @param context  context
     * @param feedList list items;
     */
    public CommentAdapter(final Context context, final ArrayList<CommentModel> feedList) {
        this.list = feedList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
//        listener = (GridAdapter.StoryClickListener) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        switch (viewType) {
            case VIEW_COMMENT:
                return new CommentHolder(inflater
                        .inflate(R.layout.adapter_comment, parent, false));
            case VIEW_NO_COMMENT:
                return new NoCommentHolder(inflater
                        .inflate(R.layout.adapter_no_comment, parent, false));
            default:
                return new NoCommentHolder(inflater
                        .inflate(R.layout.adapter_no_comment, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final CommentModel feedModel = list.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_NO_COMMENT:
                NoCommentHolder noCommentHolder = (NoCommentHolder) holder;
                break;
            case VIEW_COMMENT:
                CommentHolder commentHolder = (CommentHolder) holder;
                commentHolder.tvCmntUserName.setText("Rahul abrol");
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(final int position) {
        //If size is equal to 0 then return view type 2 else 1.
        if (list != null && list.size() > 0) {
            return VIEW_COMMENT;
        } else {
            return VIEW_NO_COMMENT;
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    /**
     * Class @{@link GridAdapter.FeedHolder} used to bind
     * the views with the recyclerview.;
     */
    class CommentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvCmntUserName, tvCmntTime, tvCmntStory;

        /**
         * Constructor to initialize the view Attribute.
         *
         * @param itemView Item view.
         */
        CommentHolder(final View itemView) {
            super(itemView);

            tvCmntUserName = itemView.findViewById(R.id.tvCmntUserName);
            tvCmntTime = itemView.findViewById(R.id.tvCmntTime);
            tvCmntStory = itemView.findViewById(R.id.tvCmntStory);
            //Listeners Initializations
            tvCmntStory.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
//            int pos = getAdapterPosition();
//            int id = list.get(getAdapterPosition()).getId();
            switch (v.getId()) {
                case R.id.tvCmntStory:

                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Class @{@link GridAdapter.AddFeedHolder} used to bind
     * the views with the recyclerview.;
     */
    class NoCommentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvAddComment;

        /**
         * Constructor to initialize the view Attribute.
         *
         * @param itemView Item view.
         */
        NoCommentHolder(final View itemView) {
            super(itemView);
            tvAddComment = itemView.findViewById(R.id.tvAddComment);
            //Listeners Initializations
            tvAddComment.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
//            int pos = getAdapterPosition();
//            int id = list.get(getAdapterPosition()).getId();
            switch (v.getId()) {
                case R.id.tvAddComment:
//                    listener.onAddFeed(pos, id);
                    break;
                default:
                    break;
            }
        }
    }
}
