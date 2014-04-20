package com.ruenzuo.pokeffective.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.adapters.STABAdapter;
import com.ruenzuo.pokeffective.base.BaseListActivity;
import com.ruenzuo.pokeffective.models.STAB;

import java.util.ArrayList;

/**
 * Created by ruenzuo on 21/04/14.
 */
public class STABListActivity extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.effective_list_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ArrayList<STAB> STABs = (ArrayList<STAB>) extras.getSerializable("STABs");
            STABAdapter adapter = new STABAdapter(this, R.layout.stab_row, STABs);
            SwingBottomInAnimationAdapter swingRightInAnimationAdapter = new SwingBottomInAnimationAdapter(adapter);
            swingRightInAnimationAdapter.setAbsListView(getListView());
            setListAdapter(swingRightInAnimationAdapter);
        }
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
