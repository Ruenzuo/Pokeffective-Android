package com.ruenzuo.pokeffective.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.adapters.FilterMenuAdapter;
import com.ruenzuo.pokeffective.definitions.OnFilterOptionChangedListener;
import com.ruenzuo.pokeffective.models.FilterOption;

/**
 * Created by ruenzuo on 17/04/14.
 */
public class FilterOptionsMenuFragment extends ListFragment {

    private OnFilterOptionChangedListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFilterOptionChangedListener) {
            listener = (OnFilterOptionChangedListener)activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFilterOptionChangedListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FilterMenuAdapter adapter = new FilterMenuAdapter(getActivity(), R.layout.filter_menu_row);
        adapter.addAll(FilterOption.pokemonFilterOptions());
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        FilterOption option = (FilterOption) getListAdapter().getItem(position);
        switch (option.getFilterType()) {
            case POKEDEX_TYPE:
                showPokedexFilteringDialog();
                break;
            case POKEMON_TYPE:
                showPokemonTypeFilteringDialog();
                break;
        }
    }

    private void showPokedexFilteringDialog()
    {
        DialogFragment dialog = new PokedexFilterDialogFragment();
        dialog.show(getChildFragmentManager(), "PokedexFilterDialogFragment");
    }

    private void showPokemonTypeFilteringDialog()
    {

    }

}
