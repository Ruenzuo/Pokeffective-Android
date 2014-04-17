package com.ruenzuo.pokeffective.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ruenzuo.pokeffective.R;

/**
 * Created by ruenzuo on 17/04/14.
 */
public class BoxActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.box_activity);
        getActionBar().setTitle("Box");
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
            startActivity(intent);
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

}
