package com.jullae.ui.message;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jullae.R;
import com.jullae.model.MessageModel;
import com.jullae.ui.homefeed.HomeFeedPresentor;
import com.jullae.ui.storydetails.StoryDetailPresentor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Abrol on 12/20/17.
 * <p>
 * Class @{@link MessageAdapter} used as a adapter of
 * the class @{@link com.jullae.ui.fragments.LikeDialogFragment}
 * to hold the elements of dialog fragment to show the users
 * who like the story.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    private static final String TAG = MessageAdapter.class.getName();
    private final RequestOptions picOptions;
    private final String currentUserId;
    List<MessageModel.Message> messagelist = new ArrayList<>();
    private HomeFeedPresentor mPresentor;
    private Context context;
    private StoryDetailPresentor mStoryPresentor;


    public MessageAdapter(final Context context, String currentUserId) {
        this.context = context;
        this.currentUserId = currentUserId;
        picOptions = new RequestOptions();
        picOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    }


    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        if (viewType == 1)
            return new MessageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_self, parent, false));
        else
            return new MessageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_visitor, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageHolder holder, final int position) {

        holder.user_name.setText(messagelist.get(position).getSent_by_id());
        holder.message.setText(messagelist.get(position).getMessage());
    }

    @Override
    public int getItemViewType(int position) {
        if (messagelist.get(position).getSent_by_id().equals(currentUserId)) {
            return 1;
        } else
            return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<MessageModel.Message> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }

    public void addMessage(MessageModel.Message messageModel) {
        messagelist.add(messageModel);
        notifyItemInserted(messagelist.size() - 1);
    }


    class MessageHolder extends RecyclerView.ViewHolder {

        private TextView user_name, message;

        /**
         * Constructor to initialize the view Attribute.
         *
         * @param itemView itemview
         */
        MessageHolder(final View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.text_name);
            message = itemView.findViewById(R.id.text_message);

        }

    }
}
