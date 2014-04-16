package com.ruenzuo.pokeffective.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.definitions.OnPokemonListSearchListener;
import com.ruenzuo.pokeffective.definitions.OnPokemonSelectedListener;
import com.ruenzuo.pokeffective.fragments.PokemonListFragment;
import com.ruenzuo.pokeffective.models.Pokemon;

public class PokemonListActivity extends Activity implements OnPokemonSelectedListener, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private OnPokemonListSearchListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void setupSearchView(SearchView searchView) {
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getResources().getString(R.string.pokemon_query_hint));
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setHintTextColor(Color.WHITE);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listener.onSearchQueryChange(newText);
        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        listener.onSearchStart();
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        listener.onSearchCancel();
        return true;
    }
}
