package com.ruenzuo.pokeffective.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
