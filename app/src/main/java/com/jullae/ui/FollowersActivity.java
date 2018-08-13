package com.jullae.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.R;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.FollowersModel;
import com.jullae.data.db.model.FollowingModel;
import com.jullae.databinding.DialogFollowersBinding;
import com.jullae.ui.adapters.LikeAdapter;
import com.jullae.utils.Constants;
import com.jullae.utils.NetworkUtils;
import com.jullae.utils.ToastUtils;
import com.jullae.utils.customview.PagingRecyclerView;

public class FollowersActivity extends AppCompatActivity {

    private String userId;
    private boolean isFollowerPage;
    private String TAG = FollowersActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DialogFollowersBinding binding = DataBindingUtil.setContentView(this, R.layout.dialog_followers);


        isFollowerPage = getIntent().getBooleanExtra("follower", false);
        userId = getIntent().getStringExtra("userId");

        if (isFollowerPage)
            binding.title.setText("Followers");
        else
            binding.title.setText("Following");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final LikeAdapter likeAdapter;
        if (isFollowerPage)
            likeAdapter = new LikeAdapter(this, Constants.FOLLOWERS_LIST);
        else likeAdapter = new LikeAdapter(this, Constants.FOLLOWING_LIST);

        binding.recyclerView.setAdapter(likeAdapter);
        binding.recyclerView.addReachListBottomListener(new PagingRecyclerView.ReachListBottomListener() {
            private boolean isReqRunning;
            private int count = 2;

            @Override
            public void onReachListBottom() {

                if (!isReqRunning) {
                    isReqRunning = true;
                    binding.progressBar.setVisibility(View.VISIBLE);
                    if(isFollowerPage)
                    AppDataManager.getInstance().getmApiHelper().getFollowersList(userId, count, isFollowerPage).getAsObject(FollowersModel.class, new ParsedRequestListener<FollowersModel>() {
                        @Override
                        public void onResponse(FollowersModel followersModel) {
                            NetworkUtils.parseResponse(TAG, followersModel);
                            isReqRunning = false;
                            count++;
                            {
                                binding.progressBar.setVisibility(View.INVISIBLE);
                                likeAdapter.addMore(followersModel.getLikes());
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            NetworkUtils.parseError(TAG, anError);
                            isReqRunning = false;
                            {
                                binding.progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                    else AppDataManager.getInstance().getmApiHelper().getFollowersList(userId, count, isFollowerPage).getAsObject(FollowingModel.class, new ParsedRequestListener<FollowingModel>() {
                        @Override
                        public void onResponse(FollowingModel followersModel) {
                            NetworkUtils.parseResponse(TAG, followersModel);
                            isReqRunning = false;
                            count++;
                            {
                                binding.progressBar.setVisibility(View.INVISIBLE);
                                likeAdapter.addMore(followersModel.getLikes());
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            NetworkUtils.parseError(TAG, anError);
                            isReqRunning = false;
                            {
                                binding.progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });


        binding.getRoot().findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.getRoot().findViewById(R.id.close1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(isFollowerPage)
        AppDataManager.getInstance().getmApiHelper().getFollowersList(userId, 1, isFollowerPage).getAsObject(FollowersModel.class, new ParsedRequestListener<FollowersModel>() {

            @Override
            public void onResponse(FollowersModel followersModel) {
                NetworkUtils.parseResponse(TAG, followersModel);
                likeAdapter.add(followersModel.getLikes());
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                ToastUtils.showNoInternetToast(FollowersActivity.this);

            }
        });
        else AppDataManager.getInstance().getmApiHelper().getFollowersList(userId, 1, isFollowerPage).getAsObject(FollowingModel.class, new ParsedRequestListener<FollowingModel>() {

            @Override
            public void onResponse(FollowingModel followersModel) {
                NetworkUtils.parseResponse(TAG, followersModel);
                likeAdapter.add(followersModel.getLikes());
            }

            @Override
            public void onError(ANError anError) {
                NetworkUtils.parseError(TAG, anError);
                ToastUtils.showNoInternetToast(FollowersActivity.this);

            }
        });

    }

}
