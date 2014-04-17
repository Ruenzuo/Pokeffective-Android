package com.ruenzuo.pokeffective.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.adapters.BoxAdapter;
import com.ruenzuo.pokeffective.adapters.PokemonAdapter;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.tasks.BoxTask;
import com.ruenzuo.pokeffective.tasks.PokemonTask;
import com.telly.groundy.Groundy;
import com.telly.groundy.annotations.OnSuccess;
import com.telly.groundy.annotations.Param;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ruenzuo on 17/04/14.
 */
public class BoxActivity extends Activity {

    private GridView gridView;
    private static final int POKEMON_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.box_activity);
        getActionBar().setTitle("Box");
        gridView = (GridView) findViewById(R.id.gridView);
        BoxAdapter adapter = new BoxAdapter(this, R.layout.member_item, new ArrayList<Pokemon>());
        SwingBottomInAnimationAdapter swingRightInAnimationAdapter = new SwingBottomInAnimationAdapter(adapter);
        swingRightInAnimationAdapter.setAbsListView(gridView);
        gridView.setAdapter(swingRightInAnimationAdapter);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return true;
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
        if (id == R.id.action_add) {
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

}
