package com.jullae.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.jullae.R;
import com.jullae.data.db.model.FeedModel;
import com.jullae.ui.adapters.SearchAdapter;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.search.SearchFeedAdapter;
import com.jullae.ui.search.SearchFeedContract;
import com.jullae.ui.search.SearchFeedPresentor;

import java.util.List;

public class SearchFragment extends BaseFragment implements SearchFeedContract.View {
    private View view;
    private SearchFeedPresentor searchFeedPresentor;
    private SearchFeedAdapter searchFeedAdapter;
    private String searchTag;
    private TextView searchTextView;
    private AutoCompleteTextView autoCompleteTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.fragment_search, container, false);
        searchTag = getArguments().getString("searchTag");
        searchFeedPresentor = new SearchFeedPresentor();


        autoCompleteTextView = view.findViewById(R.id.search_tag_field);
        autoCompleteTextView.setText(searchTag);
        autoCompleteTextView.setSelection(searchTag.length());
        SearchAdapter searchAdapter = new SearchAdapter(getmContext());
        autoCompleteTextView.setAdapter(searchAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String searchTag = ((TextView) view).getText().toString();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // autoCompleteTextView.setText("");


                        // ((HomeActivity) getmContext()).showSearchActivity(searchTag);
                        onSearchTextChanged(searchTag);

                    }
                }, 500);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        searchFeedAdapter = new SearchFeedAdapter(getmContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //ItemOffTBsetDecoration itemDecoration = new ItemOffTBsetDecoration(mContext, R.dimen.item_offset);
        ///recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(searchFeedAdapter);


        return view;
    }

    private void onSearchTextChanged(String searchTag) {
        searchFeedPresentor.loadFeeds(searchTag);
    }

    @Override
    public void onFetchFeedsSuccess(List<FeedModel> list) {
        // searchFeedAdapter.add(list);
        searchFeedAdapter.add(list);
    }

    @Override
    public void onFetchFeedsFail() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchFeedPresentor.attachView(this);
        searchFeedPresentor.loadFeeds(searchTag);

    }

    @Override
    public void onDestroyView() {
        searchFeedPresentor.detachView();

        super.onDestroyView();
    }

}
