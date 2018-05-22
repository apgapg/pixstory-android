package com.jullae.ui.home.profile.draftTab;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jullae.R;
import com.jullae.data.db.model.DraftModel;
import com.jullae.data.db.model.PictureModel;
import com.jullae.data.db.model.StoryModel;
import com.jullae.ui.home.homeFeed.freshfeed.HomeFeedAdapter;
import com.jullae.utils.AppUtils;
import com.jullae.utils.GlideUtils;
import com.jullae.utils.dialog.MyProgressDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by master on 1/5/17.
 */

public class DraftTabAdapter extends RecyclerView.Adapter<DraftTabAdapter.SearchFeedViewHolder> {

    private static final String TAG = DraftTabAdapter.class.getName();
    private final Activity mContext;
    private final RequestOptions picOptions;
    private final DraftTabPresentor mPresentor;

    List<DraftModel.FreshFeed> messagelist = new ArrayList<>();

    public DraftTabAdapter(Activity activity, DraftTabPresentor mPresentor) {
        this.mContext = activity;
        this.mPresentor = mPresentor;
        picOptions = new RequestOptions();
        picOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
    }

    @NonNull
    @Override
    public SearchFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchFeedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drafts, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFeedViewHolder viewHolder, int position) {
        PictureModel pictureModel = messagelist.get(position).getPictureModel();
        StoryModel storyModel = messagelist.get(position).getStoryModel();

        GlideUtils.loadImagefromUrl(mContext, pictureModel.getPicture_url_small(), viewHolder.image);
        viewHolder.writer_name.setText(storyModel.getWriter_penname().trim());
        viewHolder.story_title.setText(storyModel.getStory_title());
        viewHolder.story_text.setText(storyModel.getStory_text());

    }


    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    public void add(List<DraftModel.FreshFeed> list) {
        messagelist.clear();
        messagelist.addAll(list);
        Log.d(TAG, "add: list size: " + list.size());
        notifyDataSetChanged();
    }

    private void showDeleteDraftWarningDialog(final int adapterPosition, final String story_id) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Delete draft!");
        alertDialog.setMessage("Are you sure you want to delete this draft?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                MyProgressDialog.showProgressDialog(mContext, "Please Wait!");
                mPresentor.sendDeleteDraftReq(story_id, new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        MyProgressDialog.dismissProgressDialog();
                        Toast.makeText(mContext.getApplicationContext(), "Draft deleted successfully", Toast.LENGTH_SHORT).show();
                        messagelist.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition);
                    }

                    @Override
                    public void onFail() {
                        MyProgressDialog.dismissProgressDialog();

                        Toast.makeText(mContext.getApplicationContext(), "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();

    }

    public interface DeleteListener {
        void onSuccess();

        void onFail();
    }

    public class SearchFeedViewHolder extends RecyclerView.ViewHolder {


        private View rootview;
        private ImageView image;
        private TextView writer_name, story_title, story_text;


        public SearchFeedViewHolder(View inflate) {
            super(inflate);

            image = inflate.findViewById(R.id.image);
            story_title = inflate.findViewById(R.id.story_title);
            story_text = inflate.findViewById(R.id.story_text);
            writer_name = inflate.findViewById(R.id.writer_name);

            inflate.findViewById(R.id.text_delete_draft).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteDraftWarningDialog(getAdapterPosition(), messagelist.get(getAdapterPosition()).getStoryModel().getStory_id());

                }
            });
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showFullPictureDialog(mContext, messagelist.get(getAdapterPosition()).getPictureModel(), new AppUtils.LikeClickListener() {
                        @Override
                        public void onLikeClick() {
                            if (messagelist.get(getAdapterPosition()).getPictureModel().getIs_liked()) {
                                messagelist.get(getAdapterPosition()).getPictureModel().setIs_liked(false);
                                messagelist.get(getAdapterPosition()).getPictureModel().setDecrementLikeCount();
                            } else {
                                messagelist.get(getAdapterPosition()).getPictureModel().setIs_liked(true);
                                messagelist.get(getAdapterPosition()).getPictureModel().setIncrementLikeCount();
                            }
                            mPresentor.setLike(messagelist.get(getAdapterPosition()).getPictureModel().getPicture_id(), new HomeFeedAdapter.ReqListener() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onFail() {
                                    Toast.makeText(mContext.getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
                                    if (messagelist.get(getAdapterPosition()).getPictureModel().getIs_liked()) {
                                        messagelist.get(getAdapterPosition()).getPictureModel().setIs_liked(false);
                                        messagelist.get(getAdapterPosition()).getPictureModel().setDecrementLikeCount();
                                    } else {
                                        messagelist.get(getAdapterPosition()).getPictureModel().setIs_liked(true);
                                        messagelist.get(getAdapterPosition()).getPictureModel().setIncrementLikeCount();
                                    }
                                }
                            }, !messagelist.get(getAdapterPosition()).getPictureModel().getIs_liked());


                        }
                    });
                }
            });

            inflate.findViewById(R.id.rootview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.showWriteStoryDialogWithData(mContext, messagelist.get(getAdapterPosition()).getPictureModel().getPicture_id(), messagelist.get(getAdapterPosition()).getStoryModel().getStory_title(), messagelist.get(getAdapterPosition()).getStoryModel().getStory_text());


                }
            });
        }
    }
}
