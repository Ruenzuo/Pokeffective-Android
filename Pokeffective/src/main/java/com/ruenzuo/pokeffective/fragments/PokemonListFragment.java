package com.ruenzuo.pokeffective.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.adapters.PokemonAdapter;
import com.ruenzuo.pokeffective.definitions.OnPokemonFilterChangedListener;
import com.ruenzuo.pokeffective.definitions.OnPokemonListSearchListener;
import com.ruenzuo.pokeffective.definitions.OnPokemonSelectedListener;
import com.ruenzuo.pokeffective.models.PokedexType;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.models.PokemonType;
import com.ruenzuo.pokeffective.tasks.PokemonTask;
import com.telly.groundy.Groundy;
import com.telly.groundy.annotations.OnSuccess;
import com.telly.groundy.annotations.Param;

import java.util.ArrayList;

/**
 * Created by ruenzuo on 16/04/14.
 */
public class PokemonListFragment extends ListFragment implements OnPokemonListSearchListener, OnPokemonFilterChangedListener {

    private OnPokemonSelectedListener listener;

    private void startPokemonTask(PokedexType pokedexType, PokemonType pokemonType) {
        Groundy.create(PokemonTask.class)
                .arg("PokedexType", pokedexType)
                .arg("PokemonType", pokemonType)
                .callback(this)
                .queueUsing(getActivity());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnPokemonSelectedListener) {
            listener = (OnPokemonSelectedListener)activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPokemonSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PokemonAdapter adapter = new PokemonAdapter(getActivity(), R.layout.pokemon_row, new ArrayList<Pokemon>());
        setListAdapter(adapter);
        startPokemonTask(PokedexType.NATIONAL, PokemonType.NONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pokemon_list_fragment, container, false);
        return view;
    }

    @OnSuccess(PokemonTask.class)
    public void onSuccess(@Param("Pokemons") ArrayList<Pokemon> pokemons) {
        PokemonAdapter adapter = (PokemonAdapter)getListAdapter();
        adapter.clear();
        adapter.addAllCopying(pokemons);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchQueryChange(String query) {
        PokemonAdapter adapter = (PokemonAdapter)getListAdapter();
        adapter.getFilter().filter(query);
    }

    @Override
    public void onSearchStart() {
        PokemonAdapter adapter = (PokemonAdapter)getListAdapter();
        adapter.clear();
        adapter.setSearching(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchCancel() {
        PokemonAdapter adapter = (PokemonAdapter)getListAdapter();
        adapter.setSearching(false);
        adapter.restoreCopy();
    }

    @Override
    public void onPokemonFilterChanged(PokedexType pokedexType, PokemonType pokemonType) {
        startPokemonTask(pokedexType, pokemonType);
    }

}
