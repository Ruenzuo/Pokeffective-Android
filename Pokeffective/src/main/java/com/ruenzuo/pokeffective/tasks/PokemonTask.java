package com.ruenzuo.pokeffective.tasks;

import com.ruenzuo.pokeffective.helpers.SQLiteHelper;
import com.ruenzuo.pokeffective.models.PokedexType;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.models.PokemonType;
import com.telly.groundy.GroundyTask;
import com.telly.groundy.TaskResult;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ruenzuo on 16/04/14.
 */
public class PokemonTask extends GroundyTask {

    @Override
    protected TaskResult doInBackground() {
        PokedexType pokedexType = (PokedexType) getArgs().getSerializable("PokedexType");
        PokemonType pokemonType = (PokemonType) getArgs().getSerializable("PokemonType");
        SQLiteHelper sqliteHelper = new SQLiteHelper(getContext());
        try {
            sqliteHelper.open();
        } catch (SQLException e) {
            return failed();
        }
        ArrayList<Pokemon> pokemons = sqliteHelper.getPokemons(pokedexType, pokemonType);
        return succeeded().add("Pokemons", pokemons);
    }

}
