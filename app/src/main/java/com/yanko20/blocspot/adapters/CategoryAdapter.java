package com.yanko20.blocspot.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanko20.blocspot.R;
import com.yanko20.blocspot.model.Category;

import io.realm.RealmResults;

/**
 * Created by yanko on 3/24/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{

    private RealmResults<Category> dataSet;

    public CategoryAdapter(RealmResults<Category> dataSet){
        this.dataSet = dataSet;
    }

    @Override
    public CategoryHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        Category[] categories = dataSet.toArray(new Category[dataSet.size()]);
        holder.itemView.setBackgroundColor(categories[position].getColor());
        holder.textView.setText(categories[position].getName());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private View itemView;

        public CategoryHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textView = (TextView) itemView.findViewById(R.id.category_list_item_text_view);
        }
    }

}
