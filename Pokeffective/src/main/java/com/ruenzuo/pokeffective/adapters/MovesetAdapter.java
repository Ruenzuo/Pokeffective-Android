package com.ruenzuo.pokeffective.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.models.Move;
import com.ruenzuo.pokeffective.models.SelectionType;
import com.ruenzuo.pokeffective.utils.ViewUtils;

import java.util.ArrayList;

/**
 * Created by ruenzuo on 18/04/14.
 */
public class MovesetAdapter extends ArrayAdapter<Move> {

    private int resourceId;
    private Context context;

    public MovesetAdapter(Context applicationContext, int resource, ArrayList<Move> items) {
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
        Move move = getItem(position);
        TextView txtViewMoveName = (TextView) convertView.findViewById(R.id.txtViewMoveName);
        txtViewMoveName.setText(move.getName());
        TextView txtViewMoveCategory = (TextView) convertView.findViewById(R.id.txtViewMoveCategory);
        txtViewMoveCategory.setText(move.getCategory().toString());
        TextView txtViewMovePower = (TextView) convertView.findViewById(R.id.txtViewMovePower);
        txtViewMovePower.setText("Pwr: " + move.getPower());
        TextView txtViewMoveAccuracy = (TextView) convertView.findViewById(R.id.txtViewMoveAccuracy);
        txtViewMoveAccuracy.setText("Acc: " + move.getAccuracy() + "%");
        convertView.setBackground(ViewUtils.getBackground(move.getPokemonType(), null, SelectionType.UNSELECTED));
        return convertView;
    }

}
