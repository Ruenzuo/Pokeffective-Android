package com.ruenzuo.pokeffective.helpers;

import com.ruenzuo.pokeffective.models.MoveCategory;
import com.ruenzuo.pokeffective.models.MoveLearnMethod;
import com.ruenzuo.pokeffective.models.PokedexType;
import com.ruenzuo.pokeffective.models.Pokemon;
import com.ruenzuo.pokeffective.models.PokemonType;

/**
 * Created by ruenzuo on 16/04/14.
 */
public class QueryHelper {

    public static String pokemonSearchQuery(PokedexType pokedexType, PokemonType pokemonType, int typeSlot) {
        if (pokemonType != PokemonType.NONE) {
            return "select po.id as identifier, " +
            "  ps.identifier as name, " +
            "  pdn.pokedex_number as number, " +
            "  pt.type_id as type, " +
            "  ps.evolves_from_species_id as evolves " +
            "from pokemon_species as ps " +
            "  join pokemon_dex_numbers as pdn " +
            "    on ps.id = pdn.species_id " +
            "  join pokemon as po " +
            "    on ps.id = po.species_id " +
            "  join pokemon_types as pt " +
            "    on po.id = pt.pokemon_id " +
            "where pdn.pokedex_id = " + pokedexType.ordinal() + " and " +
            "  pt.type_id = " + pokemonType.ordinal() + " and " +
            "  pt.slot = " + typeSlot  + " " +
            "order by pdn.pokedex_number ";
        }
        else {
            return "select po.id as identifier, " +
            "  ps.identifier as name, "  +
            "  pdn.pokedex_number as number, "  +
            "  pt.type_id as type, "  +
            "  ps.evolves_from_species_id as evolves "  +
            "from pokemon_species as ps "  +
            "  join pokemon_dex_numbers as pdn "  +
            "    on ps.id = pdn.species_id "  +
            "  join pokemon as po "  +
            "    on ps.id = po.species_id "  +
            "  join pokemon_types as pt "  +
            "    on po.id = pt.pokemon_id "  +
            "where pdn.pokedex_id = " + pokedexType.ordinal() + " and "  +
            "  pt.slot = " + typeSlot  + " " +
            "order by pdn.pokedex_number ";
        }
    }

    public static String pokemonTypeQuery(int identifier, int typeSlot) {
        return "select po.id as identifier, " +
        "  ps.identifier as name, " +
        "  pt.type_id as type " +
        "from pokemon_species as ps " +
        "  join pokemon as po " +
        "    on ps.id = po.species_id " +
        "  join pokemon_types as pt " +
        "    on po.id = pt.pokemon_id " +
        "where po.id = " + identifier + " and " +
        "  pt.slot = " + typeSlot;
    }

    public static String movesSearchQuery(Pokemon pokemon, PokemonType moveType, MoveLearnMethod learnMethod, MoveCategory category) {
        String query = "select m.identifier as name," +
        "  m.type_id as type, " +
        "  mdc.id as category, " +
        "  m.power, " +
        "  m.accuracy " +
        "from " +
        "  pokemon_moves as pm " +
        "    join moves as m " +
        "      on pm.move_id = m.id " +
        "    join move_damage_classes as mdc " +
        "      on m.damage_class_id = mdc.id " +
        "where ";
        if (learnMethod != MoveLearnMethod.ALL) {
            query += " pm.pokemon_move_method_id = " + learnMethod.ordinal() + " and ";
        }
        if (moveType != PokemonType.NONE) {
            query += " m.type_id = "+ moveType.ordinal() + " and ";
        }
        if (category != MoveCategory.ALL) {
            query += " m.damage_class_id = " + category.ordinal() + " and ";
        }
        query += " pokemon_id = "+ pokemon.getIdentifier() + " " +
        "group by m.identifier order by m.identifier";
        return query;
    }

    public static String prevolutionQuery(int identifier) {
        return "select ps.evolves_from_species_id as prevolution " +
        "from pokemon_species as ps " +
        "  join pokemon as po " +
        "    on ps.id = po.species_id  " +
        "where po.id = " + identifier;
    }

    public static String efficacyQuery() {
        return "select damage_type_id as damager, " +
        " target_type_id as target, " +
        " damage_factor as factor " +
        "from type_efficacy ";
    }

}
