package com.ruenzuo.pokeffective.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.adapters.BoxAdapter;
import com.ruenzuo.pokeffective.definitions.OnConfirmListener;
import com.ruenzuo.pokeffective.fragments.ConfirmDialogFragment;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.tasks.BoxTask;
import com.telly.groundy.Groundy;
import com.telly.groundy.annotations.OnSuccess;
import com.telly.groundy.annotations.Param;

import java.util.ArrayList;

/**
 * Created by ruenzuo on 17/04/14.
 */
public class BoxActivity extends Activity implements OnConfirmListener {

    private GridView gridView;
    private static final int POKEMON_REQUEST_CODE = 1;
    private static final int INVALID_POSITION = -1;
    private int lastHoldPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lastHoldPosition = INVALID_POSITION;
        setContentView(R.layout.box_activity);
        getActionBar().setTitle("Box");
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setEmptyView(findViewById(R.id.txtViewNoBox));
        BoxAdapter adapter = new BoxAdapter(this, R.layout.member_item, new ArrayList<Pokemon>());
        SwingBottomInAnimationAdapter swingRightInAnimationAdapter = new SwingBottomInAnimationAdapter(adapter);
        swingRightInAnimationAdapter.setAbsListView(gridView);
        gridView.setAdapter(swingRightInAnimationAdapter);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                lastHoldPosition = position;
                SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter) gridView.getAdapter();
                BoxAdapter adapter = (BoxAdapter) listAdapter.getDecoratedBaseAdapter();
                Pokemon pokemon = adapter.get(position);
                ConfirmDialogFragment dialog = ConfirmDialogFragment.newInstance("Remove " + pokemon.getName() + " from box?");
                dialog.show(getFragmentManager(), "ConfirmDialogFragment");
                return true;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter) gridView.getAdapter();
                BoxAdapter adapter = (BoxAdapter) listAdapter.getDecoratedBaseAdapter();
                Pokemon pokemon = adapter.get(position);
                Intent intent = new Intent(getApplicationContext(), MovesetListActivity.class);
                intent.putExtra("Pokemon", pokemon);
                startActivity(intent);
            }
        });
        Groundy.create(BoxTask.class)
                .callback(this)
                .queueUsing(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.box_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_box_add) {
            Intent intent = new Intent(getApplicationContext(), PokemonListActivity.class);
            startActivityForResult(intent, POKEMON_REQUEST_CODE);
            return true;
        }
        else if (id == R.id.action_balance) {
            return true;
        }
        else if (id == R.id.action_info) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == POKEMON_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Pokemon pokemon = (Pokemon)data.getExtras().get("Pokemon");
                SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)gridView.getAdapter();
                BoxAdapter adapter = (BoxAdapter)listAdapter.getDecoratedBaseAdapter();
                int count = adapter.getCount();
                for(int i = 0; i < count; i++) {
                    Pokemon stored = adapter.get(i);
                    if (stored.getIdentifier() == pokemon.getIdentifier()) {
                        Toast toast = Toast.makeText(this, "You can't save the same pokémon twice in your box.", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                }
                pokemon.save();
                ArrayList<Pokemon> pokemons = new ArrayList<Pokemon>();
                pokemons.add(pokemon);
                addToAdapter(pokemons, false);
            }
        }
    }

    @OnSuccess(BoxTask.class)
    public void onSuccess(@Param("Box") ArrayList<Pokemon> box) {
        addToAdapter(box, true);
    }

    private void addToAdapter(ArrayList<Pokemon> pokemons, boolean shouldClear) {
        SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)gridView.getAdapter();
        BoxAdapter adapter = (BoxAdapter)listAdapter.getDecoratedBaseAdapter();
        if (shouldClear) {
            adapter.clear();
            listAdapter.setShouldAnimateFromPosition(0);
        }
        else {
            listAdapter.setShouldAnimateFromPosition(adapter.getCount());
        }
        adapter.addAll(pokemons);
        adapter.notifyDataSetChanged();
        if (shouldClear) {
            gridView.setSelection(0);
        }
        else {
            gridView.smoothScrollToPosition(adapter.getCount());
        }
    }

    @Override
    public void onConfirm(boolean confirmed) {
        if (confirmed && lastHoldPosition != INVALID_POSITION) {
            SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)gridView.getAdapter();
            BoxAdapter adapter = (BoxAdapter)listAdapter.getDecoratedBaseAdapter();
            Pokemon pokemon = adapter.get(lastHoldPosition);
            pokemon.delete();
            adapter.remove(lastHoldPosition);
            listAdapter.setShouldAnimateFromPosition(lastHoldPosition);
            adapter.notifyDataSetChanged();
            gridView.smoothScrollToPosition(lastHoldPosition);
            lastHoldPosition = INVALID_POSITION;
        }
    }

}
