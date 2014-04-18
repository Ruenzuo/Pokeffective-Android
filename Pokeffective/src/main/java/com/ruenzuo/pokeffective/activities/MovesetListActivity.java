package com.ruenzuo.pokeffective.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.fragments.MovesetListFragment;
import com.ruenzuo.pokeffective.models.Pokemon;

/**
 * Created by ruenzuo on 18/04/14.
 */
public class MovesetListActivity extends Activity {

    private static final int MOVE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moveset_list_activity);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Pokemon pokemon = (Pokemon) extras.getSerializable("Pokemon");
            getActionBar().setTitle(pokemon.getName());
            MovesetListFragment fragment = MovesetListFragment.newInstance(pokemon);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.movesetListFragmentPlaceholder, fragment);
            transaction.commit();
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
            finish();
            return true;
        } else if (id == R.id.action_moveset_add) {
            Intent intent = new Intent(getApplicationContext(), MoveListActivity.class);
            intent.putExtra("Pokemon", getIntent().getExtras().getSerializable("Pokemon"));
            startActivityForResult(intent, MOVE_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
