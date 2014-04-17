package com.ruenzuo.pokeffective.tasks;

import com.ruenzuo.pokeffective.helpers.SQLiteHelper;
import com.ruenzuo.pokeffective.models.PokedexType;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.models.PokemonType;
import com.telly.groundy.GroundyTask;
import com.telly.groundy.TaskResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruenzuo on 17/04/14.
 */
public class BoxTask extends GroundyTask {

    @Override
    protected TaskResult doInBackground() {
        List<Pokemon> pokemons = Pokemon.getAll();
        ArrayList<Pokemon> box = new ArrayList<Pokemon>(pokemons);
        return succeeded().add("Box", box);
    }

}
