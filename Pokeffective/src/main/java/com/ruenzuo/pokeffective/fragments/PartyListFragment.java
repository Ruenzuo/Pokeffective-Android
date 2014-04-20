package com.ruenzuo.pokeffective.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.adapters.PartyAdapter;
import com.ruenzuo.pokeffective.definitions.OnPartyMemberSelectedListener;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.tasks.BoxTask;
import com.telly.groundy.Groundy;
import com.telly.groundy.annotations.OnSuccess;
import com.telly.groundy.annotations.Param;

import java.util.ArrayList;

/**
 * Created by ruenzuo on 19/04/14.
 */
public class PartyListFragment extends ListFragment {

    private OnPartyMemberSelectedListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnPartyMemberSelectedListener) {
            listener = (OnPartyMemberSelectedListener)activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPartyMemberSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PartyAdapter adapter = new PartyAdapter(getActivity(), R.layout.member_row, new ArrayList<Pokemon>());
        SwingBottomInAnimationAdapter swingRightInAnimationAdapter = new SwingBottomInAnimationAdapter(adapter);
        swingRightInAnimationAdapter.setAbsListView(getListView());
        setListAdapter(swingRightInAnimationAdapter);
        Groundy.create(BoxTask.class)
                .callback(this)
                .queueUsing(getActivity());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Pokemon pokemon = (Pokemon)getListAdapter().getItem(position);
        if (listener.shouldAllowSelection(pokemon)) {
            pokemon.setSelected(!pokemon.isSelected());
            listener.onPartyMemberSelected(pokemon);
            SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)getListAdapter();
            PartyAdapter adapter = (PartyAdapter)listAdapter.getDecoratedBaseAdapter();
            adapter.notifyDataSetChanged();
        }
        else {
            Toast toast = Toast.makeText(getActivity(), "You can't analyze a party with more than three pókemon.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.party_list_fragment, container, false);
        return view;
    }

    @OnSuccess(BoxTask.class)
    public void onSuccess(@Param("Box") ArrayList<Pokemon> box) {
        ArrayList<Pokemon> filteredBox = new ArrayList<Pokemon>();
        for(Pokemon pokemon : box) {
            if (pokemon.moves().size() >= 4) {
                filteredBox.add(pokemon);
            }
        }
        SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)getListAdapter();
        listAdapter.setShouldAnimateFromPosition(0);
        PartyAdapter adapter = (PartyAdapter)listAdapter.getDecoratedBaseAdapter();
        adapter.addAll(filteredBox);
        adapter.notifyDataSetChanged();
    }

}
