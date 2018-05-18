package com.jullae.ui.adapters;

import android.app.Activity;
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
import com.google.gson.Gson;
import com.jullae.R;
import com.jullae.data.db.model.PictureModel;
import com.jullae.ui.pictureDetail.PictureDetailActivity;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;
import com.jullae.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.PicturesViewHolder> {

    private static final String TAG = PicturesAdapter.class.getName();
    private final Activity mContext;
    private final RequestOptions picOptions;

    List<PictureModel> messagelist = new ArrayList<>();

    public PicturesAdapter(Activity activity) {
        this.mContext = activity;

        picOptions = new RequestOptions();
        picOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    }

    @NonNull
    @Override
    public PicturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PicturesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pictures, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PicturesViewHolder viewHolder, int position) {
        PictureModel pictureModel = messagelist.get(position);


        GlideUtils.loadImagefromUrl(mContext, pictureModel.getPicture_url(), viewHolder.image);
        viewHolder.user_name.setText(pictureModel.getPhotographer_name().trim());
        viewHolder.picture_like_count.setText(pictureModel.getLike_count() + " likes");
        viewHolder.picture_comment_count.setText(pictureModel.getStory_count() + " stories");
        viewHolder.picture_title.setText(pictureModel.getPicture_title());

    }


    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<PictureModel> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }


    public interface ReqListener {
        void onSuccess();

        void onFail();
    }

    public class PicturesViewHolder extends RecyclerView.ViewHolder {


        private View rootview;
        private ImageView image;
        private TextView user_name, picture_title;
        private TextView picture_like_count;
        private TextView picture_comment_count;
        private TextView button_write_story;

        public PicturesViewHolder(View inflate) {
            super(inflate);

            image = inflate.findViewById(R.id.image);
            picture_title = inflate.findViewById(R.id.picture_title);
            user_name = inflate.findViewById(R.id.user_name);
            picture_like_count = inflate.findViewById(R.id.story_like_count);
            picture_comment_count = inflate.findViewById(R.id.story_comment_count);
            button_write_story = inflate.findViewById(R.id.text_write_story);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showFullPictureDialog(mContext, messagelist.get(getAdapterPosition()));
                }
            });

            button_write_story.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AppUtils.showWriteStoryDialog(mContext, messagelist.get(getAdapterPosition()).getPicture_id());
                }
            });
            inflate.findViewById(R.id.view_all_stories).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gson gson = new Gson();
                    String pictureModel = gson.toJson(messagelist.get(getAdapterPosition()));
                    Intent i = new Intent(mContext, PictureDetailActivity.class);
                    i.putExtra("pictureModel", pictureModel);
                    mContext.startActivityForResult(i, AppUtils.REQUEST_CODE_WRITESTORY_FROM_PICTURE_TAB);
                }
            });
            picture_like_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showLikesDialog(mContext, messagelist.get(getAdapterPosition()).getPicture_id(), Constants.LIKE_TYPE_PICTURE);
                }
            });
        }
    }
}
