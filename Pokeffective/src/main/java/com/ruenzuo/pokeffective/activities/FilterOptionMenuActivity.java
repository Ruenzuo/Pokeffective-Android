package com.ruenzuo.pokeffective.activities;

import android.app.Activity;
import android.os.Bundle;

import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.definitions.OnFilterOptionChangedListener;

/**
 * Created by ruenzuo on 17/04/14.
 */
public class FilterOptionMenuActivity extends Activity implements OnFilterOptionChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_menu_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
