package com.ruenzuo.pokeffective.activities;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;

import com.ruenzuo.pokeffective.base.BaseActivity;
import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.definitions.OnFilterOptionChangedListener;
import com.ruenzuo.pokeffective.definitions.OnPokemonFilterChangedListener;
import com.ruenzuo.pokeffective.definitions.OnPokemonListSearchListener;
import com.ruenzuo.pokeffective.definitions.OnPokemonSelectedListener;
import com.ruenzuo.pokeffective.fragments.FilterDialogFragment;
import com.ruenzuo.pokeffective.fragments.PokemonListFragment;
import com.ruenzuo.pokeffective.models.FilterOption;
import com.ruenzuo.pokeffective.models.PokedexType;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.models.PokemonType;

public class PokemonListActivity extends BaseActivity implements OnPokemonSelectedListener, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener, OnFilterOptionChangedListener {

    private OnPokemonListSearchListener listSearchListener;
    private OnPokemonFilterChangedListener pokemonFilterListener;
    private MenuItem filterItem;
    private MenuItem clearItem;
    private FilterOption pokedexFilterOption;
    private FilterOption pokemonTypeFilterOption;
    private boolean filterActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filterActive = false;
        pokedexFilterOption = FilterOption.defaultPokedexFilterOption();
        pokemonTypeFilterOption = FilterOption.defaultPokemonTypeFilterOption();
        setContentView(R.layout.pokemon_list_activity);
        getActionBar().setIcon(getResources().getDrawable(R.drawable.ic_action_back));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle("Pokémon");
        PokemonListFragment fragment = (PokemonListFragment) getFragmentManager().findFragmentById(R.id.pokemonListFragment);
        setListSearchListener(fragment);
        setPokemonFilterListener(fragment);
    }

    private void setListSearchListener(Fragment fragment) {
        try {
            listSearchListener = (OnPokemonListSearchListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(fragment.toString()
                    + " must implement OnPokemonListSearchListener");
        }
    }

    private void setPokemonFilterListener(Fragment fragment) {
        try {
            pokemonFilterListener = (OnPokemonFilterChangedListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(fragment.toString()
                    + " must implement OnPokemonFilterChangedListener");
        }
    }

    @Override
    public void onPokemonSelected(Pokemon pokemon) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Pokemon", pokemon);
        setResult(RESULT_OK, returnIntent);
        finishAnimated();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pokemon_list_menu, menu);
        if (filterActive) {
            menu.add(Menu.NONE, R.id.action_pokemon_filter_clear, Menu.NONE, "Clear")
                    .setIcon(getResources().getDrawable(R.drawable.ic_action_trash))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            clearItem = menu.findItem(R.id.action_pokemon_filter_clear);
        }
        MenuItem searchItem = menu.findItem(R.id.action_pokemon_search);
        searchItem.setOnActionExpandListener(this);
        SearchView searchView = (SearchView)searchItem.getActionView();
        setupSearchView(searchView);
        filterItem = menu.findItem(R.id.action_pokemon_filter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            setResult(RESULT_CANCELED, null);
            finishAnimated();
            return true;
        }
        else if (id == R.id.action_pokemon_search) {
            return true;
        }
        else if (id == R.id.action_pokemon_filter_pokedex) {
            FilterOption option = pokedexFilterOption.clone();
            DialogFragment dialog = FilterDialogFragment.newInstance(option);
            dialog.show(getFragmentManager(), "FilterDialogFragment");
            return true;
        }
        else if (id == R.id.action_pokemon_filter_type) {
            FilterOption option = pokemonTypeFilterOption.clone();
            DialogFragment dialog = FilterDialogFragment.newInstance(option);
            dialog.show(getFragmentManager(), "FilterDialogFragment");
        }
        else if (id == R.id.action_pokemon_filter_clear) {
            pokedexFilterOption = FilterOption.defaultPokedexFilterOption();
            pokemonTypeFilterOption = FilterOption.defaultPokemonTypeFilterOption();
            pokemonFilterListener.onPokemonFilterChanged((PokedexType) pokedexFilterOption.getValue(),
                    (PokemonType) pokemonTypeFilterOption.getValue());
            filterActive = false;
            invalidateOptionsMenu();
            return true;
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
        listSearchListener.onSearchQueryChange(newText);
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        filterItem.setVisible(false);
        if (filterActive) {
            clearItem.setVisible(false);
        }
        listSearchListener.onSearchStart();
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        filterItem.setVisible(true);
        if (filterActive) {
            clearItem.setVisible(true);
        }
        listSearchListener.onSearchCancel();
        return true;
    }

    @Override
    public void onFilterOptionChanged(FilterOption filterOption) {
        filterActive = true;
        invalidateOptionsMenu();
        switch (filterOption.getFilterType()) {
            case POKEDEX_TYPE:
                pokedexFilterOption = filterOption;
                break;
            case POKEMON_TYPE:
                pokemonTypeFilterOption = filterOption;
                break;
            default:
                break;
        }
        pokemonFilterListener.onPokemonFilterChanged((PokedexType) pokedexFilterOption.getValue(),
                (PokemonType) pokemonTypeFilterOption.getValue());
    }

}
