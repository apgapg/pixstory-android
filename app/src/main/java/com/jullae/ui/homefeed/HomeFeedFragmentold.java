package com.jullae.ui.homefeed;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jullae.R;
import com.jullae.app.AppController;
import com.jullae.model.LikeResponseModel;
import com.jullae.retrofit.APIError;
import com.jullae.retrofit.ApiInterface;
import com.jullae.retrofit.CommonParams;
import com.jullae.retrofit.ResponseResolver;
import com.jullae.retrofit.RestClient;
import com.jullae.ui.base.BaseFragment;
import com.jullae.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by Rahul Abrol on 12/14/17.
 * <p>
 * Class @{@link HomeFeedFragmentold} used to add the
 * functionality of the home feed view.
 */
public class HomeFeedFragmentold extends BaseFragment implements HomeFeedContract.HomeFeedFragmentView {
    public static final String token = "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MjA3NTkwOTUsImlhdCI6MTUyMDY3MjY5NSwiaXNzIjoianVsbGFlLmNvbSIsInNjb3BlcyI6W10sInVzZXIiOnsidXNlcl9pZCI6NH19.Cj3LeNQRoJ2EgRodiB_ULhrD3JEKBNPPAIl7_MwwKuw";
    private Activity mActivity;
    private LinearLayout linearLayout;
    private FeedListener listener;
    private ArrayList<HomeFeedModel.Feed> feedList;
    private ApiInterface client;
    private HomeFeedPresentor mHomeFeedPresentor;
    private Activity mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity)
            mContext = (Activity) context;
        else throw new IllegalArgumentException("Context should be an instance of Activity");
    }

    @Override
    public void onDestroy() {
        mContext = null;

        super.onDestroy();


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_feed, container, false);
        mHomeFeedPresentor = new HomeFeedPresentor(((AppController) mContext.getApplication()).getmAppDataManager());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        client = RestClient.getApiInterface();

        //  linearLayout = view.findViewById(R.story_id.linearLayout);
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
        //getFeeds(1, "10");
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
        Call<HomeFeedModel> data = client.getFeeds();
        data.enqueue(new ResponseResolver<HomeFeedModel>(mActivity, true, false) {
            @Override
            public void success(final HomeFeedModel homeFeedModel) {
                /*if (homeFeedModel != null && homeFeedModel.getFeed().size() > 0) {
                    //setViews(homeFeedModel);
                }*/
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
  /*  private void setViews(final HomeFeedModel model) {
        linearLayout.removeAllViews();
        if (model != null) {
            feedList = new ArrayList<>();
            feedList = (ArrayList<HomeFeedModel.Feed>) model.getFeed();
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (int i = 0; i < feedList.size(); i++) {
                assert inflater != null;
                View itemView = inflater.inflate(R.layout.item_home_feed, null, false);
                ImageView ivUserPic = itemView.findViewById(R.story_id.user_image);
                ImageView ivMore = itemView.findViewById(R.story_id.ivMore);
                ImageView ivStoryPic = itemView.findViewById(R.story_id.image);
                ImageView ivLike = itemView.findViewById(R.story_id.ivLike);
                ImageView ivEditStory = itemView.findViewById(R.story_id.ivEditStory);
                TextView tvUserName = itemView.findViewById(R.story_id.user_name);
                TextView tvLocation = itemView.findViewById(R.story_id.tvLocation);
                TextView tvTimeInDays = itemView.findViewById(R.story_id.tvTimeInDays);
                TextView tvLikes = itemView.findViewById(R.story_id.like_count);
                TextView tvComments = itemView.findViewById(R.story_id.story_count);


                final HomeFeedModel.Feed modelData = feedList.get(i);
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

                ArrayList<HomeFeedModel.Story> storyList = new ArrayList<>();
                RecyclerView rvStories = itemView.findViewById(R.story_id.recycler_view_story);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
                rvStories.setLayoutManager(mLayoutManager);

                GridAdapter adapter = new GridAdapter(mActivity, storyList);
                rvStories.setAdapter(adapter);

                if (modelData.getStories().size() > 0) {
                    storyList.addAll(modelData.getStories());
                }
                HomeFeedModel.Story story = new HomeFeedModel.Story();
                story.setWriterName("Add Story");
                story.setId(007);
                storyList.add(story);

                adapter.notifyDataSetChanged();

                final int finalI = i;
                final int story_id = modelData.getStory_id();
                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        switch (v.getStory_id()) {
                            case R.story_id.story_count:
                                listener.onFeedClick(finalI, AppConstant.ITEM_EDIT, story_id);
                                break;
                            case R.story_id.user_image:
                                listener.onFeedClick(finalI, AppConstant.ITEM_USER_PIC, story_id);
                                break;
                            case R.story_id.ivMore:
                                listener.onFeedClick(finalI, AppConstant.ITEM_MORE, story_id);
                                break;
                            case R.story_id.image:
                                listener.onFeedClick(finalI, AppConstant.ITEM_STORY_PIC, story_id);
                                break;
                            case R.story_id.user_name:
                                listener.onFeedClick(finalI, AppConstant.ITEM_USER_NAME, story_id);
                                break;
                            case R.story_id.tvLocation:
                                listener.onFeedClick(finalI, AppConstant.ITEM_LOC, story_id);
                                break;
                            case R.story_id.ivLike:
//                                likeApi(modelData.getStory_id() + "");
                                break;
                            case R.story_id.ivEditStory:

                                break;
//                            case R.story_id.tvLikes:
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

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 16, 16);
                itemView.setLayoutParams(layoutParams);
                linearLayout.addView(itemView);
            }
        }
    }*/

    /**
     * Get all the feeds.
     *
     * @param id story_id.
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
         * @param id         story_id of item
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
