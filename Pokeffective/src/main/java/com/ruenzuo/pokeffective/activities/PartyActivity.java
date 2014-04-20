package com.ruenzuo.pokeffective.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.base.BaseActivity;
import com.ruenzuo.pokeffective.definitions.OnPartyMemberSelectedListener;
import com.ruenzuo.pokeffective.models.Pokemon;

/**
 * Created by ruenzuo on 19/04/14.
 */
public class PartyActivity extends BaseActivity implements OnPartyMemberSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onPartyMemberSelected(Pokemon member) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finishAnimated();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
