package com.ruenzuo.pokeffective.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ruenzuo.pokeffective.R;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.tasks.SQLiteTask;
import com.telly.groundy.Groundy;
import com.telly.groundy.annotations.OnSuccess;
import com.telly.groundy.annotations.Param;

import java.util.ArrayList;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Groundy.create(SQLiteTask.class).callback(this).queueUsing(this);
    }

    @OnSuccess(SQLiteTask.class)
    public void onSuccess(@Param("Pokemons") ArrayList<Pokemon> pokemons) {

    }

}
