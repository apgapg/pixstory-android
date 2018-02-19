package com.jullae.ui.storydetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.constant.AppConstant;
import com.jullae.model.CommentModel;
import com.jullae.retrofit.APIError;
import com.jullae.retrofit.ApiInterface;
import com.jullae.retrofit.CommonResponse;
import com.jullae.retrofit.ResponseResolver;
import com.jullae.retrofit.RestClient;
import com.jullae.ui.adapters.CommentAdapter;
import com.jullae.ui.base.BaseActivity;
import com.jullae.ui.fragments.DetailSheetFragment;
import com.jullae.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by Rahul Abrol on 12/20/17.
 * <p>
 * Class @{@link StoryDetailsActivity} used to show the
 * details of the comments and story that user wrote on the
 * picture.
 */

public class StoryDetailsActivity extends BaseActivity {

    private RelativeLayout rLayoutHideComment;
    private ApiInterface client;
    private int id;
    private BottomSheetDialogFragment bottomSheetDialogFragment;
    private LinearLayout linearLayoutComments;
    private TextView tvSeeAll;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_details);
        client = RestClient.getApiInterface();
        Bundle bundle = getIntent().getBundleExtra(AppConstant.BUNDLE);
        id = bundle.getInt(AppConstant.EXTRA_ID);

        //Find bottom Sheet ID
        View bottomSheet = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setPeekHeight(0);

        TextView tvYourTitle = findViewById(R.id.tvYourTitle);
        TextView tvClose = findViewById(R.id.tvClose);
        TextView tvLikeName = findViewById(R.id.tvLikeName);
        TextView tvLikeUserName = findViewById(R.id.tvLikeUserName);
        TextView tvLikeFollow = findViewById(R.id.tvLikeFollow);
        TextView tvStory = findViewById(R.id.tvStory);
        TextView tvLikeCount = findViewById(R.id.tvLikeCount);
        tvSeeAll = findViewById(R.id.tvSeeAll);
        EditText edComment = findViewById(R.id.edComment);
        rLayoutHideComment = findViewById(R.id.rLayoutHideComment);
        linearLayoutComments = findViewById(R.id.linearLayoutComments);
        ImageView ivEditStory = findViewById(R.id.ivEditStory);
        ImageView ivLike = findViewById(R.id.ivLike);
        ImageView ivLikeUserPic = findViewById(R.id.ivLikeUserPic);
        edComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    Toast.makeText(StoryDetailsActivity.this, "POST comment", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        ArrayList<CommentModel> storyList = new ArrayList<>();
        RecyclerView rvComments = findViewById(R.id.rvComments);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvComments.setLayoutManager(mLayoutManager);

        CommentAdapter adapter = new CommentAdapter(this, storyList);
        rvComments.setAdapter(adapter);


        //listener.
        tvClose.setOnClickListener(this);
        tvSeeAll.setOnClickListener(this);
        tvLikeFollow.setOnClickListener(this);
        ivEditStory.setOnClickListener(this);
        linearLayoutComments.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.ivEditStory:
                Bundle bundle = new Bundle();
                //Show the Bottom Sheet Fragment.
                bottomSheetDialogFragment = DetailSheetFragment.getInstance(bundle);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getClass().getSimpleName());
                break;
            case R.id.tvClose:
                finish();
                break;
            case R.id.tvSeeAll:
                linearLayoutComments.setVisibility(View.VISIBLE);
                tvSeeAll.setVisibility(View.GONE);
                break;
            case R.id.tvLikeFollow:
                Toast.makeText(this, "follow", Toast.LENGTH_SHORT).show();
                break;
            case R.id.linearLayoutComments:
                linearLayoutComments.setVisibility(View.GONE);
                tvSeeAll.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    /**
     * Follow user api hit.
     */
    private void followUser() {
        Call<CommonResponse> data = client.followUser(id);
        data.enqueue(new ResponseResolver<CommonResponse>(this, true, false) {
            @Override
            public void success(final CommonResponse likeModel) {
//                if (likeModel != null && likeModel.getLikes().size() > 0) {
//                    feedList.addAll(likeModel.getFeed());
//                }
            }

            @Override
            public void failure(final APIError error) {
                Utils.showSnackbar(StoryDetailsActivity.this, rLayoutHideComment, error.getMessage());
            }
        });
    }
}
