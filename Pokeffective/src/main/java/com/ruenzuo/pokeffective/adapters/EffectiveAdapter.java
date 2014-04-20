package com.ruenzuo.pokeffective.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.models.Effective;
import com.ruenzuo.pokeffective.utils.ViewUtils;

import java.util.ArrayList;

/**
 * Created by ruenzuo on 20/04/14.
 */
public class EffectiveAdapter extends ArrayAdapter<Effective> {

    private int resourceId;
    private Context context;

    public EffectiveAdapter(Context applicationContext, int resource, ArrayList<Effective> items) {
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
        Effective effective = getItem(position);
        TextView txtViewEffectiveType = (TextView) convertView.findViewById(R.id.txtViewEffectiveType);
        txtViewEffectiveType.setText(effective.getPokemonType().toString());
        TextView txtViewEffectiveValue = (TextView) convertView.findViewById(R.id.txtViewEffectiveValue);
        txtViewEffectiveValue.setText(effective.getEffectiveness().toString());
        convertView.setBackground(ViewUtils.getBackground(effective.getPokemonType(), null, false));
        return convertView;
    }

}
