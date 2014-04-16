package com.ruenzuo.pokeffective.helpers;

import android.database.Cursor;

import com.ruenzuo.pokeffective.models.Pokemon;

/**
 * Created by ruenzuo on 16/04/14.
 */
public class TranslatorHelper {

    public static Pokemon translatePokemon(Cursor cursor)
    {
        return new Pokemon.PokemonBuilder(cursor.getInt(cursor.getColumnIndex("identifier")),
                cursor.getString(cursor.getColumnIndex("name"))).build();
    }

}
