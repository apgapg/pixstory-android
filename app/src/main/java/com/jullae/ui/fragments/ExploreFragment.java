package com.jullae.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jullae.R;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.homefeed.HomeActivity;

/**
 * Created by Rahul Abrol on 12/26/17.
 * <p>
 * Shape fragment;
 */

public class ExploreFragment extends BaseFragment implements View.OnClickListener {


    private View view;
    private View autoCompleteTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.fragment_explore, container, false);


        autoCompleteTextView = view.findViewById(R.id.search_tag_field);
        autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getmContext()).showSearchFragment();
            }
        });
        view.findViewById(R.id.fresh_feeds).setOnClickListener(this);
        view.findViewById(R.id.weekly_feeds).setOnClickListener(this);
        view.findViewById(R.id.popular_feeds).setOnClickListener(this);
        view.findViewById(R.id.fav_feeds).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fresh_feeds:
                ((HomeActivity) getmContext()).showFreshFeedFragment(0);
                break;
            case R.id.weekly_feeds:
                ((HomeActivity) getmContext()).showFreshFeedFragment(1);

                break;
            case R.id.fav_feeds:
                ((HomeActivity) getmContext()).showFreshFeedFragment(2);

                break;
            case R.id.popular_feeds:
                ((HomeActivity) getmContext()).showFreshFeedFragment(3);

                break;
        }
    }
    /*private void updateCropEditText(List<String> strings) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, strings);
        ed_crop.setAdapter(adapter);
    }*/

}
