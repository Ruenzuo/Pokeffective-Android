package com.ruenzuo.pokeffective.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import com.ruenzuo.pokeffective.models.PokemonType;

/**
 * Created by ruenzuo on 17/04/14.
 */
public class ViewUtils {

    public static Drawable getBackground(PokemonType firstType, PokemonType secondType, boolean isSelected) {
        StateListDrawable states = new StateListDrawable();
        if (secondType != null) {
            int colors[] = new int[2];
            colors[0] = firstType.toColor();
            colors[1] = secondType.toColor();
            int darkerColors[] = new int[2];
            darkerColors[0] = ColorUtils.darkerColor(colors[0]);
            darkerColors[1] = ColorUtils.darkerColor(colors[1]);
            GradientDrawable shapeUnselected = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
            shapeUnselected.setCornerRadius(10 * Resources.getSystem().getDisplayMetrics().density);
            GradientDrawable shapeSelected = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, darkerColors);
            shapeSelected.setCornerRadius(10 * Resources.getSystem().getDisplayMetrics().density);
            if (isSelected) {
                states.addState(new int[] {android.R.attr.state_pressed}, shapeUnselected);
                states.addState(new int[] {android.R.attr.state_focused}, shapeSelected);
                states.addState(new int[] { }, shapeSelected);
            }
            else {
                states.addState(new int[] {android.R.attr.state_pressed}, shapeSelected);
                states.addState(new int[] {android.R.attr.state_focused}, shapeSelected);
                states.addState(new int[] { }, shapeUnselected);
            }
        }
        else {
            GradientDrawable shapeUnselected = new GradientDrawable();
            shapeUnselected.setColor(firstType.toColor());
            shapeUnselected.setCornerRadius(10 * Resources.getSystem().getDisplayMetrics().density);
            GradientDrawable shapeSelected = new GradientDrawable();
            shapeSelected.setColor(ColorUtils.darkerColor(firstType.toColor()));
            shapeSelected.setCornerRadius(10 * Resources.getSystem().getDisplayMetrics().density);
            if (isSelected) {
                states.addState(new int[] {android.R.attr.state_pressed}, shapeUnselected);
                states.addState(new int[] {android.R.attr.state_focused}, shapeSelected);
                states.addState(new int[] { }, shapeSelected);
            }
            else {
                states.addState(new int[] {android.R.attr.state_pressed}, shapeSelected);
                states.addState(new int[] {android.R.attr.state_focused}, shapeSelected);
                states.addState(new int[] { }, shapeUnselected);
            }
        }
        return states;
    }

}
