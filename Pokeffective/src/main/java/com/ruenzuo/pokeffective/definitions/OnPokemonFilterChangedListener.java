package com.ruenzuo.pokeffective.definitions;

import com.ruenzuo.pokeffective.models.PokedexType;
import com.ruenzuo.pokeffective.models.PokemonType;

/**
 * Created by ruenzuo on 17/04/14.
 */
public interface OnPokemonFilterChangedListener {

    public void onPokemonFilterChanged(PokedexType pokedexType, PokemonType pokemonType);

}
