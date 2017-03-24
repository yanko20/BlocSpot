package com.yanko20.blocspot.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yanko20.blocspot.R;
import com.yanko20.blocspot.adapters.PoiItemAdapter;
import com.yanko20.blocspot.database.Database;
import com.yanko20.blocspot.model.Category;
import com.yanko20.blocspot.model.PointOfInterest;
import java.util.UUID;

import io.realm.RealmResults;

/**
 * Created by yanko on 1/13/2017.
 */

public class PoiListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.poi_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RealmResults<PointOfInterest> dataSet = Database.getAllPois();
        adapter = new PoiItemAdapter(dataSet);
        recyclerView.setAdapter(adapter);
        return v;
    }


}
