package com.jullae.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.R;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.SuggestionMainModel;
import com.jullae.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends ArrayAdapter<SuggestionMainModel.SuggestionModel> {
    private static final String TAG = SearchAdapter.class.getName();
    private final List<SuggestionMainModel.SuggestionModel> searchList;

    public SearchAdapter(@NonNull Context context) {
        super(context, R.layout.item_search_explore_adapter);
        searchList = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return searchList.size();
    }

    @Nullable
    @Override
    public SuggestionMainModel.SuggestionModel getItem(int position) {
        return searchList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_search_explore_adapter, parent, false);
        }

        TextView title = view.findViewById(R.id.title);
        TextView number = view.findViewById(R.id.number);
        if (getItem(position).getTag_name() != null) {
            title.setText(getItem(position).getTag_name());
            number.setText(getItem(position).getCount());
        } else {
            title.setText("No results");
            number.setText("");

        }
        return view;
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

                            if (suggestionMainModel.getSuggestionModelList().size() > 0)
                                searchList.addAll(suggestionMainModel.getSuggestionModelList());
                            else {
                                SuggestionMainModel.SuggestionModel suggestionModel = new SuggestionMainModel.SuggestionModel();
                                searchList.add(suggestionModel);
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
