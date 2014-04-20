package com.ruenzuo.pokeffective.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ruenzuo.pokeffective.models.AnalysisType;
import com.ruenzuo.pokeffective.models.Effective;
import com.ruenzuo.pokeffective.models.Effectiveness;
import com.ruenzuo.pokeffective.models.Move;
import com.ruenzuo.pokeffective.models.MoveCategory;
import com.ruenzuo.pokeffective.models.MoveLearnMethod;
import com.ruenzuo.pokeffective.models.PokedexType;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.models.PokemonType;

import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;

/**
 * Created by ruenzuo on 16/04/14.
 */
public class SQLiteHelper {

    private static final int FIRST_TYPE_SLOT = 1;
    private static final int SECOND_TYPE_SLOT = 2;

    private SQLiteDatabase SQLiteDatabase;
    private DatabaseOpenHelper databaseOpenHelper;

    public SQLiteHelper(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context);
    }

    public void open() throws SQLException {
        SQLiteDatabase = databaseOpenHelper.getReadableDatabase();
    }

    public void close() {
        databaseOpenHelper.close();
    }

    public ArrayList<Move> getMoves(Pokemon pokemon, MoveCategory category, MoveLearnMethod learnMethod, PokemonType moveType) {
        ArrayList<Move> moves = new ArrayList<Move>();
        boolean prevolutionSearch = pokemon.isEvolution() && (learnMethod == MoveLearnMethod.ALL || learnMethod == MoveLearnMethod.EGG);
        if (prevolutionSearch) {
            boolean found = false;
            String prevolutionQuery = null;
            int prevolutionIdentifier = 0;
            do {
                if (prevolutionIdentifier == 0) {
                    prevolutionQuery = QueryHelper.prevolutionQuery(pokemon.getIdentifier());
                }
                else {
                    prevolutionQuery = QueryHelper.prevolutionQuery(prevolutionIdentifier);
                }
                Cursor prevolutionCursor = SQLiteDatabase.rawQuery(prevolutionQuery, null);
                while (prevolutionCursor.moveToNext()) {
                    int result = prevolutionCursor.getInt(prevolutionCursor.getColumnIndex("prevolution"));
                    if (result != 0) {
                        prevolutionIdentifier = result;
                    }
                    else {
                        found = true;
                    }
                }
            } while (!found);
            Pokemon stubPokemon = new Pokemon();
            stubPokemon.setIdentifier(prevolutionIdentifier);
            String prevolutionMovesQuery = QueryHelper.movesSearchQuery(stubPokemon, moveType, learnMethod, category);
            Cursor prevolutionMovesCursor = SQLiteDatabase.rawQuery(prevolutionMovesQuery, null);
            while (prevolutionMovesCursor.moveToNext()) {
                Move move = TranslatorHelper.translateMove(prevolutionMovesCursor);
                moves.add(move);
            }
        }
        String movesQuery = QueryHelper.movesSearchQuery(pokemon, moveType, learnMethod, category);
        Cursor movesCursor = SQLiteDatabase.rawQuery(movesQuery, null);
        while (movesCursor.moveToNext()) {
            Move move = TranslatorHelper.translateMove(movesCursor);
            moves.add(move);
        }
        if (prevolutionSearch) {
            Hashtable movesTable = new Hashtable();
            for (Move move : moves) {
                movesTable.put(move.getName(), move);
            }
            moves.clear();
            moves.addAll(movesTable.values());
            Collections.sort(moves, new MovesComparator());
            return moves;
        }
        else {
            return moves;
        }
    }

    public ArrayList<Pokemon> getPokemons(PokedexType pokedexType, PokemonType pokemonType) {
        ArrayList<Pokemon> pokemons = new ArrayList<Pokemon>();
        if (pokemonType == PokemonType.NONE) {
            Hashtable pokemonTable = new Hashtable();
            String firstTypeQuery = QueryHelper.pokemonSearchQuery(pokedexType, pokemonType, FIRST_TYPE_SLOT);
            Cursor firstTypeCursor = SQLiteDatabase.rawQuery(firstTypeQuery, null);
            while (firstTypeCursor.moveToNext()) {
                Pokemon pokemon = TranslatorHelper.translatePokemon(firstTypeCursor);
                pokemonTable.put(pokemon.getName(), pokemon);
            }
            String secondTypeQuery = QueryHelper.pokemonSearchQuery(pokedexType, pokemonType, SECOND_TYPE_SLOT);
            Cursor secondTypeCursor = SQLiteDatabase.rawQuery(secondTypeQuery, null);
            while (secondTypeCursor.moveToNext()) {
                String name = secondTypeCursor.getString(secondTypeCursor.getColumnIndex("name"));
                Pokemon pokemon = (Pokemon) pokemonTable.get(StringUtils.capitalize(name));
                int secondType = secondTypeCursor.getInt(secondTypeCursor.getColumnIndex("type"));
                pokemon.setSecondType(PokemonType.values()[secondType]);
            }
            pokemons.addAll(pokemonTable.values());
            Collections.sort(pokemons, new PokemonComparator());
        }
        else {
            String firstTypeQuery = QueryHelper.pokemonSearchQuery(pokedexType, pokemonType, FIRST_TYPE_SLOT);
            Cursor firstTypeCursor = SQLiteDatabase.rawQuery(firstTypeQuery, null);
            while (firstTypeCursor.moveToNext()) {
                Pokemon pokemon = TranslatorHelper.translatePokemon(firstTypeCursor);
                pokemons.add(pokemon);
                String secondInnerTypeQuery = QueryHelper.pokemonTypeQuery(pokemon.getIdentifier(), SECOND_TYPE_SLOT);
                Cursor secondInnerTypeCursor = SQLiteDatabase.rawQuery(secondInnerTypeQuery, null);
                while (secondInnerTypeCursor.moveToNext()) {
                    pokemon.setSecondType(PokemonType.values()[secondInnerTypeCursor.getInt(secondInnerTypeCursor.getColumnIndex("type"))]);
                }
            }
            String secondTypeQuery = QueryHelper.pokemonSearchQuery(pokedexType, pokemonType, SECOND_TYPE_SLOT);
            Cursor secondTypeCursor = SQLiteDatabase.rawQuery(secondTypeQuery, null);
            while (secondTypeCursor.moveToNext()) {
                Pokemon pokemon = TranslatorHelper.translatePokemon(secondTypeCursor);
                pokemons.add(pokemon);
                String firstInnerTypeQuery = QueryHelper.pokemonTypeQuery(pokemon.getIdentifier(), FIRST_TYPE_SLOT);
                Cursor firstInnerTypeCursor = SQLiteDatabase.rawQuery(firstInnerTypeQuery, null);
                while (firstInnerTypeCursor.moveToNext()) {
                    pokemon.setSecondType(pokemon.getFirstType());
                    pokemon.setFirstType(PokemonType.values()[firstInnerTypeCursor.getInt(firstInnerTypeCursor.getColumnIndex("type"))]);
                }
            }
            Collections.sort(pokemons, new PokemonComparator());
        }
        return pokemons;
    }

    public Hashtable efficacy(AnalysisType analysisType) {
        Hashtable efficacy = new Hashtable();
        String efficacyQuery = QueryHelper.efficacyQuery();
        Cursor efficacyCursor = SQLiteDatabase.rawQuery(efficacyQuery, null);
        while (efficacyCursor.moveToNext()) {
            PokemonType damager = PokemonType.values()[efficacyCursor.getInt(efficacyCursor.getColumnIndex("damager"))];
            PokemonType target = PokemonType.values()[efficacyCursor.getInt(efficacyCursor.getColumnIndex("target"))];
            Effectiveness effectiveness = Effectiveness.fromIntValue(efficacyCursor.getInt(efficacyCursor.getColumnIndex("factor")));
            if (analysisType == AnalysisType.ATTACK) {
                ArrayList<Effectiveness> type = (ArrayList<Effectiveness>) efficacy.get(target);
                if (type == null) {
                    type = new ArrayList<Effectiveness>();
                    for (int i = 0; i < PokemonType.values().length - 1; i++) {
                        type.add(null);
                    }
                }
                type.set(damager.ordinal() - 1, effectiveness);
                efficacy.put(target, type);
            }
            else {
                ArrayList<Effectiveness> type = (ArrayList<Effectiveness>) efficacy.get(damager);
                if (type == null) {
                    type = new ArrayList<Effectiveness>();
                    for (int i = 0; i < PokemonType.values().length - 1; i++) {
                        type.add(null);
                    }
                }
                type.set(target.ordinal() - 1, effectiveness);
                efficacy.put(damager, type);
            }
        }
        return efficacy;
    }

    private class PokemonComparator implements Comparator<Pokemon> {

        @Override
        public int compare(Pokemon o1, Pokemon o2) {
            return o1.getPokedexNumber() - o2.getPokedexNumber();
        }

    }

    private class MovesComparator implements Comparator<Move> {

        @Override
        public int compare(Move o1, Move o2) {
            return o1.getName().compareTo(o2.getName());
        }

    }
}
