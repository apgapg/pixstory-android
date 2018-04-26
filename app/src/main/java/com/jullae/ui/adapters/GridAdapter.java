package com.jullae.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jullae.R;
import com.jullae.data.db.model.StoryModel;

import java.util.ArrayList;

/**
 * Created by Rahul Abrol on 12/22/17.
 * <p>
 * Class @{@link GridAdapter} used as an
 * adapter of Recycler View.
 */
public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_ADD_STORY_BUTTON = 1;
    private static final int VIEW_SHOW_STORY = 2;
    private final LayoutInflater inflater;
    private ArrayList<StoryModel> list;
    private Context context;
    private StoryClickListener listener;

    /**
     * Constructor with two parameters one is the
     * for the calling class context and other is
     * list of feeds.
     *
     * @param context  context
     * @param feedList list items;
     */
    public GridAdapter(final Context context, final ArrayList<StoryModel> feedList) {
        this.list = feedList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        listener = (StoryClickListener) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        switch (viewType) {
            case VIEW_ADD_STORY_BUTTON:
                return new AddFeedHolder(inflater
                        .inflate(R.layout.fragment_add_feed, parent, false));
            case VIEW_SHOW_STORY:
                return new FeedHolder(inflater
                        .inflate(R.layout.item_story, parent, false));
            default:
                return new AddFeedHolder(inflater
                        .inflate(R.layout.fragment_add_feed, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final StoryModel feedModel = list.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_ADD_STORY_BUTTON:
                AddFeedHolder addFeedHolder = (AddFeedHolder) holder;
                break;
            case VIEW_SHOW_STORY:
                FeedHolder feedHolder = (FeedHolder) holder;

              /*  feedHolder.tvCmntUserName.setText(feedModel.getWriterName());
                try {
                    //convert UTc date into milliseconds and the get relative time.
                    long date = DateTimeUtil.getDateInMilliSec(feedModel.getStoryCreatedAt());
                    feedHolder.tvCmntTime.setText(DateTimeUtil.getTimeAgo(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
                feedHolder.tvCmntStory.setText(feedModel.getStory_text());

                String comments;
                String likes;
                /*if (feedModel.getStoryCommentCount() > 1) {
                    comments = String.valueOf(feedModel.getStoryCommentCount())
                            + AppConstant.SPACE_STRING + context.getString(R.string.comments);
                } else {
                    comments = String.valueOf(feedModel.getStoryCommentCount())
                            + AppConstant.SPACE_STRING + context.getString(R.string.comment);
                }
                feedHolder.tvCmntComments.setText(comments);
                if (feedModel.getStoryLikeCount() > 1) {
                    likes = String.valueOf(feedModel.getStoryLikeCount())
                            + AppConstant.SPACE_STRING + context.getString(R.string.likes);
                } else {
                    likes = String.valueOf(feedModel.getStoryLikeCount())
                            + AppConstant.SPACE_STRING + context.getString(R.string.like);
                }*/
                //  feedHolder.tvCmntLikes.setText(likes);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(final int position) {
        //If id is equal to 007 then return view type 2 else 1.
        if (list != null && list.size() > 0) {
          /*  if (list.get(position).getStory_id() == AppConstant.HEX_ID) {
                return VIEW_ADD_STORY_BUTTON;
            } else {
                return VIEW_SHOW_STORY;
            }*/
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    /**
     * StoryModel Click Listener for click on
     * like,comments and story.
     */
    public interface StoryClickListener {
        /**
         * on Click of story.
         *
         * @param position position clicked.
         * @param id       id of user
         * @param tag      clicked tag
         */
        void onStoryClick(int position, int id, String tag);

        /**
         * method to add feed.
         *
         * @param position position
         * @param id       id of the view.
         */
        void onAddFeed(int position, int id);
    }

    /**
     * Class @{@link FeedHolder} used to bind
     * the views with the recyclerview.;
     */
    class FeedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvCmntUserName, tvCmntTime,
                tvCmntStory, tvCmntLikes, tvCmntComments;

        /**
         * Constructor to initialize the view Attribute.
         *
         * @param itemView Item view.
         */
        FeedHolder(final View itemView) {
            super(itemView);

            tvCmntUserName = itemView.findViewById(R.id.text_name);
            tvCmntTime = itemView.findViewById(R.id.tvCmntTime);
            tvCmntStory = itemView.findViewById(R.id.story_text);
          /*  tvCmntLikes = itemView.findViewById(R.id.likes);
            tvCmntComments = itemView.findViewById(R.id.comments);*/
            //Listeners Initializations
            tvCmntStory.setOnClickListener(this);
            tvCmntLikes.setOnClickListener(this);
            tvCmntComments.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            int pos = getAdapterPosition();
            // int id = list.get(pos).getStory_id();
            /**/          /*  int countLike = list.get(pos).getStoryLikeCount();
            int countComment = list.get(pos).getStoryCommentCount();*/
           /* switch (v.getStory_id()) {
                case R.id.story_text:
                    listener.onStoryClick(pos, id, AppConstant.TAG_STORY);
                    break;
                case R.id.likes:
                    if (countLike > 0) {
                        listener.onStoryClick(pos, id, AppConstant.TAG_LIKE);
                    } else {
                        Toast.makeText(context, "0 like", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.comments:
                    if (countComment > 0) {
                        listener.onStoryClick(pos, id, AppConstant.TAG_COMMENT);
                    } else {
                        Toast.makeText(context, "0 comment", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }*/
        }
    }

    /**
     * Class @{@link AddFeedHolder} used to bind
     * the views with the recyclerview.;
     */
    class AddFeedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Button tvAddStory;

        /**
         * Constructor to initialize the view Attribute.
         *
         * @param itemView Item view.
         */
        AddFeedHolder(final View itemView) {
            super(itemView);
            tvAddStory = itemView.findViewById(R.id.tvAddStory);
            //Listeners Initializations
            tvAddStory.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            int pos = getAdapterPosition();
            // int id = list.get(getAdapterPosition()).getStory_id();
            switch (v.getId()) {
                case R.id.tvAddStory:
                    // listener.onAddFeed(pos, id);
                    break;
                default:
                    break;
            }
        }
    }
}
