package com.ruenzuo.pokeffective.helpers;

import android.database.Cursor;

import com.ruenzuo.pokeffective.models.Move;
import com.ruenzuo.pokeffective.models.MoveCategory;
import com.ruenzuo.pokeffective.models.MoveLearnMethod;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.models.PokemonType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;

/**
 * Created by ruenzuo on 16/04/14.
 */
public class TranslatorHelper {

    public static Pokemon translatePokemon(Cursor cursor) {
        Pokemon.PokemonBuilder builder = new Pokemon.PokemonBuilder(cursor.getInt(cursor.getColumnIndex("identifier")),
                StringUtils.capitalize(cursor.getString(cursor.getColumnIndex("name"))))
                .pokedexNumber(cursor.getInt(cursor.getColumnIndex("number")))
                .firstType(PokemonType.values()[cursor.getInt(cursor.getColumnIndex("type"))]);
        if (cursor.getInt(cursor.getColumnIndex("evolves"))!= 0) {
            builder.evolution(true);
        }
        else {
            builder.evolution(false);
        }
        return builder.build();
    }

    public static Move translateMove(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex("name"));
        name = name.replace("-", " ");
        return new Move.MoveBuilder(WordUtils.capitalize(name))
                .pokemonType(PokemonType.values()[cursor.getInt(cursor.getColumnIndex("type"))])
                .category(MoveCategory.values()[cursor.getInt(cursor.getColumnIndex("category"))])
                .power(cursor.getInt(cursor.getColumnIndex("power")))
                .accuracy(cursor.getInt(cursor.getColumnIndex("accuracy")))
                .build();
    }

}
