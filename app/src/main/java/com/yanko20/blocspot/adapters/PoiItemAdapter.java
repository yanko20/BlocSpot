package com.yanko20.blocspot.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yanko20.blocspot.R;

import java.util.ArrayList;

/**
 * Created by yanko on 1/13/2017.
 */

public class PoiItemAdapter extends RecyclerView.Adapter<PoiItemAdapter.PoiItemViewHolder>{

    private ArrayList<String> dataSet;

    public PoiItemAdapter(ArrayList<String> dataSet){
        this.dataSet = dataSet;
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
        holder.textView.setText(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class PoiItemViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public PoiItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.poi_item_text);
        }

    }
}