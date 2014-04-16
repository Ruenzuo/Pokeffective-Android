package com.ruenzuo.pokeffective.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ruenzuo.pokeffective.models.PokedexType;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.models.PokemonType;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by ruenzuo on 16/04/14.
 */
public class SQLiteHelper {

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

    public ArrayList<Pokemon> getPokemons(PokedexType pokedexType, PokemonType pokemonType, int typeSlot) {
        String query = QueryHelper.pokemonSearchQuery(pokedexType, pokemonType, typeSlot);
        Cursor cursor = SQLiteDatabase.rawQuery(query, null);
        ArrayList<Pokemon> pokemons = new ArrayList<Pokemon>();
        while (cursor.moveToNext()) {
            Pokemon pokemon = TranslatorHelper.translatePokemon(cursor);
            pokemons.add(pokemon);
        }
        return pokemons;
    }

}
