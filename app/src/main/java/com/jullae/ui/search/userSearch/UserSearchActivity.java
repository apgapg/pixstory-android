package com.jullae.ui.search.userSearch;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.jullae.R;
import com.jullae.ui.storydetails.StoryDetailFragment;

public class UserSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);


    }

    private void showStoryDetailFragment(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StoryDetailFragment storyDetailFragment = new StoryDetailFragment();
        storyDetailFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, storyDetailFragment).commit();


    }
}
