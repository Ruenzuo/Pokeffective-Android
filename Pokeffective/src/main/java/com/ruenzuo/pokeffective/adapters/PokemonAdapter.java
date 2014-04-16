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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.utils.ColorUtils;

/**
 * Created by ruenzuo on 16/04/14.
 */
public class PokemonAdapter extends ArrayAdapter<Pokemon> {

    private int resourceId;

    public PokemonAdapter(Context context, int resource) {
        super(context, resource);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
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
        int resourceId = getContext().getResources().getIdentifier(resourceName, "drawable", getContext().getPackageName());
        Drawable drawable = getContext().getResources().getDrawable(resourceId);
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

}
