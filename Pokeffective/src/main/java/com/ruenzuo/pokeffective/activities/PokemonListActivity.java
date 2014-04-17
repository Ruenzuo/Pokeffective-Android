package com.ruenzuo.pokeffective.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;

import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.definitions.OnFilterOptionChangedListener;
import com.ruenzuo.pokeffective.definitions.OnPokemonListSearchListener;
import com.ruenzuo.pokeffective.definitions.OnPokemonSelectedListener;
import com.ruenzuo.pokeffective.fragments.FilterDialogFragment;
import com.ruenzuo.pokeffective.fragments.PokemonListFragment;
import com.ruenzuo.pokeffective.models.FilterOption;
import com.ruenzuo.pokeffective.models.Pokemon;

public class PokemonListActivity extends Activity implements OnPokemonSelectedListener, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener, OnFilterOptionChangedListener {

    private OnPokemonListSearchListener listener;
    private MenuItem filterItem;
    private FilterOption pokedexFilterOption;
    private FilterOption pokemonTypeFilterOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pokedexFilterOption = FilterOption.defaultPokedexFilterOption();
        pokemonTypeFilterOption = FilterOption.defaultPokemonTypeFilterOption();
        setContentView(R.layout.pokemon_list_activity);
        getActionBar().setIcon(getResources().getDrawable(R.drawable.ic_action_back));
        PokemonListFragment fragment = (PokemonListFragment) getFragmentManager().findFragmentById(R.id.pokemonListFragment);
        listener = fragment;
    }

    @Override
    public void onPokemonSelected(Pokemon pokemon) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setOnActionExpandListener(this);
        SearchView searchView = (SearchView)searchItem.getActionView();
        setupSearchView(searchView);
        filterItem = menu.findItem(R.id.action_filter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        else if (id == R.id.action_filter_pokedex) {
            FilterOption option = pokedexFilterOption.clone();
            DialogFragment dialog = FilterDialogFragment.newInstance(option);
            dialog.show(getFragmentManager(), "FilterDialogFragment");
            return true;
        }
        else if (id == R.id.action_filter_pokemon_type) {
            FilterOption option = pokemonTypeFilterOption.clone();
            DialogFragment dialog = FilterDialogFragment.newInstance(option);
            dialog.show(getFragmentManager(), "FilterDialogFragment");
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupSearchView(SearchView searchView) {
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getResources().getString(R.string.pokemon_query_hint));
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setHintTextColor(Color.WHITE);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listener.onSearchQueryChange(newText);
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        filterItem.setVisible(false);
        listener.onSearchStart();
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        filterItem.setVisible(true);
        listener.onSearchCancel();
        return true;
    }

    @Override
    public void onFilterOptionChanged(FilterOption filterOption) {
        switch (filterOption.getFilterType()) {
            case POKEDEX_TYPE:
                pokedexFilterOption = filterOption;
                break;
            case POKEMON_TYPE:
                pokemonTypeFilterOption = filterOption;
                break;
        }
    }

}
