package com.jullae;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.jullae.ui.fragments.SearchFragment;

public class SearchActivity extends AppCompatActivity {

    private String mSearchTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (getIntent() != null) {
            mSearchTag = getIntent().getStringExtra("searchtag");
            if (mSearchTag == null)
                throw new NullPointerException("search Tag cannot be null!");

        }
        showSearchFragment();
    }

    public void showSearchFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString("searchTag", mSearchTag);
        searchFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, searchFragment).commit();

    }
}
