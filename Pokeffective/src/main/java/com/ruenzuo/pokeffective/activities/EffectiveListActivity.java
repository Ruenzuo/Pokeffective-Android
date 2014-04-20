package com.ruenzuo.pokeffective.activities;

import android.os.Bundle;
import android.widget.ListView;

import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;
import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.adapters.EffectiveAdapter;
import com.ruenzuo.pokeffective.base.BaseListActivity;
import com.ruenzuo.pokeffective.models.AnalysisType;
import com.ruenzuo.pokeffective.models.Effective;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.tasks.BoxTask;
import com.ruenzuo.pokeffective.tasks.EffectiveTask;
import com.telly.groundy.Groundy;
import com.telly.groundy.annotations.OnSuccess;
import com.telly.groundy.annotations.Param;

import java.util.ArrayList;

/**
 * Created by ruenzuo on 20/04/14.
 */
public class EffectiveListActivity extends BaseListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.effective_list_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ArrayList<Pokemon> party = (ArrayList<Pokemon>) extras.getSerializable("Party");
            AnalysisType analysisType = (AnalysisType) extras.getSerializable("AnalysisType");
            EffectiveAdapter adapter = new EffectiveAdapter(this, R.layout.effective_row, new ArrayList<Effective>());
            SwingBottomInAnimationAdapter swingRightInAnimationAdapter = new SwingBottomInAnimationAdapter(adapter);
            swingRightInAnimationAdapter.setAbsListView(getListView());
            setListAdapter(swingRightInAnimationAdapter);
            Groundy.create(EffectiveTask.class)
                    .arg("Party", party)
                    .arg("AnalysisType", analysisType)
                    .callback(this)
                    .queueUsing(this);

        }
    }

    @OnSuccess(EffectiveTask.class)
    public void onSuccess(@Param("Pokeffective") ArrayList<Effective> pokeffective) {
        SwingBottomInAnimationAdapter listAdapter = (SwingBottomInAnimationAdapter)getListAdapter();
        listAdapter.setShouldAnimateFromPosition(0);
        EffectiveAdapter adapter = (EffectiveAdapter)listAdapter.getDecoratedBaseAdapter();
        adapter.addAll(pokeffective);
        adapter.notifyDataSetChanged();
        getListView().setSelection(0);
    }

}
