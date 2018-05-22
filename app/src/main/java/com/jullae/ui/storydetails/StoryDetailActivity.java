package com.jullae.ui.storydetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_story_detail);


        Intent i = getIntent();

        Bundle bundle = new Bundle();
        if (getIntent().hasExtra("storymodel"))
            bundle.putString("storymodel", i.getStringExtra("storymodel"));
        else if (getIntent().hasExtra("story_id"))
            bundle.putString("story_id", i.getStringExtra("story_id"));
        else throw new NullPointerException("story params cant be null!");

        showStoryDetailFragment(bundle);

    }


    private void showStoryDetailFragment(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StoryDetailFragment storyDetailFragment = new StoryDetailFragment();
        storyDetailFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, storyDetailFragment).commit();


    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof StoryDetailFragment && fragment.isVisible()) {
            if (!((StoryDetailFragment) fragment).closeBottomSheet()) {
                super.onBackPressed();
            }
        } else
            super.onBackPressed();
    }


    public void showSearchResults(String story_title) {
        Intent i = new Intent();
        i.putExtra("searchtag", story_title);
        setResult(RESULT_OK, i);
        finish();
    }
}
