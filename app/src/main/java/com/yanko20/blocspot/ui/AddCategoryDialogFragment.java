package com.yanko20.blocspot.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.yanko20.blocspot.R;

/**
 * Created by ykomizor on 3/28/2017.
 */

public class AddCategoryDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Add new category")
                .setPositiveButton(R.string.add_category, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Adding category..", Toast.LENGTH_SHORT);
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
}
