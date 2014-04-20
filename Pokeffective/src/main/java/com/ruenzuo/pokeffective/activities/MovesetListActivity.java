package com.ruenzuo.pokeffective.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.adapters.MovesetAdapter;
import com.ruenzuo.pokeffective.base.BaseListActivity;
import com.ruenzuo.pokeffective.definitions.OnChoiceSelectedListener;
import com.ruenzuo.pokeffective.fragments.ChoiceDialogFragment;
import com.ruenzuo.pokeffective.models.Move;
import com.ruenzuo.pokeffective.models.Pokemon;

import java.util.ArrayList;

/**
 * Created by ruenzuo on 18/04/14.
 */
public class MovesetListActivity extends BaseListActivity implements OnChoiceSelectedListener {

    private static final int MOVE_REQUEST_CODE = 1;
    private static final int INVALID_POSITION = -1;
    private int lastHoldPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lastHoldPosition = INVALID_POSITION;
        setContentView(R.layout.moveset_list_activity);
        getActionBar().setIcon(getResources().getDrawable(R.drawable.ic_action_back));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Pokemon pokemon = (Pokemon) extras.getSerializable("Pokemon");
            Pokemon stored = Pokemon.getStored(pokemon);
            getActionBar().setTitle(pokemon.getName());
            MovesetAdapter adapter = new MovesetAdapter(this, R.layout.move_row, new ArrayList<Move>(stored.moves()));
            SwingBottomInAnimationAdapter swingRightInAnimationAdapter = new SwingBottomInAnimationAdapter(adapter);
            swingRightInAnimationAdapter.setAbsListView(getListView());
            getListView().setAdapter(swingRightInAnimationAdapter);
            getListView().setEmptyView(findViewById(R.id.txtViewNoMovesetResults));
            getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    lastHoldPosition = position;
                    SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter) getListView().getAdapter();
                    MovesetAdapter adapter = (MovesetAdapter) listAdapter.getDecoratedBaseAdapter();
                    Pokemon pokemon = (Pokemon) getIntent().getExtras().getSerializable("Pokemon");
                    Move move = adapter.get(position);
                    String[] choices = {"OK", "Cancel"};
                    ChoiceDialogFragment dialog = ChoiceDialogFragment.newInstance("Confirm", "Remove " + move.getName() + " from " + pokemon.getName() + "?", choices);
                    dialog.show(getFragmentManager(), "ChoiceDialogFragment");
                    return true;
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.moveset_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finishAnimated();
            return true;
        } else if (id == R.id.action_moveset_add) {
            Intent intent = new Intent(getApplicationContext(), MoveListActivity.class);
            intent.putExtra("Pokemon", getIntent().getExtras().getSerializable("Pokemon"));
            startActivityForResult(intent, MOVE_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MOVE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Move move = (Move)data.getExtras().get("Move");
                SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)getListView().getAdapter();
                MovesetAdapter adapter = (MovesetAdapter)listAdapter.getDecoratedBaseAdapter();
                int count = adapter.getCount();
                if (count >= 4) {
                    Toast toast = Toast.makeText(this, "You can't save more than four moves for a pokémon in your box. Remove one first in order to add another.", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                for(int i = 0; i < count; i++) {
                    Move stored = adapter.get(i);
                    if (stored.getName().equalsIgnoreCase(move.getName())) {
                        Toast toast = Toast.makeText(this, "You can't save the same move twice for a pókemon in your box.", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                }
                Pokemon pokemon = (Pokemon) getIntent().getExtras().getSerializable("Pokemon");
                Pokemon stored = Pokemon.getStored(pokemon);
                move.setPokemon(stored);
                move.save();
                ArrayList<Move> moves = new ArrayList<Move>();
                moves.add(move);
                addToAdapter(moves, false);
            }
        }
    }

    private void addToAdapter(ArrayList<Move> moves, boolean shouldClear) {
        SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)getListView().getAdapter();
        MovesetAdapter adapter = (MovesetAdapter)listAdapter.getDecoratedBaseAdapter();
        if (shouldClear) {
            adapter.clear();
            listAdapter.setShouldAnimateFromPosition(0);
        }
        else {
            listAdapter.setShouldAnimateFromPosition(adapter.getCount());
        }
        adapter.addAll(moves);
        adapter.notifyDataSetChanged();
        if (shouldClear) {
            getListView().setSelection(0);
        }
        else {
            getListView().smoothScrollToPosition(adapter.getCount());
        }
    }

    @Override
    public void onChoiceSelected(boolean selected) {
        if (selected && lastHoldPosition != INVALID_POSITION) {
            SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter) getListView().getAdapter();
            MovesetAdapter adapter = (MovesetAdapter)listAdapter.getDecoratedBaseAdapter();
            Move move = adapter.get(lastHoldPosition);
            move.delete();
            adapter.remove(lastHoldPosition);
            listAdapter.setShouldAnimateFromPosition(lastHoldPosition);
            adapter.notifyDataSetChanged();
            getListView().smoothScrollToPosition(lastHoldPosition);
            lastHoldPosition = INVALID_POSITION;
        }
    }

}
