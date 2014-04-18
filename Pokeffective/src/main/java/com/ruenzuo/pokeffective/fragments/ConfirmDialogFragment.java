package com.ruenzuo.pokeffective.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.ruenzuo.pokeffective.definitions.OnConfirmListener;

/**
 * Created by ruenzuo on 18/04/14.
 */
public class ConfirmDialogFragment extends DialogFragment {

    OnConfirmListener listener;

    public static ConfirmDialogFragment newInstance(String message) {
        ConfirmDialogFragment fragment = new ConfirmDialogFragment();
        Bundle args = new Bundle();
        args.putString("Message", message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnConfirmListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnConfirmListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString("Message");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onConfirm(true);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onConfirm(false);
            }
        });
        return builder.create();
    }

}
