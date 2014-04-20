package com.ruenzuo.pokeffective.tasks;

import com.ruenzuo.pokeffective.helpers.SQLiteHelper;
import com.ruenzuo.pokeffective.models.AnalysisType;
import com.ruenzuo.pokeffective.models.Effective;
import com.ruenzuo.pokeffective.models.Effectiveness;
import com.ruenzuo.pokeffective.models.Move;
import com.ruenzuo.pokeffective.models.MoveCategory;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.models.PokemonType;
import com.ruenzuo.pokeffective.models.STAB;
import com.telly.groundy.GroundyTask;
import com.telly.groundy.TaskResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by ruenzuo on 20/04/14.
 */
public class EffectiveTask extends GroundyTask {

    @Override
    protected TaskResult doInBackground() {
        AnalysisType analysisType = (AnalysisType) getArgs().getSerializable("AnalysisType");
        ArrayList<Pokemon> party = (ArrayList<Pokemon>) getArgs().getSerializable("Party");
        SQLiteHelper sqliteHelper = new SQLiteHelper(getContext());
        try {
            sqliteHelper.open();
        } catch (SQLException e) {
            return failed();
        }
        Hashtable efficacy = sqliteHelper.efficacy(analysisType);
        sqliteHelper.close();
        ArrayList<Effective> pokeffective = new ArrayList<Effective>();
        int startValue = PokemonType.NORMAL.ordinal();
        for (int i = startValue; i < PokemonType.values().length; i++) {
            PokemonType pokemonTypeTarget = PokemonType.values()[i];
            ArrayList<Effectiveness> typeEfficacy = (ArrayList<Effectiveness>) efficacy.get(pokemonTypeTarget);
            Effectiveness effectiveness = Effectiveness.NO_EFFECT;
            ArrayList<STAB> STABs = new ArrayList<STAB>();
            for (Pokemon pokemon : party) {
                Pokemon stub = Pokemon.getStored(pokemon);
                for (Move move : stub.moves()) {
                    Effectiveness comparison = typeEfficacy.get(move.getPokemonType().ordinal() - 1);
                    if (comparison.toInt() > effectiveness.toInt()) {
                        effectiveness = comparison;
                    }
                    if (comparison == Effectiveness.SUPER_EFFECTIVE) {
                        if (move.getPokemonType() == pokemon.getFirstType() || move.getPokemonType() == pokemon.getSecondType()) {
                            if (move.getCategory() != MoveCategory.NON_DAMAGING) {
                                STAB anSTAB = new STAB.STABBuilder(pokemon, move).build();
                                STABs.add(anSTAB);
                            }
                        }
                    }
                }
            }
            Effective.EffectiveBuilder builder = new Effective.EffectiveBuilder(pokemonTypeTarget, effectiveness);
            if (analysisType == AnalysisType.ATTACK) {
                builder.STABs(STABs).build();
            }
            Effective effective = builder.build();
            pokeffective.add(effective);
        }
        return succeeded().add("Pokeffective", pokeffective);
    }

}
