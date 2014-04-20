package com.ruenzuo.pokeffective.base;

import android.app.ListActivity;
import android.os.Bundle;

import com.ruenzuo.pokeffective.R;

/**
 * Created by ruenzuo on 19/04/14.
 */
public class BaseListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
    }

    protected void finishAnimated() {
        finish();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    public void onBackPressed() {
        finishAnimated();
    }

}
