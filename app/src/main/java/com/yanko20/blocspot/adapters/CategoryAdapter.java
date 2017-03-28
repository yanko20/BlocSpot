package com.yanko20.blocspot.adapters;

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
import com.yanko20.blocspot.model.Category;

import io.realm.RealmResults;

/**
 * Created by yanko on 3/24/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private RealmResults<Category> dataSet;
    private static final String logTag = "CategoryAdapter.class";

    public CategoryAdapter(RealmResults<Category> dataSet) {
        this.dataSet = dataSet;
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
        holder.itemView.setBackgroundColor(categories[position].getColor());
        holder.textView.setText(categories[position].getName());
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
                    Toast.makeText(buttonView.getContext(),
                            "isChecked: " + isChecked, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
