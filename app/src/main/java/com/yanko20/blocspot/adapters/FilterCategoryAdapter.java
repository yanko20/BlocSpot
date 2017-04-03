package com.yanko20.blocspot.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yanko20.blocspot.model.Category;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by yanko on 4/2/2017.
 */

public class FilterCategoryAdapter extends RealmRecyclerViewAdapter<Category, RecyclerView.ViewHolder> {

    public FilterCategoryAdapter(@Nullable OrderedRealmCollection<Category> data, boolean autoUpdate) {
        super(data, autoUpdate);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
