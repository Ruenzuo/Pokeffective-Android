package com.ruenzuo.pokeffective.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public class PokemonComparator implements Comparator<Pokemon> {

        @Override
        public int compare(Pokemon o1, Pokemon o2) {
            return o1.getPokedexNumber() - o2.getPokedexNumber();
        }

    }

}
