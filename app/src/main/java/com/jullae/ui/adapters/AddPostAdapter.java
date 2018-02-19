package com.jullae.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jullae.R;

import java.util.ArrayList;

/**
 * Created by Rahul Abrol on 12/18/17.
 * <p>
 * Class @{@link AddPostAdapter} used to
 * hold tge views of Add Post items.
 */
public class AddPostAdapter extends RecyclerView.Adapter<AddPostAdapter.FeedHolder> {

    private final LayoutInflater inflater;
    private ArrayList<String> list;
    private Context context;

    /**
     * Constructor with two parameters one is the
     * for the calling class context and other is
     * list of feeds.
     *
     * @param context  context
     * @param feedList list items;
     */
    public AddPostAdapter(final Context context, final ArrayList<String> feedList) {
        this.list = feedList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public AddPostAdapter.FeedHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new AddPostAdapter.FeedHolder(inflater
                .inflate(R.layout.adapter_bottom_sheet, parent, false));
    }

    @Override
    public void onBindViewHolder(final AddPostAdapter.FeedHolder holder, final int position) {
        holder.tvItemName.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    /**
     * Class @{@link HomeFeedAdapter.FeedHolder} used to bind
     * the views with the Recycler View.
     */
    class FeedHolder extends RecyclerView.ViewHolder {

        private TextView tvItemName;

        /**
         * Constructor to initialize the view Attribute.
         *
         * @param itemView Item View.
         */
        FeedHolder(final View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItems);
        }
    }
}
