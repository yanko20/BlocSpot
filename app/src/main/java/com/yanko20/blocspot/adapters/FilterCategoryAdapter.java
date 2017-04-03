package com.yanko20.blocspot.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yanko20.blocspot.R;
import com.yanko20.blocspot.model.Category;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by yanko on 4/3/2017.
 */

public class FilterCategoryAdapter extends
        RealmRecyclerViewAdapter<Category, FilterCategoryAdapter.FilterCategoryHolder> {

    private OrderedRealmCollection<Category> categories;

    public FilterCategoryAdapter(@Nullable OrderedRealmCollection<Category> categories, boolean autoUpdate) {
        super(categories, autoUpdate);
        this.categories = categories;
    }

    @Override
    public FilterCategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);
        return new FilterCategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(final FilterCategoryHolder holder, int position) {
        Category category = categories.get(position);
        holder.itemView.setBackgroundColor(category.getColor());
        holder.textView.setText(category.getName());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(holder.checkBox.getContext(), "Filter..", Toast.LENGTH_SHORT).show();
                if (isChecked) {

                } else {

                }

            }
        });
    }

    public class FilterCategoryHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        private CheckBox checkBox;


        public FilterCategoryHolder(final View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.category_list_item_text_view);
            checkBox = (CheckBox) itemView.findViewById(R.id.category_list_item_checkbox);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        checkBox.setChecked(false);
                    } else {
                        checkBox.setChecked(true);
                    }
                }
            });

        }
    }
}
