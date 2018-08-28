package com.jullae.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jullae.R;
import com.jullae.ui.adapters.SearchAdapter;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.home.HomeActivity;

/**
 * Created by Rahul Abrol on 12/26/17.
 * <p>
 * Shape fragment;
 */

public class ExploreFragment extends BaseFragment implements View.OnClickListener {


    private View view;
    private AutoCompleteTextView autoCompleteTextView;

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
        SearchAdapter searchAdapter = new SearchAdapter(getmContext());
        autoCompleteTextView.setAdapter(searchAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                final String searchTag = ((TextView) ((LinearLayout) view).getChildAt(0)).getText().toString();
                if (searchTag.contains("No results")) {
                    autoCompleteTextView.setText("");

                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            autoCompleteTextView.setText("");


                            ((HomeActivity) getmContext()).showSearchActivity(searchTag);

                        }
                    }, 300);
                }
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
                // ((HomeActivity) getmContext()).showFreshFeedFragment(1);
                Toast.makeText(getmContext().getApplicationContext(), "Coming soon!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.fav_feeds:
                //((HomeActivity) getmContext()).showFreshFeedFragment(2);
                Toast.makeText(getmContext().getApplicationContext(), "Coming soon!", Toast.LENGTH_SHORT).show();

                break;
            case R.id.popular_feeds:
                ((HomeActivity) getmContext()).showFreshFeedFragment(3);

                break;
        }
    }


}
