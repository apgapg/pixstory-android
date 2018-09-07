package com.jullae.ui.pictureDetail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.data.db.model.StoryModel;
import com.jullae.databinding.ContentPictureDetailBinding;
import com.jullae.ui.custom.ItemOffLRsetDecoration;
import com.jullae.ui.home.HomeActivity;
import com.jullae.ui.home.homeFeed.HomeFeedModel;
import com.jullae.ui.home.homeFeed.StoryAdapter;
import com.jullae.ui.home.homeFeed.freshfeed.HomeFeedAdapter;
import com.jullae.utils.AppUtils;
import com.jullae.utils.Constants;
import com.jullae.utils.DialogUtils;

import java.util.List;

public class PictureDetailActivity extends AppCompatActivity implements PictureDetailView {

    private PictureDetailPresentor mPresentor;
    private RecyclerView recycler_view;
    private PictureAllStoryAdapter pictureAllStoryAdapter;
    private ContentPictureDetailBinding binding;
    private StoryAdapter storyAdaper;
    private HomeFeedModel.Feed model;
    private String pictureId;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int refreshMode = intent.getIntExtra(Constants.REFRESH_MODE, -1);
            Log.d("receiver", "Got message: " + refreshMode);
            switch (refreshMode) {
                case Constants.REFRESH_HOME_FEEDS:
                    mPresentor.loadPictureDetails(pictureId);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.content_picture_detail);
        final ContentPictureDetailBinding binding = DataBindingUtil.setContentView(
                this, R.layout.content_picture_detail);
        this.binding = binding;
        mPresentor = new PictureDetailPresentor();
        mPresentor.attachView(this);

        setUpRecyclerView();
        pictureId = getIntent().getStringExtra("picture_id");

        mPresentor.loadPictureDetails(pictureId);


        binding.getRoot().findViewById(R.id.close1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

        binding.buttonLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                changeLike();
                mPresentor.setLike(model.getPicture_id(), new HomeFeedAdapter.ReqListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(PictureDetailActivity.this.getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
                        changeLike();
                    }


                }, !model.getIs_liked());


            }
        });
        binding.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   showMenuOptions(getAdapterPosition());
                DialogUtils.showPictureMoreOptions(PictureDetailActivity.this, mPresentor, model);
            }
        });
        setupRefreshBroadcastListener();


    }


    private void setupRefreshBroadcastListener() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter(Constants.REFRESH_INTENT_FILTER));


    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);

        super.onDestroy();
    }

    private void changeLike() {
        if (model.getIs_liked()) {
            model.setIs_liked(false);
            model.setDecrementLikeCount();
        } else {
            model.setIs_liked(true);
            model.setIncrementLikeCount();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(PictureDetailActivity.this, HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
        finish();
    }

    private void setUpRecyclerView() {
        recycler_view = findViewById(R.id.recycler_view_story);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.addItemDecoration(new ItemOffLRsetDecoration(this, R.dimen.item_offset_4dp));

        storyAdaper = new StoryAdapter(this, null);
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
        storyAdaper.setPictureId(homeFeedModel.getPicture_id());
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
