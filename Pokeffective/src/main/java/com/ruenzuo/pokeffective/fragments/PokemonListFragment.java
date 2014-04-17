package com.ruenzuo.pokeffective.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
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
        SwingBottomInAnimationAdapter swingRightInAnimationAdapter = new SwingBottomInAnimationAdapter(adapter);
        swingRightInAnimationAdapter.setAbsListView(getListView());
        setListAdapter(swingRightInAnimationAdapter);
        startPokemonTask(PokedexType.NATIONAL, PokemonType.NONE);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Pokemon pokemon = (Pokemon)getListAdapter().getItem(position);
        listener.onPokemonSelected(pokemon);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pokemon_list_fragment, container, false);
        return view;
    }

    @OnSuccess(PokemonTask.class)
    public void onSuccess(@Param("Pokemons") ArrayList<Pokemon> pokemons) {
        SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)getListAdapter();
        listAdapter.setShouldAnimateFromPosition(0);
        PokemonAdapter adapter = (PokemonAdapter)listAdapter.getDecoratedBaseAdapter();
        adapter.clear();
        adapter.addAllCopying(pokemons);
        adapter.notifyDataSetChanged();
        getListView().setSelection(0);
    }

    @Override
    public void onSearchQueryChange(String query) {
        SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)getListAdapter();
        PokemonAdapter adapter = (PokemonAdapter)listAdapter.getDecoratedBaseAdapter();
        adapter.getFilter().filter(query);
    }

    @Override
    public void onSearchStart() {
        SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)getListAdapter();
        listAdapter.setShouldAnimateFromPosition(0);
        PokemonAdapter adapter = (PokemonAdapter)listAdapter.getDecoratedBaseAdapter();
        adapter.clear();
        adapter.setSearching(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchCancel() {
        SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)getListAdapter();
        listAdapter.setShouldAnimateFromPosition(0);
        PokemonAdapter adapter = (PokemonAdapter)listAdapter.getDecoratedBaseAdapter();
        adapter.setSearching(false);
        adapter.restoreCopy();
    }

    @Override
    public void onPokemonFilterChanged(PokedexType pokedexType, PokemonType pokemonType) {
        startPokemonTask(pokedexType, pokemonType);
    }

}
