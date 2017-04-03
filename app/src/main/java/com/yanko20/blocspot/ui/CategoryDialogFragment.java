package com.yanko20.blocspot.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.yanko20.blocspot.R;
import com.yanko20.blocspot.adapters.CategoryAdapter;
import com.yanko20.blocspot.adapters.FilterCategoryAdapter;
import com.yanko20.blocspot.adapters.PoiItemAdapter;
import com.yanko20.blocspot.database.DataHelper;
import com.yanko20.blocspot.model.Category;
import com.yanko20.blocspot.model.PointOfInterest;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by ykomizor on 3/22/2017.
 */

public class CategoryDialogFragment extends DialogFragment {

    private Realm realm;
    public static final String DIALOG_TAG = "AddCategoryDialogFragmentTag";
    public static final String logTag = "CategoryDialFrag.class";
    public static final String MODE_KEY = "modeKey";
    public static final String ASSIGN_MODE = "assignMode";
    public static final String FILTER_MODE = "filterMode";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_list, container);
        ImageButton addCategoryButton = (ImageButton) view.findViewById(R.id.add_category_button);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.category_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        realm = Realm.getDefaultInstance();
        Bundle bundle = this.getArguments();

        String mode = bundle.getString(MODE_KEY);
        if(mode == ASSIGN_MODE){
            String poiId = bundle.getString(PoiItemAdapter.PoiItemViewHolder.POI_ID_KEY);
            final CategoryAdapter adapter = new CategoryAdapter(poiId, realm);
            recyclerView.setAdapter(adapter);
            OrderedRealmCollectionChangeListener categoriesChangeListener = new OrderedRealmCollectionChangeListener<RealmResults<Category>>() {
                @Override
                public void onChange(RealmResults<Category> collection, OrderedCollectionChangeSet changeSet) {
                    adapter.notifyDataSetChanged();
                }
            };
            DataHelper.getAllCategories(realm).addChangeListener(categoriesChangeListener);
        } else if(mode == FILTER_MODE){
            FilterCategoryAdapter filterCategoryAdapter =
                    new FilterCategoryAdapter(DataHelper.getAllCategories(realm), true);
            recyclerView.setAdapter(filterCategoryAdapter);
        }

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddCategoryDialogFragment()
                        .show(getFragmentManager(), AddCategoryDialogFragment.DIALOG_TAG);
            }
        });

        return view;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.removeAllChangeListeners();
        realm.close();
    }
}
