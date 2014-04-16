package com.ruenzuo.pokeffective.helpers;

import com.ruenzuo.pokeffective.models.PokedexType;
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
            "  pt.slot = " + typeSlot  +
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

}
