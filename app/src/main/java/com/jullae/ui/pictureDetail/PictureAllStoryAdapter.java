package com.jullae.ui.pictureDetail;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jullae.R;
import com.jullae.data.db.model.StoryModel;
import com.jullae.ui.storydetails.StoryDetailActivity;
import com.jullae.utils.AppUtils;
import com.jullae.utils.GlideUtils;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class PictureAllStoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = PictureAllStoryAdapter.class.getName();
    private final Activity mContext;

    private List<StoryModel> messagelist = new ArrayList<>();

    public PictureAllStoryAdapter(Activity activity) {
        this.mContext = activity;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PictureAllStoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture_all_story, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PictureAllStoryHolder viewHolder = (PictureAllStoryHolder) holder;

        GlideUtils.loadImagefromUrl(mContext, messagelist.get(position).getWriter_avatar(), viewHolder.user_image);

        viewHolder.user_name.setText(messagelist.get(position).getWriter_name());

        viewHolder.like_count.setText(messagelist.get(position).getLike_count() + " likes");
        viewHolder.comment_count.setText(messagelist.get(position).getComment_count() + " comments");
        viewHolder.story_text.setHtml(messagelist.get(position).getStory_text());


    }


    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<StoryModel> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }


    private class PictureAllStoryHolder extends RecyclerView.ViewHolder {


        private ImageView user_image, image, ivStoryPic, ivEditStory, ivLike, ivMore;
        private TextView user_name, tvLocation, tvTimeInDays, like_count, comment_count, story_count;
        private RecyclerView recycler_view_story;
        private HtmlTextView story_text;

        public PictureAllStoryHolder(View inflate) {
            super(inflate);

            ivMore = inflate.findViewById(R.id.ivMore);
            ivLike = inflate.findViewById(R.id.btn_like);
            user_name = inflate.findViewById(R.id.text_name);
            user_image = inflate.findViewById(R.id.image_avatar);
            story_text = inflate.findViewById(R.id.story_text);
            tvLocation = inflate.findViewById(R.id.penname_field);
            tvTimeInDays = inflate.findViewById(R.id.tvTimeInDays);
            like_count = inflate.findViewById(R.id.like_count);
            comment_count = inflate.findViewById(R.id.comment_count);
         /*   user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showVisitorProfile(messagelist.get(getAdapterPosition()).getWriter_penname());
                }
            });
            user_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showVisitorProfile(messagelist.get(getAdapterPosition()).getWriter_penname());
                }
            });*/

            inflate.findViewById(R.id.rootview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, StoryDetailActivity.class);
                    Gson gson = new Gson();
                    String object = gson.toJson(messagelist.get(getAdapterPosition()));
                    i.putExtra("storymodel", object);
                    i.putExtra("profile", false);
                    mContext.startActivity(i);
                }
            });
            user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showVisitorProfile(mContext, messagelist.get(getAdapterPosition()).getWriter_penname());
                }
            });
            user_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showVisitorProfile(mContext, messagelist.get(getAdapterPosition()).getWriter_penname());
                }
            });
        }


    }

}
