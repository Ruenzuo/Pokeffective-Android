package com.ruenzuo.pokeffective.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.adapters.MoveAdapter;
import com.ruenzuo.pokeffective.definitions.OnMoveFilterChangedListener;
import com.ruenzuo.pokeffective.definitions.OnMoveListSearchListener;
import com.ruenzuo.pokeffective.definitions.OnMoveSelectedListener;
import com.ruenzuo.pokeffective.definitions.OnPokemonSelectedListener;
import com.ruenzuo.pokeffective.models.Move;
import com.ruenzuo.pokeffective.models.MoveCategory;
import com.ruenzuo.pokeffective.models.MoveLearnMethod;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.models.PokemonType;
import com.ruenzuo.pokeffective.tasks.MoveTask;
import com.telly.groundy.Groundy;
import com.telly.groundy.annotations.OnSuccess;
import com.telly.groundy.annotations.Param;

import java.util.ArrayList;

/**
 * Created by ruenzuo on 18/04/14.
 */
public class MoveListFragment extends ListFragment implements OnMoveListSearchListener, OnMoveFilterChangedListener {

    private OnMoveSelectedListener listener;

    private void startMoveTask(MoveCategory category, MoveLearnMethod learnMethod, PokemonType moveType) {
        Groundy.create(MoveTask.class)
                .arg("Pokemon", getArguments().getSerializable("Pokemon"))
                .arg("MoveCategory", category)
                .arg("PokemonType", moveType)
                .arg("MoveLearnMethod", learnMethod)
                .callback(this)
                .queueUsing(getActivity());
    }

    public static MoveListFragment newInstance(Pokemon pokemon) {
        MoveListFragment fragment = new MoveListFragment();
        Bundle args = new Bundle();
        args.putSerializable("Pokemon", pokemon);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnMoveSelectedListener) {
            listener = (OnMoveSelectedListener)activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMoveSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MoveAdapter adapter = new MoveAdapter(getActivity(), R.layout.move_row, new ArrayList<Move>());
        SwingBottomInAnimationAdapter swingRightInAnimationAdapter = new SwingBottomInAnimationAdapter(adapter);
        swingRightInAnimationAdapter.setAbsListView(getListView());
        setListAdapter(swingRightInAnimationAdapter);
        startMoveTask(MoveCategory.ALL, MoveLearnMethod.ALL, PokemonType.NONE);
        getListView().setEmptyView(getActivity().findViewById(R.id.txtViewNoMoveResults));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.move_list_fragment, container, false);
        return view;
    }

    @OnSuccess(MoveTask.class)
    public void onSuccess(@Param("Moves") ArrayList<Move> moves) {
        SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)getListAdapter();
        listAdapter.setShouldAnimateFromPosition(0);
        MoveAdapter adapter = (MoveAdapter)listAdapter.getDecoratedBaseAdapter();
        adapter.clear();
        adapter.addAllCopying(moves);
        adapter.notifyDataSetChanged();
        getListView().setSelection(0);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Move move = (Move)getListAdapter().getItem(position);
        listener.onMoveSelected(move);
    }

    @Override
    public void onSearchQueryChange(String query) {
        SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)getListAdapter();
        MoveAdapter adapter = (MoveAdapter)listAdapter.getDecoratedBaseAdapter();
        adapter.getFilter().filter(query);
    }

    @Override
    public void onSearchStart() {
        SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)getListAdapter();
        listAdapter.setShouldAnimateFromPosition(0);
        MoveAdapter adapter = (MoveAdapter)listAdapter.getDecoratedBaseAdapter();
        adapter.clear();
        adapter.setSearching(true);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchCancel() {
        SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)getListAdapter();
        listAdapter.setShouldAnimateFromPosition(0);
        MoveAdapter adapter = (MoveAdapter)listAdapter.getDecoratedBaseAdapter();
        adapter.setSearching(false);
        adapter.restoreCopy();
    }

    @Override
    public void onMoveFilterChanged(MoveCategory category, MoveLearnMethod learnMethod, PokemonType moveType) {
        startMoveTask(category, learnMethod, moveType);
    }
}
