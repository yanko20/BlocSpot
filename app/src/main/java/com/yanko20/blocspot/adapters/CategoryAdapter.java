package com.yanko20.blocspot.adapters;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yanko20.blocspot.R;
import com.yanko20.blocspot.database.DataHelper;
import com.yanko20.blocspot.model.Category;
import com.yanko20.blocspot.model.PointOfInterest;

import java.util.Iterator;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by yanko on 3/24/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private RealmResults<Category> dataSet;
    private Realm realm;
    private PointOfInterest poi;
    private static final String logTag = "CategoryAdapter.class";

    public CategoryAdapter(String poiId, Realm realm) {
        this.realm = realm;
        this.dataSet = DataHelper.getAllCategories(this.realm);
        this.poi = DataHelper.getPoi(realm, poiId);


    }

    @Override
    public CategoryHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        Log.d(logTag, "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        Log.d(logTag, "onBindViewHolder");
        Category[] categories = dataSet.toArray(new Category[dataSet.size()]);
        Category category = categories[position];
        holder.itemView.setBackgroundColor(category.getColor());
        holder.textView.setText(category.getName());
        if (DataHelper.isCategoryAssignedToPoi(realm, poi, category.getName())) {
            holder.checkBox.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private View itemView;
        private CheckBox checkBox;


        public CategoryHolder(View itemView) {
            super(itemView);
            Log.d(logTag, "CategoryHolder");
            this.itemView = itemView;
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
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // todo refactor two functional modes: assign category and filter by category
                    Toast.makeText(buttonView.getContext(),
                            textView.getText().toString() + " isChecked: " + isChecked, Toast.LENGTH_SHORT).show();
                    String name = textView.getText().toString();
                    if (isChecked) {
                        if (!DataHelper.isCategoryAssignedToPoi(realm, poi, name)) {
                            DataHelper.addCategoryToPoi(realm, poi, name);
                        }
                    } else {
                        DataHelper.removeCategoryFromPoi(realm, poi, name);
                    }
                }
            });
        }
    }

}
