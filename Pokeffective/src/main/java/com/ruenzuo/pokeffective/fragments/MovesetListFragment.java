package com.ruenzuo.pokeffective.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.ruenzuo.pokeffective.models.Pokemon;

/**
 * Created by ruenzuo on 18/04/14.
 */
public class MovesetListFragment extends Fragment {

    public static MovesetListFragment newInstance(Pokemon pokemon) {
        MovesetListFragment fragment = new MovesetListFragment();
        Bundle args = new Bundle();
        args.putSerializable("Pokemon", pokemon);
        fragment.setArguments(args);
        return fragment;
    }

}
