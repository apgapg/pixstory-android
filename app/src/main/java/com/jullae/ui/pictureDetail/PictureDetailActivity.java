package com.jullae.ui.pictureDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jullae.R;
import com.jullae.data.db.model.HomeFeedSingleModel;
import com.jullae.data.db.model.PictureModel;
import com.jullae.data.db.model.StoryModel;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;
import com.jullae.utils.GlideUtils;

import java.util.List;

public class PictureDetailActivity extends AppCompatActivity implements PictureDetailView {

    private ImageView image, user_image;
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
        user_image = findViewById(R.id.user_photo);
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
            GlideUtils.loadImagefromUrl(this, pictureModel.getPicture_url(), image);
            GlideUtils.loadImagefromUrl(this, pictureModel.getPhotographer_avatar(), user_image);
            user_name.setText(pictureModel.getPhotographer_name());
            pic_title.setText(pictureModel.getPicture_title());
            pic_like_count.setText(pictureModel.getLike_count() + " likes");
            pic_story_count.setText(pictureModel.getStory_count() + " stories");
            mPresentor.loadStories(pictureModel.getPicture_id());

        } else {
            mPresentor.loadPictureDetails(getIntent().getStringExtra("picture_id"));
        }

        mPresentor.loadPictureDetails(getIntent().getStringExtra("picture_id"));

        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showVisitorProfile(PictureDetailActivity.this, pictureModel.getPhotographer_penname());
            }
        });
        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showVisitorProfile(PictureDetailActivity.this, pictureModel.getPhotographer_penname());
            }
        });

        pic_like_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pictureModel.getLike_count() != 0)

                    AppUtils.showLikesDialog(PictureDetailActivity.this, pictureModel.getPicture_id(), Constants.LIKE_TYPE_PICTURE);
            }
        });

        findViewById(R.id.write_story).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showWriteStoryDialog(PictureDetailActivity.this, pictureModel.getPicture_id());
            }
        });


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
    public void onFetchFeedSuccess(HomeFeedSingleModel.Feed homeFeedModel) {
        GlideUtils.loadImagefromUrl(this, homeFeedModel.getPicture_url(), image);
        GlideUtils.loadImagefromUrl(this, homeFeedModel.getPhotographer_avatar(), user_image);
        user_name.setText(homeFeedModel.getPhotographer_name());
        pic_title.setText(homeFeedModel.getPicture_title());
        pic_like_count.setText(homeFeedModel.getLike_count() + " likes");
        pic_story_count.setText(homeFeedModel.getStory_count() + " stories");
        mPresentor.loadStories(homeFeedModel.getPicture_id());

    }

    @Override
    public void onFetchFeedFail() {

    }
}
