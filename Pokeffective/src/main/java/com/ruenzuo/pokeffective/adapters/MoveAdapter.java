package com.ruenzuo.pokeffective.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.models.Move;
import com.ruenzuo.pokeffective.models.SelectionType;
import com.ruenzuo.pokeffective.utils.ViewUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ruenzuo on 18/04/14.
 */
public class MoveAdapter extends ArrayAdapter<Move> implements Filterable {

    private int resourceId;
    private ArrayList<Move> itemsCopy;
    private boolean searching;
    private Context context;

    public boolean isSearching() {
        return searching;
    }

    public void setSearching(boolean searching) {
        this.searching = searching;
    }

    public MoveAdapter(Context applicationContext, int resource, ArrayList<Move> items) {
        super(items);
        context = applicationContext;
        itemsCopy = (ArrayList<Move>) items.clone();
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

    public void addAllCopying(Collection<? extends Move> collection) {
        super.addAll(collection);
        ArrayList<Move> casted = (ArrayList<Move>) collection;
        itemsCopy = (ArrayList<Move>) casted.clone();
    }

    public void restoreCopy() {
        clear();
        addAll(itemsCopy);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (isSearching()) {
                    ArrayList<Move> filtered = (ArrayList<Move>) results.values;
                    MoveAdapter.this.clear();
                    MoveAdapter.this.addAll(filtered);
                    notifyDataSetChanged();
                }
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Move> filtered = new ArrayList<Move>();
                constraint = constraint.toString().toLowerCase();
                if (!constraint.toString().isEmpty()) {
                    for (int i = 0; i < itemsCopy.size(); i++) {
                        Move move = MoveAdapter.this.itemsCopy.get(i);
                        if (move.getName().toLowerCase().contains(constraint.toString()))  {
                            filtered.add(move);
                        }
                    }
                }
                results.values = filtered;
                return results;
            }

        };
        return filter;
    }
}
