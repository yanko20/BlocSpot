package com.yanko20.blocspot.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.yanko20.blocspot.database.Database;
import com.yanko20.blocspot.model.Category;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by ykomizor on 3/22/2017.
 */

public class CategoryFragment extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Iterator<Category> categoryIterator = Database.getAllCategories().iterator();
        ArrayList<String> namesList = new ArrayList<>();
        while(categoryIterator.hasNext()){
            namesList.add(categoryIterator.next().getName());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Category");
        // todo builder.setAdapter();
        builder.setMultiChoiceItems(
                namesList.toArray(new String[namesList.size()]),
                null,
                new DialogInterface.OnMultiChoiceClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    }
                });
        return builder.create();
    }
}
