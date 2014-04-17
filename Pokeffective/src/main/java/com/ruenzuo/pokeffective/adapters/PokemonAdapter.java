package com.ruenzuo.pokeffective.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.utils.ColorUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ruenzuo on 16/04/14.
 */
public class PokemonAdapter extends ArrayAdapter<Pokemon> implements Filterable {

    private int resourceId;
    private ArrayList<Pokemon> itemsCopy;
    private boolean searching;
    private Context context;

    public boolean isSearching() {
        return searching;
    }

    public void setSearching(boolean searching) {
        this.searching = searching;
    }

    public PokemonAdapter(Context applicationContext, int resource, ArrayList<Pokemon> items) {
        super();
        context = applicationContext;
        itemsCopy = (ArrayList<Pokemon>) items.clone();
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(resourceId, null);
        }
        Pokemon pokemon = getItem(position);
        TextView txtViewPokemonName = (TextView) convertView.findViewById(R.id.txtViewPokemonName);
        txtViewPokemonName.setText(pokemon.getName());
        TextView txtViewPokedexNumber = (TextView) convertView.findViewById(R.id.txtViewPokedexNumber);
        txtViewPokedexNumber.setText("" + String.format("%03d", pokemon.getPokedexNumber()));
        TextView txtViewPokemonTypes = (TextView) convertView.findViewById(R.id.txtViewPokemonTypes);
        if (pokemon.getSecondType() != null) {
            txtViewPokemonTypes.setText(pokemon.getFirstType().toString() + " / " + pokemon.getSecondType().toString());
        }
        else {
            txtViewPokemonTypes.setText(pokemon.getFirstType().toString());
        }
        ImageView imgViewPokemonPicture = (ImageView) convertView.findViewById(R.id.imgViewPokemonPicture);
        String resourceName = String.format("pokemon_%03d", pokemon.getIdentifier());
        int resourceId = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
        Drawable drawable = context.getResources().getDrawable(resourceId);
        imgViewPokemonPicture.setImageDrawable(drawable);
        if (pokemon.getSecondType() != null) {
            int colors[] = new int[2];
            colors[0] = pokemon.getFirstType().toColor();
            colors[1] = pokemon.getSecondType().toColor();
            int darkerColors[] = new int[2];
            darkerColors[0] = ColorUtils.darkerColor(colors[0]);
            darkerColors[1] = ColorUtils.darkerColor(colors[1]);
            GradientDrawable shapeUnselected = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
            shapeUnselected.setCornerRadius(10 * Resources.getSystem().getDisplayMetrics().density);
            GradientDrawable shapeSelected = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, darkerColors);
            shapeSelected.setCornerRadius(10 * Resources.getSystem().getDisplayMetrics().density);
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[] {android.R.attr.state_pressed}, shapeSelected);
            states.addState(new int[] {android.R.attr.state_focused}, shapeSelected);
            states.addState(new int[] { }, shapeUnselected);
            convertView.setBackground(states);
        }
        else {
            GradientDrawable shapeUnselected = new GradientDrawable();
            shapeUnselected.setColor(pokemon.getFirstType().toColor());
            shapeUnselected.setCornerRadius(10 * Resources.getSystem().getDisplayMetrics().density);
            GradientDrawable shapeSelected = new GradientDrawable();
            shapeSelected.setColor(ColorUtils.darkerColor(pokemon.getFirstType().toColor()));
            shapeSelected.setCornerRadius(10 * Resources.getSystem().getDisplayMetrics().density);
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[] {android.R.attr.state_pressed}, shapeSelected);
            states.addState(new int[] {android.R.attr.state_focused}, shapeSelected);
            states.addState(new int[] { }, shapeUnselected);
            convertView.setBackground(states);
        }
        return convertView;
    }

    public void addAllCopying(Collection<? extends Pokemon> collection) {
        super.addAll(collection);
        ArrayList<Pokemon> casted = (ArrayList<Pokemon>) collection;
        itemsCopy = (ArrayList<Pokemon>) casted.clone();
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
                    ArrayList<Pokemon> filtered = (ArrayList<Pokemon>) results.values;
                    PokemonAdapter.this.clear();
                    PokemonAdapter.this.addAll(filtered);
                    notifyDataSetChanged();
                }
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Pokemon> filtered = new ArrayList<Pokemon>();
                constraint = constraint.toString().toLowerCase();
                if (!constraint.toString().isEmpty()) {
                    for (int i = 0; i < itemsCopy.size(); i++) {
                        Pokemon pokemon = PokemonAdapter.this.itemsCopy.get(i);
                        if (pokemon.getName().toLowerCase().contains(constraint.toString()))  {
                            filtered.add(pokemon);
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
