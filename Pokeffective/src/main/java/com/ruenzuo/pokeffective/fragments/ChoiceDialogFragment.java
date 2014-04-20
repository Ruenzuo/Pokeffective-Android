package com.ruenzuo.pokeffective.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.ruenzuo.pokeffective.definitions.OnChoiceSelectedListener;

/**
 * Created by ruenzuo on 18/04/14.
 */
public class ChoiceDialogFragment extends DialogFragment {

    OnChoiceSelectedListener listener;

    public static ChoiceDialogFragment newInstance(String title, String message, String[] buttons) {
        ChoiceDialogFragment fragment = new ChoiceDialogFragment();
        Bundle args = new Bundle();
        args.putString("Title", title);
        args.putString("Message", message);
        args.putSerializable("Buttons", buttons);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnChoiceSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnChoiceSelectedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString("Message");
        String title = getArguments().getString("Title");
        String[] buttons = (String[]) getArguments().getSerializable("Buttons");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttons[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onChoiceSelected(true);
                    }
                }).setNegativeButton(buttons[1], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onChoiceSelected(false);
            }
        });
        return builder.create();
    }

}
