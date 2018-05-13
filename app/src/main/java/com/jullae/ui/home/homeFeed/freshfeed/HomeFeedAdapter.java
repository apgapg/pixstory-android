package com.jullae.ui.home.homeFeed.freshfeed;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jullae.ApplicationClass;
import com.jullae.GlideApp;
import com.jullae.R;
import com.jullae.data.db.model.LikesModel;
import com.jullae.ui.adapters.LikeAdapter;
import com.jullae.ui.custom.ItemOffLRsetDecoration;
import com.jullae.ui.home.homeFeed.HomeFeedModel;
import com.jullae.ui.home.homeFeed.HomeFeedPresentor;
import com.jullae.ui.home.homeFeed.StoryAdapter;
import com.jullae.ui.storydetails.StoryDetailPresentor;
import com.jullae.ui.writeStory.WriteStoryActivity;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class HomeFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = HomeFeedAdapter.class.getName();
    private final Activity mContext;
    private final RequestOptions picOptions;
    private final HomeFeedPresentor mPresentor;
    private final float calculateoffset;

    List<HomeFeedModel.Feed> messagelist = new ArrayList<HomeFeedModel.Feed>();
    private LikeAdapter likeAdapter;

    public HomeFeedAdapter(Activity activity, HomeFeedPresentor homeFeedPresentor) {
        this.mContext = activity;

        this.mPresentor = homeFeedPresentor;

        picOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        calculateoffset = (((ApplicationClass) activity.getApplication()).getDpWidth() - 20) / 2;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeFeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_feed, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HomeFeedViewHolder viewHolder = (HomeFeedViewHolder) holder;

        GlideApp.with(mContext).load(messagelist.get(position).getPicture_url()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.image);

        viewHolder.user_name.setText(messagelist.get(position).getPhotographer_name());
        viewHolder.user_penname.setText(messagelist.get(position).getPhotographer_penname());

        Glide.with(mContext).load(messagelist.get(position).getPhotographer_avatar()).apply(picOptions).into(viewHolder.user_image);

        viewHolder.like_count.setText(messagelist.get(position).getLike_count() + " likes");
        viewHolder.story_count.setText(messagelist.get(position).getStory_count() + " stories");
        if (messagelist.get(position).getStories().size() != 0) {
            viewHolder.storyAdapter.add(messagelist.get(position).getStories());
            Log.d(TAG, "onBindViewHolder: " + messagelist.get(position).getHighlightStoryIndex());
            viewHolder.linearLayoutManager.scrollToPositionWithOffset(messagelist.get(position).getHighlightStoryIndex(), (int) calculateoffset);
        } else viewHolder.storyAdapter.addEmptyMessage(messagelist.get(position).getPicture_id());


        if (messagelist.get(position).getIs_liked().equals("false")) {
            viewHolder.btn_like.setImageResource(R.drawable.ic_unlike);
            viewHolder.like_count.setTextColor(Color.parseColor("#9e9e9e"));
            viewHolder.like_count.setTypeface(Typeface.DEFAULT);
        } else {
            viewHolder.btn_like.setImageResource(R.drawable.ic_like);
            viewHolder.like_count.setTextColor(Color.parseColor("#424242"));
            viewHolder.like_count.setTypeface(Typeface.DEFAULT_BOLD);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        HomeFeedViewHolder viewHolder = (HomeFeedViewHolder) holder;

        if (payloads.contains("like")) {
            messagelist.get(position).setIs_liked("true");
            viewHolder.btn_like.setImageResource(R.drawable.ic_like);
            viewHolder.like_count.setText(String.valueOf(Integer.parseInt(messagelist.get(position).getLike_count()) + 1) + " likes");
            messagelist.get(position).setLike_count(String.valueOf(Integer.parseInt(messagelist.get(position).getLike_count()) + 1));
            viewHolder.like_count.setTextColor(Color.parseColor("#424242"));
            viewHolder.like_count.setTypeface(Typeface.DEFAULT_BOLD);


        } else if (payloads.contains("unlike")) {
            messagelist.get(position).setIs_liked("false");
            viewHolder.btn_like.setImageResource(R.drawable.ic_unlike);
            if (Integer.parseInt(messagelist.get(position).getLike_count()) != 0) {
                viewHolder.like_count.setText(String.valueOf(Integer.parseInt(messagelist.get(position).getLike_count()) - 1) + " likes");
                messagelist.get(position).setLike_count(String.valueOf(Integer.parseInt(messagelist.get(position).getLike_count()) - 1));

            }
            viewHolder.like_count.setTextColor(Color.parseColor("#9e9e9e"));
            viewHolder.like_count.setTypeface(Typeface.DEFAULT);


        } else
            super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<HomeFeedModel.Feed> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }

    private void showLikesDialog(String id) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        View view = mContext.getLayoutInflater().inflate(R.layout.dialog_likes, null);

        setupRecyclerView(view, id);
        dialogBuilder.setView(view);

        final AlertDialog dialog = dialogBuilder.create();
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    private void setupRecyclerView(View view, String id) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        likeAdapter = new LikeAdapter(mContext, mPresentor);
        recyclerView.setAdapter(likeAdapter);

        mPresentor.getLikeslist(id, Constants.LIKE_TYPE_PICTURE);
    }

    public void onLikesListFetchSuccess(LikesModel likesModel) {
        likeAdapter.add(likesModel.getLikes());
    }

    public void onLikesListFetchFail() {

    }

    private void showMenuOptions(final int adapterPosition, ImageView ivMore) {
        PopupMenu popup = new PopupMenu(mContext, ivMore);
        //inflating menu from xml resource
        popup.inflate(R.menu.picture_options);
        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu1:
                        //handle menu1 click
                        showReportStoryDialog(adapterPosition);
                        break;
                    case R.id.menu2:
                        //handle menu2 click
                        break;
                }
                return false;
            }
        });
        //displaying the popup
        popup.show();
    }

    public interface ReqListener {
        void onSuccess();

        void onFail();
    }

    public void showReportStoryDialog(final int adapterPosition) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        final View view3 = mContext.getLayoutInflater().inflate(R.layout.dialog_report_story, null);
        dialogBuilder.setView(view3);

        final AlertDialog dialog = dialogBuilder.create();

        dialog.show();
        final EditText field_report = view3.findViewById(R.id.field_report);
        final TextView btn_report = view3.findViewById(R.id.report);
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String report = field_report.getText().toString().trim();
                if (report.length() != 0) {

                    view3.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                    btn_report.setVisibility(View.INVISIBLE);

                    mPresentor.reportPicture(report, messagelist.get(adapterPosition).getPicture_id(), new StoryDetailPresentor.StringReqListener() {
                        @Override
                        public void onSuccess() {
                            view3.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                            dialog.dismiss();
                            Toast.makeText(mContext.getApplicationContext(), "Your report has been submitted!", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFail() {
                            view3.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                            btn_report.setVisibility(View.VISIBLE);
                            Toast.makeText(mContext.getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {

                }
            }
        });

    }

    private class HomeFeedViewHolder extends RecyclerView.ViewHolder {


        private ImageView user_image, image, ivStoryPic, ivEditStory, btn_like, ivMore;
        private TextView user_name, user_penname, tvTimeInDays, like_count, story_count;
        private RecyclerView recycler_view_story;
        private LinearLayoutManager linearLayoutManager;
        private StoryAdapter storyAdapter;

        public HomeFeedViewHolder(View inflate) {
            super(inflate);

            user_image = itemView.findViewById(R.id.image_avatar);
            image = itemView.findViewById(R.id.image);
            ivMore = itemView.findViewById(R.id.ivMore);
            btn_like = itemView.findViewById(R.id.btn_like);
            user_name = itemView.findViewById(R.id.text_name);
            user_penname = itemView.findViewById(R.id.text_penname);
            tvTimeInDays = itemView.findViewById(R.id.tvTimeInDays);
            like_count = itemView.findViewById(R.id.like_count);
            story_count = itemView.findViewById(R.id.story_count);

            ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenuOptions(getAdapterPosition(), ivMore);
                }
            });
            itemView.findViewById(R.id.text_write_story).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, WriteStoryActivity.class);
                    i.putExtra("picture_id", messagelist.get(getAdapterPosition()).getPicture_id());
                    mContext.startActivityForResult(i, AppUtils.REQUEST_CODE_WRTIE_STORY);

                }
            });
            recycler_view_story = itemView.findViewById(R.id.recycler_view_story);
            linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            recycler_view_story.setLayoutManager(linearLayoutManager);

            ItemOffLRsetDecoration itemDecoration = new ItemOffLRsetDecoration(mContext, R.dimen.item_offset_4dp);
            recycler_view_story.addItemDecoration(itemDecoration);

            storyAdapter = new StoryAdapter(mContext);
            recycler_view_story.setAdapter(storyAdapter);
            user_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showVisitorProfile(mContext, messagelist.get(getAdapterPosition()).getPhotographer_penname());
                }
            });
            user_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showVisitorProfile(mContext, messagelist.get(getAdapterPosition()).getPhotographer_penname());
                }
            });

            user_penname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showVisitorProfile(mContext, messagelist.get(getAdapterPosition()).getPhotographer_penname());
                }
            });


            btn_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String isLiked = messagelist.get(getAdapterPosition()).getIs_liked();
                    if (isLiked.equals("false"))
                        notifyItemChanged(getAdapterPosition(), "like");
                    else notifyItemChanged(getAdapterPosition(), "unlike");

                    mPresentor.setLike(messagelist.get(getAdapterPosition()).getPicture_id(), new ReqListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFail() {
                            if (messagelist.get(getAdapterPosition()).getIs_liked().equals("false"))
                                notifyItemChanged(getAdapterPosition(), "like");
                            else notifyItemChanged(getAdapterPosition(), "unlike");
                            Toast.makeText(mContext.getApplicationContext(), "couldn't connect!", Toast.LENGTH_SHORT).show();
                        }
                    }, isLiked);
                }
            });

            like_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLikesDialog(messagelist.get(getAdapterPosition()).getPicture_id());
                }
            });


        }


    }


}
