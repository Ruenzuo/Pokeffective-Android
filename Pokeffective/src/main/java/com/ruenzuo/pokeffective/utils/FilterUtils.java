package com.ruenzuo.pokeffective.utils;

import com.ruenzuo.pokeffective.models.FilterOption;
import com.ruenzuo.pokeffective.models.FilterType;
import com.ruenzuo.pokeffective.models.MoveCategory;
import com.ruenzuo.pokeffective.models.MoveLearnMethod;
import com.ruenzuo.pokeffective.models.PokedexType;
import com.ruenzuo.pokeffective.models.PokemonType;

/**
 * Created by ruenzuo on 17/04/14.
 */
public class FilterUtils {

    public static CharSequence[] getFilterOptions(FilterType type) {
        switch (type) {
            case POKEMON_TYPE:
                return PokemonType.pokemonTypes();
            case POKEDEX_TYPE:
                return PokedexType.pokedexTypes();
            case MOVE_CATEGORY:
                return MoveCategory.moveCategories();
            case MOVE_LEARN_METHOD:
                return MoveLearnMethod.moveLearnMethods();
            case MOVE_TYPE:
                return PokemonType.pokemonTypes();
            default:
                return new CharSequence[0];
        }
    }

    private static Object[] getFilterValues(FilterType type) {
        switch (type) {
            case POKEMON_TYPE:
                return PokemonType.values();
            case POKEDEX_TYPE:
                return PokedexType.values();
            case MOVE_CATEGORY:
                return MoveCategory.values();
            case MOVE_LEARN_METHOD:
                return MoveLearnMethod.values();
            case MOVE_TYPE:
                return PokemonType.values();
            default:
                return new Object[0];
        }
    }

    public static int getIndex(FilterOption option) {
        CharSequence[] options =  getFilterOptions(option.getFilterType());
        for (int i = 0; i < options.length; i++) {
            if (option.getValue().toString().equalsIgnoreCase(options[i].toString())) {
                return i;
            }
        }
        return -1;
    }

    public static Object getValue(FilterType type, int selection) {
        CharSequence[] options =  getFilterOptions(type);
        CharSequence option = options[selection];
        for (Object value : getFilterValues(type)) {
            if (option.toString().equalsIgnoreCase(value.toString())) {
                return value;
            }
        }
        return null;
    }

}
