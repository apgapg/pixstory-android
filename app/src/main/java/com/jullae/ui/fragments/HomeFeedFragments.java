package com.jullae.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jullae.R;
import com.jullae.constant.AppConstant;
import com.jullae.model.FeedModel;
import com.jullae.model.LikeResponseModel;
import com.jullae.retrofit.APIError;
import com.jullae.retrofit.ApiInterface;
import com.jullae.retrofit.CommonParams;
import com.jullae.retrofit.ResponseResolver;
import com.jullae.retrofit.RestClient;
import com.jullae.ui.adapters.GridAdapter;
import com.jullae.ui.base.BaseFragment;
import com.jullae.utils.DateTimeUtil;
import com.jullae.utils.Utils;

import java.text.ParseException;
import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by Rahul Abrol on 12/14/17.
 * <p>
 * Class @{@link HomeFeedFragments} used to add the
 * functionality of the home feed view.
 */
public class HomeFeedFragments extends BaseFragment {
    private Activity mActivity;
    private LinearLayout linearLayout;
    private FeedListener listener;
    private ArrayList<FeedModel.Feed> feedList;
    private ApiInterface client;

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        try {
            listener = (FeedListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        client = RestClient.getApiInterface();

        linearLayout = view.findViewById(R.id.linearLayout);
        NestedScrollView nestedScrollView = view.findViewById(R.id.nestedScrollView);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(final NestedScrollView v, final int scrollX,
                                       final int scrollY, final int oldScrollX, final int oldScrollY) {

                if (scrollY > oldScrollY) {
                    listener.onScroll(true);
                } else {
                    listener.onScroll(false);
                }
            }
        });
//        setViews();
//      please UNCOMMENT getFeeds method and COMMENT setView.
        getFeeds(1, "10");
    }

    /**
     * Get all the feeds.
     *
     * @param page pages.
     * @param per  items.
     */
    private void getFeeds(final int page, final String per) {
        CommonParams.Builder params = new CommonParams.Builder();
        params.add("page", page);
        params.add("per", per);
//        params.build().getMap()
        Call<FeedModel> data = client.getFeeds();
        data.enqueue(new ResponseResolver<FeedModel>(mActivity, true, false) {
            @Override
            public void success(final FeedModel feedModel) {
                if (feedModel != null && feedModel.getFeed().size() > 0) {
                    setViews(feedModel);
                }
            }

            @Override
            public void failure(final APIError error) {
                Utils.showSnackbar(mActivity, getView(), error.getMessage());
            }
        });
    }

    /**
     * Set Views.
     *
     * @param model model.
     */
    private void setViews(final FeedModel model) {
        linearLayout.removeAllViews();
        if (model != null) {
            feedList = new ArrayList<>();
            feedList = (ArrayList<FeedModel.Feed>) model.getFeed();
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (int i = 0; i < feedList.size(); i++) {
                assert inflater != null;
                View itemView = inflater.inflate(R.layout.adapter_feeds, null, false);
                ImageView ivUserPic = itemView.findViewById(R.id.ivUserPic);
                ImageView ivMore = itemView.findViewById(R.id.ivMore);
                ImageView ivStoryPic = itemView.findViewById(R.id.ivStoryPic);
                ImageView ivLike = itemView.findViewById(R.id.ivLike);
                ImageView ivEditStory = itemView.findViewById(R.id.ivEditStory);
                TextView tvUserName = itemView.findViewById(R.id.tvUserName);
                TextView tvLocation = itemView.findViewById(R.id.tvLocation);
                TextView tvTimeInDays = itemView.findViewById(R.id.tvTimeInDays);
                TextView tvLikes = itemView.findViewById(R.id.tvLikes);
                TextView tvComments = itemView.findViewById(R.id.tvComments);


                final FeedModel.Feed modelData = feedList.get(i);
                tvUserName.setText(modelData.getPhotographerName());
                tvLocation.setText(modelData.getPictureTitle());
                if (modelData.getPictureUrl() != null) {
                    Glide.with(mActivity).load(modelData.getPictureUrl()).into(ivStoryPic);
                }
                if (modelData.getPhotographerDpUrl() != null) {
                    Glide.with(mActivity).load(modelData.getPhotographerDpUrl()).into(ivUserPic);
                }
                try {
                    //convert UTC date into milliseconds and the get relative time.
                    long date = DateTimeUtil.getDateInMilliSec(modelData.getCreatedAt());
                    tvTimeInDays.setText(DateTimeUtil.getTimeAgo(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String comments;
                String likes;
                if (modelData.getStoryCount() > 1) {
                    comments = String.valueOf(modelData.getStoryCount())
                            + AppConstant.SPACE_STRING + getString(R.string.stories);
                } else {
                    comments = String.valueOf(modelData.getStoryCount())
                            + AppConstant.SPACE_STRING + getString(R.string.story);
                }
                tvComments.setText(comments);
                if (modelData.getLikeCount() > 1) {
                    likes = String.valueOf(modelData.getLikeCount())
                            + AppConstant.SPACE_STRING + getString(R.string.likes);
                } else {
                    likes = String.valueOf(modelData.getLikeCount())
                            + AppConstant.SPACE_STRING + getString(R.string.like);
                }
                tvLikes.setText(likes);

                ArrayList<FeedModel.Story> storyList = new ArrayList<>();
                RecyclerView rvStories = itemView.findViewById(R.id.rvStories);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
                rvStories.setLayoutManager(mLayoutManager);

                GridAdapter adapter = new GridAdapter(mActivity, storyList);
                rvStories.setAdapter(adapter);

                if (modelData.getStories().size() > 0) {
                    storyList.addAll(modelData.getStories());
                }
                FeedModel.Story story = new FeedModel.Story();
                story.setWriterName("Add Story");
                story.setId(007);
                storyList.add(story);

                adapter.notifyDataSetChanged();

                final int finalI = i;
                final int id = modelData.getId();
                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        switch (v.getId()) {
                            case R.id.tvComments:
                                listener.onFeedClick(finalI, AppConstant.ITEM_EDIT, id);
                                break;
                            case R.id.ivUserPic:
                                listener.onFeedClick(finalI, AppConstant.ITEM_USER_PIC, id);
                                break;
                            case R.id.ivMore:
                                listener.onFeedClick(finalI, AppConstant.ITEM_MORE, id);
                                break;
                            case R.id.ivStoryPic:
                                listener.onFeedClick(finalI, AppConstant.ITEM_STORY_PIC, id);
                                break;
                            case R.id.tvUserName:
                                listener.onFeedClick(finalI, AppConstant.ITEM_USER_NAME, id);
                                break;
                            case R.id.tvLocation:
                                listener.onFeedClick(finalI, AppConstant.ITEM_LOC, id);
                                break;
                            case R.id.ivLike:
//                                likeApi(modelData.getId() + "");
                                break;
                            case R.id.ivEditStory:

                                break;
//                            case R.id.tvLikes:
//                                listener.onFeedClick(modelData.get, AppConstant.ITEM_LIKE);
//                                break;
                            default:
                                break;
                        }
                    }
                };
                //Listeners Initializations
                tvComments.setOnClickListener(clickListener);
                tvLikes.setOnClickListener(clickListener);
                ivEditStory.setOnClickListener(clickListener);
                ivLike.setOnClickListener(clickListener);
                ivUserPic.setOnClickListener(clickListener);
                ivMore.setOnClickListener(clickListener);
                ivStoryPic.setOnClickListener(clickListener);
                tvUserName.setOnClickListener(clickListener);
                tvLocation.setOnClickListener(clickListener);

                linearLayout.addView(itemView);
            }
        }
    }

    /**
     * Get all the feeds.
     *
     * @param id id.
     */
    private void likeApi(final String id) {
//        CommonParams.Builder params = new CommonParams.Builder();
//        params.add("page", page);
//        params.add("per", per);
//        params.build().getMap()
        Call<LikeResponseModel> data = client.feedLike(id);
        data.enqueue(new ResponseResolver<LikeResponseModel>(mActivity, false, false) {
            @Override
            public void success(final LikeResponseModel feedModel) {

            }

            @Override
            public void failure(final APIError error) {
                Utils.showSnackbar(mActivity, getView(), error.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivity = null;
        listener = null;
    }

    /**
     * update view.
     */
    public void updateList() {
        getFeeds(0, "");
    }

    /**
     * Used as a Callback for click.
     */
    public interface FeedListener {
        /**
         * USed as a callback method.
         *
         * @param position   position of clicked item.
         * @param clickedTag clicked item tag.
         * @param id         id of item
         */
        void onFeedClick(int position, String clickedTag, int id);

        /**
         * Hide button when scroll
         *
         * @param isHide true to hide else false.
         */
        void onScroll(boolean isHide);
    }
}
