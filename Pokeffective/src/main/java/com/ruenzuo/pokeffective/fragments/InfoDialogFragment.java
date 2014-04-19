package com.ruenzuo.pokeffective.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.activities.InfoActivity;

/**
 * Created by ruenzuo on 19/04/14.
 */
public class InfoDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.info_dialog_fragment, null);
        TextView txtViewDeveloper = (TextView) view.findViewById(R.id.txtViewDeveloper);
        txtViewDeveloper.setText(Html.fromHtml("PokéApp by Renzo Crisóstomo <a href='http://www.twitter.com/Ruenzuo'>@Ruenzuo</a>"));
        txtViewDeveloper.setMovementMethod(LinkMovementMethod.getInstance());
        TextView txtViewOpenSource = (TextView) view.findViewById(R.id.txtViewOpenSource);
        txtViewOpenSource.setText(Html.fromHtml("PokéApp for Android is built using open source software: <a href='com.ruenzuo.pokeffective://open-source/license'>license</a>"));
        txtViewOpenSource.setMovementMethod(LinkMovementMethod.getInstance());
        builder.setView(view)
                .setPositiveButton("OK", null)
                .setNegativeButton("Legal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getActivity(), InfoActivity.class);
                        startActivity(intent);
                    }
                });
        return builder.create();
    }

}
