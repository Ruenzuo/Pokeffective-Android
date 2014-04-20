package com.ruenzuo.pokeffective.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.models.STAB;
import com.ruenzuo.pokeffective.models.SelectionType;
import com.ruenzuo.pokeffective.utils.SDKUtils;
import com.ruenzuo.pokeffective.utils.ViewUtils;

import java.util.ArrayList;

/**
 * Created by ruenzuo on 21/04/14.
 */
public class STABAdapter extends ArrayAdapter<STAB> {

    private int resourceId;
    private Context context;

    public STABAdapter(Context applicationContext, int resource, ArrayList<STAB> items) {
        super(items);
        context = applicationContext;
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(resourceId, null);
        }
        STAB anSTAB = getItem(position);
        TextView txtViewSTABPokemon = (TextView) convertView.findViewById(R.id.txtViewSTABPokemon);
        txtViewSTABPokemon.setText(anSTAB.getPokemon().getName());
        TextView txtViewSTABMove = (TextView) convertView.findViewById(R.id.txtViewSTABMove);
        txtViewSTABMove.setText(anSTAB.getMove().getName());
        if(SDKUtils.isHigherThanJellyBean()) {
            convertView.setBackground(ViewUtils.getBackground(anSTAB.getMove().getPokemonType(), null, SelectionType.NO_INTERACTION));
        } else {
            convertView.setBackgroundDrawable(ViewUtils.getBackground(anSTAB.getMove().getPokemonType(), null, SelectionType.NO_INTERACTION));
        }
        return convertView;
    }

}
