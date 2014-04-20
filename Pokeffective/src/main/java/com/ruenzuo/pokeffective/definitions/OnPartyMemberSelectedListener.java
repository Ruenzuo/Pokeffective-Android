package com.ruenzuo.pokeffective.definitions;

import com.ruenzuo.pokeffective.models.Pokemon;

/**
 * Created by ruenzuo on 19/04/14.
 */
public interface OnPartyMemberSelectedListener {

    public boolean shouldAllowSelection(Pokemon pokemon);
    public void onPartyMemberSelected(Pokemon member);

}
