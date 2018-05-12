package com.jullae.ui.pictureDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jullae.R;
import com.jullae.data.db.model.PictureModel;
import com.jullae.data.db.model.StoryModel;
import com.jullae.ui.home.homeFeed.HomeFeedModel;

import java.util.List;

public class PictureDetailActivity extends AppCompatActivity implements PictureDetailView {

    private ImageView image, user_photo;
    private TextView user_name, pic_title, pic_like_count, pic_story_count;
    private PictureModel pictureModel;
    private PictureDetailPresentor mPresentor;
    private RecyclerView recycler_view;
    private PictureAllStoryAdapter pictureAllStoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_picture_detail);

        image = findViewById(R.id.image);
        user_photo = findViewById(R.id.user_photo);
        user_name = findViewById(R.id.text_name);
        pic_title = findViewById(R.id.pic_title);
        pic_like_count = findViewById(R.id.pic_like_count);
        pic_story_count = findViewById(R.id.pic_comment_count);


        mPresentor = new PictureDetailPresentor();
        mPresentor.attachView(this);

        Gson gson = new Gson();
        pictureModel = gson.fromJson(getIntent().getStringExtra("pictureModel"), PictureModel.class);

        setUpRecyclerView();

        if (pictureModel != null) {
            Glide.with(this).load(pictureModel.getPicture_url_small()).into(image);
            Glide.with(this).load(pictureModel.getPhotographer_avatar()).into(user_photo);
            user_name.setText(pictureModel.getPhotographer_name());
            pic_title.setText(pictureModel.getPicture_title());
            pic_like_count.setText(pictureModel.getLike_count() + " likes");
            pic_story_count.setText(pictureModel.getStory_count() + " stories");
            mPresentor.loadStories(pictureModel.getPicture_id());

        } else {
            mPresentor.loadPictureDetails(getIntent().getStringExtra("picture_id"));
        }

    }

    private void setUpRecyclerView() {
        recycler_view = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(linearLayoutManager);

        pictureAllStoryAdapter = new PictureAllStoryAdapter(this);
        recycler_view.setAdapter(pictureAllStoryAdapter);

    }

    @Override
    public void onStoriesFetchSuccess(List<StoryModel> storyModelList) {
        pictureAllStoryAdapter.add(storyModelList);
    }

    @Override
    public void onStoriesFetchFail() {

    }

    @Override
    public void onFetchFeedSuccess(HomeFeedModel homeFeedModel) {
        Glide.with(this).load(homeFeedModel.getFeedList().get(0).getPicture_url()).into(image);
        Glide.with(this).load(homeFeedModel.getFeedList().get(0).getPhotographer_avatar()).into(user_photo);
        user_name.setText(homeFeedModel.getFeedList().get(0).getPhotographer_name());
        pic_title.setText(homeFeedModel.getFeedList().get(0).getPicture_title());
        pic_like_count.setText(homeFeedModel.getFeedList().get(0).getLike_count() + " likes");
        pic_story_count.setText(homeFeedModel.getFeedList().get(0).getStory_count() + " stories");
        mPresentor.loadStories(homeFeedModel.getFeedList().get(0).getPicture_id());

    }

    @Override
    public void onFetchFeedFail() {

    }
}
