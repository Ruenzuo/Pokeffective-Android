package com.ruenzuo.pokeffective.definitions;

/**
 * Created by ruenzuo on 16/04/14.
 */
public interface OnPokemonListSearchListener {

    public void onSearchQueryChange(String query);
    public void onSearchStart();
    public void onSearchCancel();

}
