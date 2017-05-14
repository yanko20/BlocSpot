package com.yanko20.blocspot.ui;

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
import android.widget.ImageButton;
import android.widget.TextView;

import com.yanko20.blocspot.R;
import com.yanko20.blocspot.adapters.AssignCategoryAdapter;
import com.yanko20.blocspot.adapters.FilterCategoryAdapter;
import com.yanko20.blocspot.adapters.PoiItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ykomizor on 3/22/2017.
 */

public class CategoryDialogFragment extends DialogFragment {

    public static final String DIALOG_TAG = "AddCategoryDialogFragmentTag";
    public static final String logTag = "CategoryDialFrag.class";
    public static final String MODE_KEY = "modeKey";
    public static final String ASSIGN_MODE = "assignMode";
    public static final String FILTER_MODE = "filterMode";
    private String mode;
    private static List<FilterCategoryDialogDismissListener> dialogDismissListeners
            = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View categoryListView = inflater.inflate(R.layout.category_list, container);
        TextView categoryListViewTitle = (TextView) categoryListView.findViewById(R.id.category_list_view_title);
        ImageButton addCategoryButton = (ImageButton) categoryListView.findViewById(R.id.add_category_button);
        final RecyclerView recyclerView = (RecyclerView) categoryListView.findViewById(R.id.category_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        Bundle bundle = this.getArguments();

        mode = bundle.getString(MODE_KEY);
        if (mode == ASSIGN_MODE) {
            categoryListViewTitle.setText(R.string.assign_category_list_title);
            String poiId = bundle.getString(PoiItemAdapter.PoiItemViewHolder.POI_ID_KEY);
            recyclerView.setAdapter(new AssignCategoryAdapter(poiId));
        } else if (mode == FILTER_MODE) {
            categoryListViewTitle.setText(R.string.filter_category_list_title);
            recyclerView.setAdapter(new FilterCategoryAdapter());
        }

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddCategoryDialogFragment()
                        .show(getFragmentManager(), AddCategoryDialogFragment.DIALOG_TAG);
            }
        });
        return categoryListView;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(logTag, "CategoryDialogFragment.onDismissFilterCategoryDialog");
        notifyCategoryDialogDismissListeners();
    }

    protected static void setCategoryDialogDismissListener(FilterCategoryDialogDismissListener listener) {
        dialogDismissListeners.add(listener);
    }

    protected static void removeCategoryDialogDismissListener(FilterCategoryDialogDismissListener listener) {
        dialogDismissListeners.remove(listener);
    }

    private void notifyCategoryDialogDismissListeners() {
        if (mode != null && mode == FILTER_MODE) {
            for (FilterCategoryDialogDismissListener listener : dialogDismissListeners) {
                listener.onDismissFilterCategoryDialog();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

interface FilterCategoryDialogDismissListener {
    void onDismissFilterCategoryDialog();
}
