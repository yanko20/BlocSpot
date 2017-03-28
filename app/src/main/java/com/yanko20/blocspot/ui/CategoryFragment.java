package com.yanko20.blocspot.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanko20.blocspot.R;
import com.yanko20.blocspot.adapters.CategoryAdapter;
import com.yanko20.blocspot.database.DataHelper;

import io.realm.Realm;

/**
 * Created by ykomizor on 3/22/2017.
 */

public class CategoryFragment extends DialogFragment{

    private Realm realm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_list, container);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.category_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        realm = Realm.getDefaultInstance();
        CategoryAdapter adapter = new CategoryAdapter(DataHelper.getAllCategories(realm));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }
}
