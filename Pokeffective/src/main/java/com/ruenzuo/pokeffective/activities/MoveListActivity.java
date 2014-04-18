package com.ruenzuo.pokeffective.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.definitions.OnMoveSelectedListener;
import com.ruenzuo.pokeffective.fragments.MoveListFragment;
import com.ruenzuo.pokeffective.fragments.MovesetListFragment;
import com.ruenzuo.pokeffective.models.Move;
import com.ruenzuo.pokeffective.models.Pokemon;

/**
 * Created by ruenzuo on 18/04/14.
 */
public class MoveListActivity extends Activity implements OnMoveSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.move_list_activity);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Pokemon pokemon = (Pokemon) extras.getSerializable("Pokemon");
            getActionBar().setTitle(pokemon.getName());
            MoveListFragment fragment = MoveListFragment.newInstance(pokemon);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.moveListFragmentPlaceholder, fragment);
            transaction.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.move_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_move_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMoveSelected(Move move) {

    }
}
