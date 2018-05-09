package com.jullae.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.SuggestionMainModel;
import com.jullae.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends ArrayAdapter<String> {
    private static final String TAG = SearchAdapter.class.getName();
    private final List<String> searchList;

    public SearchAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_dropdown_item_1line);
        searchList = new ArrayList<>();
    }



    @Override
    public int getCount() {
        return searchList.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return searchList.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if (constraint != null) {
                    AppDataManager.getInstance().getmApiHelper().getSearchList(constraint.toString()).getAsObject(SuggestionMainModel.class, new ParsedRequestListener<SuggestionMainModel>() {


                        @Override
                        public void onResponse(SuggestionMainModel suggestionMainModel) {
                            searchList.clear();
                            for (int i = 0; i < suggestionMainModel.getSuggestionModelList().size(); i++) {
                                searchList.add(suggestionMainModel.getSuggestionModelList().get(i).getTag_name());
                            }
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onError(ANError anError) {
                            NetworkUtils.parseError(TAG, anError);
                        }
                    });
                }
                filterResults.values = searchList;
                filterResults.count = searchList.size();

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }

            }
        };
        return filter;
    }
}
