package com.jullae.ui.pictureDetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.jullae.R;
import com.jullae.data.db.model.StoryModel;
import com.jullae.databinding.ContentPictureDetailBinding;
import com.jullae.ui.custom.ItemOffLRsetDecoration;
import com.jullae.ui.home.homeFeed.HomeFeedModel;
import com.jullae.ui.home.homeFeed.StoryAdapter;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;

import java.util.List;

public class PictureDetailActivity extends AppCompatActivity implements PictureDetailView {

    private PictureDetailPresentor mPresentor;
    private RecyclerView recycler_view;
    private PictureAllStoryAdapter pictureAllStoryAdapter;
    private ContentPictureDetailBinding binding;
    private StoryAdapter storyAdaper;
    private HomeFeedModel.Feed model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.content_picture_detail);
        final ContentPictureDetailBinding binding = DataBindingUtil.setContentView(
                this, R.layout.content_picture_detail);
        this.binding = binding;
        mPresentor = new PictureDetailPresentor();
        mPresentor.attachView(this);

        Gson gson = new Gson();
        setUpRecyclerView();


        mPresentor.loadPictureDetails(getIntent().getStringExtra("picture_id"));


        binding.getRoot().findViewById(R.id.close1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.getRoot().findViewById(R.id.text_penname).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showVisitorProfile(PictureDetailActivity.this, model.getPhotographer_penname());
            }
        });
        binding.getRoot().findViewById(R.id.image_avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showVisitorProfile(PictureDetailActivity.this, model.getPhotographer_penname());
            }
        });

        binding.getRoot().findViewById(R.id.like_count).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getLike_count() != 0)

                    AppUtils.showLikesDialog(PictureDetailActivity.this, model.getPicture_id(), Constants.LIKE_TYPE_PICTURE);
            }
        });

        binding.getRoot().findViewById(R.id.text_write_story).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showWriteStoryDialog(PictureDetailActivity.this, model.getPicture_id());
            }
        });


    }

    private void setUpRecyclerView() {
        recycler_view = findViewById(R.id.recycler_view_story);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.addItemDecoration(new ItemOffLRsetDecoration(this, R.dimen.item_offset_4dp));

        storyAdaper = new StoryAdapter(this);
        recycler_view.setAdapter(storyAdaper);

    }

    @Override
    public void onStoriesFetchSuccess(List<StoryModel> storyModelList) {
        pictureAllStoryAdapter.add(storyModelList);
    }

    @Override
    public void onStoriesFetchFail() {

    }

    @Override
    public void onFetchFeedSuccess(HomeFeedModel.Feed homeFeedModel) {
        this.model = homeFeedModel;
        binding.setFeed(homeFeedModel);
        if (homeFeedModel.getStories().size() != 0) {
            storyAdaper.add(homeFeedModel.getStories());
        } else
            storyAdaper.addEmptyMessage(homeFeedModel.getPicture_id());

        //  mPresentor.loadStories(homeFeedModel.getPicture_id());

    }

    @Override
    public void onFetchFeedFail() {

    }
}
