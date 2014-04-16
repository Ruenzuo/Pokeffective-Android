package com.ruenzuo.pokeffective.helpers;

import android.database.Cursor;

import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.models.PokemonType;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by ruenzuo on 16/04/14.
 */
public class TranslatorHelper {

    public static Pokemon translatePokemon(Cursor cursor)
    {
        return new Pokemon.PokemonBuilder(cursor.getInt(cursor.getColumnIndex("identifier")),
                StringUtils.capitalize(cursor.getString(cursor.getColumnIndex("name"))))
                .pokedexNumber(cursor.getInt(cursor.getColumnIndex("number")))
                .firstType(PokemonType.values()[cursor.getInt(cursor.getColumnIndex("type"))])
                .build();
    }

}
