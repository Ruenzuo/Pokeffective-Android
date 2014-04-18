package com.ruenzuo.pokeffective.definitions;

import com.ruenzuo.pokeffective.models.MoveCategory;
import com.ruenzuo.pokeffective.models.MoveLearnMethod;
import com.ruenzuo.pokeffective.models.PokemonType;

/**
 * Created by ruenzuo on 18/04/14.
 */
public interface OnMoveFilterChangedListener {

    public void onMoveFilterChanged(MoveCategory category, MoveLearnMethod learnMethod, PokemonType moveType);

}
