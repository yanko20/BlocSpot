package com.yanko20.blocspot.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yanko20.blocspot.BlocSpotApp;
import com.yanko20.blocspot.R;
import com.yanko20.blocspot.adapters.CategoryAdapter;
import com.yanko20.blocspot.database.Database;
import com.yanko20.blocspot.model.Category;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import io.realm.RealmResults;

/**
 * Created by ykomizor on 3/22/2017.
 */

public class CategoryFragment extends DialogFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_list, container);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.category_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        CategoryAdapter adapter = new CategoryAdapter(Database.getAllCategories());
        recyclerView.setAdapter(adapter);
        getDialog().setTitle("Choose Category");
        return view;
    }
}
