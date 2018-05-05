package com.jullae.ui.storydetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.jullae.R;
import com.jullae.ui.base.BaseActivity;

/**
 * Created by Rahul Abrol on 12/20/17.
 * <p>
 * Class @{@link StoryDetailActivity} used to show the
 * details of the comments and story that user wrote on the
 * picture.
 */

public class StoryDetailActivity extends BaseActivity {


    private String storyModel;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_story_detail);


        if (getIntent() != null) {
            Intent i = getIntent();
            storyModel = i.getStringExtra("object");
            showStoryDetailFragment();
        }


    }


    private void showStoryDetailFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StoryDetailFragment storyDetailFragment = new StoryDetailFragment();
        Bundle bundle = new Bundle();
        Log.d(TAG, "showStoryDetailFragment: " + storyModel);
        bundle.putString("storymodel", storyModel);
        storyDetailFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, storyDetailFragment).commit();


    }

    @Override
    public void onBackPressed() {
       /* if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        else*/
        super.onBackPressed();
    }


}
