package com.jullae.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.jullae.R;
import com.jullae.data.AppDataManager;
import com.jullae.data.db.model.SearchPeopleMainModel;
import com.jullae.utils.GlideUtils;
import com.jullae.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchPersonAdapter extends ArrayAdapter<SearchPeopleMainModel.SearchPeopleModel> {
    private static final String TAG = SearchPersonAdapter.class.getName();
    private final List<SearchPeopleMainModel.SearchPeopleModel> searchList;
    private final Context context;

    public SearchPersonAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_list_item_1);
        this.context = context;

        searchList = new ArrayList<>();
    }



    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_people_search, parent, false);
        }

        TextView user_name = view.findViewById(R.id.text_name);
        TextView user_penname = view.findViewById(R.id.text_penname);
        ImageView user_avatar = view.findViewById(R.id.image_avatar);
        user_name.setText(getItem(position).getUser_name());
        user_penname.setText(getItem(position).getUser_penname());
        GlideUtils.loadImagefromUrl(context, getItem(position).getUser_avatar(), user_avatar);

        return view;
    }

    @Override
    public int getCount() {
        return searchList.size();
    }

    @Nullable
    @Override
    public SearchPeopleMainModel.SearchPeopleModel getItem(int position) {
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
                    AppDataManager.getInstance().getmApiHelper().getSearchPeopleList(constraint.toString()).getAsObject(SearchPeopleMainModel.class, new ParsedRequestListener<SearchPeopleMainModel>() {


                        @Override
                        public void onResponse(SearchPeopleMainModel searchPeopleMainModel) {
                            searchList.clear();
                            searchList.addAll(searchPeopleMainModel.getSearchPeopleModelList());
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

    public SearchPeopleMainModel.SearchPeopleModel getItemAtPosition(int position) {
        return searchList.get(position);
    }
}
