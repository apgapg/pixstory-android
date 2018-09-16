package com.jullae.ui.home.profile.message;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jullae.R;
import com.jullae.data.db.model.ConversationModel;
import com.jullae.ui.home.homeFeed.HomeFeedPresentor;
import com.jullae.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Abrol on 12/20/17.
 * <p>
 * Class @{@link ConversationAdapter} used as a adapter of
 * the class @{@link com.jullae.ui.fragments.LikeDialogFragment}
 * to hold the elements of dialog fragment to show the users
 * who like the story.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ConversationHolder> {

    private static final String TAG = ConversationAdapter.class.getName();
    private final RequestOptions picOptions;
    private List<ConversationModel.Conversation> messagelist = new ArrayList<>();
    private HomeFeedPresentor mPresentor;
    private Context context;


    public ConversationAdapter(final Context context) {
        this.context = context;
        picOptions = new RequestOptions();
        picOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        this.mPresentor = mPresentor;
    }


    @NonNull
    @Override
    public ConversationHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        return new ConversationHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ConversationHolder holder, final int position) {
        holder.user_name.setText(messagelist.get(position).getName());
        holder.user_penname.setText(messagelist.get(position).getPenname());
        GlideUtils.loadImagefromUrl(context, messagelist.get(position).getUser_avatar(), holder.user_image);
    }


    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<ConversationModel.Conversation> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }


    class ConversationHolder extends RecyclerView.ViewHolder {

        private ImageView user_image;
        private View container;
        private TextView user_name, user_penname;

        ConversationHolder(final View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.rootview);
            user_image = itemView.findViewById(R.id.user_avatar);
            user_name = itemView.findViewById(R.id.user_name);
            user_penname = itemView.findViewById(R.id.text_penname);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, MessageActivity.class);
                    i.putExtra("user_id", messagelist.get(getAdapterPosition()).getUser_id());
                    i.putExtra("user_name", messagelist.get(getAdapterPosition()).getName());
                    i.putExtra("user_avatar", messagelist.get(getAdapterPosition()).getUser_avatar());
                    context.startActivity(i);
                }
            });

        }

    }
}
