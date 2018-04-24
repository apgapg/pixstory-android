package com.jullae.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.jullae.R;
import com.jullae.app.AppController;
import com.jullae.model.FreshFeedModel;
import com.jullae.ui.base.BaseFragment;
import com.jullae.ui.search.SearchFeedAdapter;
import com.jullae.ui.search.SearchFeedContract;
import com.jullae.ui.search.SearchFeedPresentor;

import java.util.List;

public class SearchFragment extends BaseFragment implements SearchFeedContract.View {
    private View view;
    private AutoCompleteTextView autoCompleteTextView;
    private SearchFeedPresentor searchFeedPresentor;
    private SearchFeedAdapter searchFeedAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        if (view != null) {
            if (view.getParent() != null)
                ((ViewGroup) view.getParent()).removeView(view);
            return view;
        }
        view = inflater.inflate(R.layout.fragment_search, container, false);


        autoCompleteTextView = view.findViewById(R.id.search_tag_field);


        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        searchFeedAdapter = new SearchFeedAdapter(getmContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getmContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //ItemOffTBsetDecoration itemDecoration = new ItemOffTBsetDecoration(mContext, R.dimen.item_offset);
        ///recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(searchFeedAdapter);

        searchFeedPresentor = new SearchFeedPresentor(((AppController) getmContext().getApplication()).getmAppDataManager());

        searchFeedPresentor.loadFeeds();
        return view;
    }

    @Override
    public void onFetchFeeds(List<FreshFeedModel> list) {
        // searchFeedAdapter.add(list);
    }

    @Override
    public void onFetchFeedsFail() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchFeedPresentor.setView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        searchFeedPresentor.removeView();
    }

}
