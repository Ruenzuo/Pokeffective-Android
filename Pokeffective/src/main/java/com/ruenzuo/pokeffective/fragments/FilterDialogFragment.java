package com.ruenzuo.pokeffective.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.ruenzuo.pokeffective.definitions.OnFilterOptionChangedListener;
import com.ruenzuo.pokeffective.models.FilterOption;
import com.ruenzuo.pokeffective.utils.FilterUtils;

/**
 * Created by ruenzuo on 17/04/14.
 */
public class FilterDialogFragment extends DialogFragment {

    private OnFilterOptionChangedListener listener;
    private int selectedValue = -1;

    public static FilterDialogFragment newInstance(FilterOption filterOption) {
        FilterDialogFragment fragment = new FilterDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("FilterOption", filterOption);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnFilterOptionChangedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFilterOptionChangedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        FilterOption option = (FilterOption) getArguments().getSerializable("FilterOption");
        selectedValue = FilterUtils.getIndex(option);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(option.getLabel())
               .setSingleChoiceItems(FilterUtils.getFilterOptions(option.getFilterType()),
                       selectedValue, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               selectedValue = which;
                           }
                       }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                FilterOption option = (FilterOption) getArguments().getSerializable("FilterOption");
                option.setValue(FilterUtils.getValue(option.getFilterType(), selectedValue));
                listener.onFilterOptionChanged(option);
            }
        }).setNegativeButton("Cancel", null);
        return builder.create();
    }

}
