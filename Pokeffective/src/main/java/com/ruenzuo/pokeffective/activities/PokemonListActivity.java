package com.ruenzuo.pokeffective.activities;

import android.app.Activity;
import android.os.Bundle;

import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.fragments.PokemonListFragment;
import com.ruenzuo.pokeffective.models.Pokemon;


public class PokemonListActivity extends Activity implements PokemonListFragment.OnPokemonSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_list_activity);

    }

    @Override
    public void onCountrySelected(Pokemon pokemon) {

    }
}
