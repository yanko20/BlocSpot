package com.yanko20.blocspot.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.yanko20.blocspot.R;
import com.yanko20.blocspot.database.DataHelper;
import com.yanko20.blocspot.model.Category;

import io.realm.Realm;

/**
 * Created by ykomizor on 3/28/2017.
 */

public class AddCategoryDialogFragment extends DialogFragment {

    public static final String DIALOG_TAG = "AddCategoryDialogFragmentTag";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText addCategoryEditText = new EditText(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add new category")
                .setView(addCategoryEditText)
                .setPositiveButton(R.string.add_category, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Adding category..", Toast.LENGTH_SHORT);
                        Category newCategory = new Category(addCategoryEditText.getText().toString());
                        DataHelper.saveCategory(newCategory);
                    }
                })
                .setNegativeButton(R.string.cancel_add_category, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Cancel adding category..", Toast.LENGTH_SHORT);
                    }
                });
        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
