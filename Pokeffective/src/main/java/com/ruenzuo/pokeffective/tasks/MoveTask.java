package com.ruenzuo.pokeffective.tasks;

import com.ruenzuo.pokeffective.helpers.SQLiteHelper;
import com.ruenzuo.pokeffective.models.Move;
import com.ruenzuo.pokeffective.models.MoveCategory;
import com.ruenzuo.pokeffective.models.MoveLearnMethod;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.models.PokemonType;
import com.telly.groundy.GroundyTask;
import com.telly.groundy.TaskResult;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ruenzuo on 18/04/14.
 */
public class MoveTask extends GroundyTask {

    @Override
    protected TaskResult doInBackground() {
        Pokemon pokemon = (Pokemon) getArgs().getSerializable("Pokemon");
        MoveCategory moveCategory = (MoveCategory) getArgs().getSerializable("MoveCategory");
        MoveLearnMethod moveLearnMethod = (MoveLearnMethod) getArgs().getSerializable("MoveLearnMethod");
        PokemonType moveType = (PokemonType) getArgs().getSerializable("PokemonType");
        SQLiteHelper sqliteHelper = new SQLiteHelper(getContext());
        try {
            sqliteHelper.open();
        } catch (SQLException e) {
            return failed();
        }
        ArrayList<Move> moves = sqliteHelper.getMoves(pokemon, moveCategory, moveLearnMethod, moveType);
        sqliteHelper.close();
        return succeeded().add("Moves", moves);
    }

}
