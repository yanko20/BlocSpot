package com.yanko20.blocspot.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yanko20.blocspot.R;
import com.yanko20.blocspot.adapters.PoiItemAdapter;
import com.yanko20.blocspot.database.DataHelper;
import com.yanko20.blocspot.model.PointOfInterest;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by yanko on 1/13/2017.
 */

public class PoiListFragment extends Fragment implements FilterCategoryDialogDismissListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private static final String logTag = "PoiListFragment.class";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View fragmentListView = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) fragmentListView.findViewById(R.id.poi_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RealmResults<PointOfInterest> dataSet = DataHelper.getAllPois();
        adapter = new PoiItemAdapter(dataSet, getActivity().getFragmentManager());
        recyclerView.setAdapter(adapter);
        CategoryDialogFragment.setCategoryDialogDismissListener(this);
        return fragmentListView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(logTag, "onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CategoryDialogFragment.removeCategoryDialogDismissListener(this);
    }

    @Override
    public void onDismissFilterCategoryDialog() {
            recyclerView.setAdapter(
                    new PoiItemAdapter(DataHelper.getFilteredPois(),
                            getActivity().getFragmentManager())
            );
            recyclerView.invalidate();
    }
}
