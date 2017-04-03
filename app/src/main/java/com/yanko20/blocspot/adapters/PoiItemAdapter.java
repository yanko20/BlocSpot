package com.yanko20.blocspot.adapters;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.yanko20.blocspot.R;
import com.yanko20.blocspot.model.PointOfInterest;
import com.yanko20.blocspot.ui.AddCategoryDialogFragment;
import com.yanko20.blocspot.ui.CategoryDialogFragment;

import io.realm.RealmResults;

/**
 * Created by yanko on 1/13/2017.
 */

public class PoiItemAdapter extends RecyclerView.Adapter<PoiItemAdapter.PoiItemViewHolder>{

    private RealmResults<PointOfInterest> dataSet;
    private FragmentManager fragmentManager;

    public PoiItemAdapter(RealmResults<PointOfInterest> dataSet, FragmentManager fragmentManager){
        this.dataSet = dataSet;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public PoiItemViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poi_list_item, parent, false);
        PoiItemViewHolder poiItemViewHolder = new PoiItemViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(), "Toast!", Toast.LENGTH_SHORT).show();
            }
        });

        return poiItemViewHolder;
    }

    @Override
    public void onBindViewHolder(PoiItemViewHolder holder, int position) {
        PointOfInterest poi = dataSet.get(position);
        holder.setId(poi.getId());
        holder.title.setText(poi.getTitle());
        holder.description.setText(poi.getDescription());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class PoiItemViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView description;
        private String id;
        public static final String POI_ID_KEY = "poiId";

        public PoiItemViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.poi_item_title);
            description = (TextView) itemView.findViewById(R.id.poi_item_description);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(POI_ID_KEY, id);
                    bundle.putString(CategoryDialogFragment.MODE_KEY, CategoryDialogFragment.FILTER_MODE);
                    CategoryDialogFragment fragment = new CategoryDialogFragment();
                    fragment.setArguments(bundle);
                    fragment.show(fragmentManager, CategoryDialogFragment.DIALOG_TAG);
                    return true;
                }
            });
        }

        public void setId(String poiId){
            this.id = poiId;
        }

    }
}