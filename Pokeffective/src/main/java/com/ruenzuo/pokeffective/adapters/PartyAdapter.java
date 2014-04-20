package com.ruenzuo.pokeffective.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhaarman.listviewanimations.ArrayAdapter;
import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.models.SelectionType;
import com.ruenzuo.pokeffective.utils.SDKUtils;
import com.ruenzuo.pokeffective.utils.ViewUtils;

import java.util.ArrayList;

/**
 * Created by ruenzuo on 19/04/14.
 */
public class PartyAdapter extends ArrayAdapter<Pokemon> {

    private int resourceId;
    private Context context;

    public PartyAdapter(Context applicationContext, int resource, ArrayList<Pokemon> items) {
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
        Pokemon pokemon = getItem(position);
        TextView txtViewPartyMemberFirstAttack = (TextView) convertView.findViewById(R.id.txtViewPartyMemberFirstAttack);
        txtViewPartyMemberFirstAttack.setText(pokemon.moves().get(0).getName());
        TextView txtViewPartyMemberSecondAttack = (TextView) convertView.findViewById(R.id.txtViewPartyMemberSecondAttack);
        txtViewPartyMemberSecondAttack.setText(pokemon.moves().get(1).getName());
        TextView txtViewPartyMemberThirdAttack = (TextView) convertView.findViewById(R.id.txtViewPartyMemberThirdAttack);
        txtViewPartyMemberThirdAttack.setText(pokemon.moves().get(2).getName());
        TextView txtViewPartyMemberForthAttack = (TextView) convertView.findViewById(R.id.txtViewPartyMemberForthAttack);
        txtViewPartyMemberForthAttack.setText(pokemon.moves().get(3).getName());
        ImageView imgViewPokemonPicture = (ImageView) convertView.findViewById(R.id.imgViewPartyMemberPicture);
        String resourceName = String.format("pokemon_%03d", pokemon.getIdentifier());
        int resourceId = context.getResources().getIdentifier(resourceName, "drawable", context.getPackageName());
        Drawable drawable = context.getResources().getDrawable(resourceId);
        imgViewPokemonPicture.setImageDrawable(drawable);
        if (pokemon.isSelected()) {
            if(SDKUtils.isHigherThanJellyBean()) {
                convertView.setBackground(ViewUtils.getBackground(pokemon.getFirstType(), pokemon.getSecondType(), SelectionType.SELECTED));
            } else {
                convertView.setBackgroundDrawable(ViewUtils.getBackground(pokemon.getFirstType(), pokemon.getSecondType(), SelectionType.SELECTED));
            }
        }
        else {
            if(SDKUtils.isHigherThanJellyBean()) {
                convertView.setBackground(ViewUtils.getBackground(pokemon.getFirstType(), pokemon.getSecondType(), SelectionType.UNSELECTED));
            } else {
                convertView.setBackgroundDrawable(ViewUtils.getBackground(pokemon.getFirstType(), pokemon.getSecondType(), SelectionType.UNSELECTED));
            }
        }
        return convertView;
    }

}
