package com.ruenzuo.pokeffective.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ruenzuo on 17/04/14.
 */
public class FilterOption implements Serializable, Cloneable {

    private String label;
    private Object value;
    private FilterType filterType;

    private FilterOption(FilterOptionBuilder builder) {
        this.label = builder.label;
        this.filterType = builder.filterType;
        this.value = builder.value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public static FilterOption defaultPokedexFilterOption() {
        return new FilterOptionBuilder("Filtering by pokédex", FilterType.POKEDEX_TYPE).value(PokedexType.NATIONAL).build();
    }

    public static FilterOption defaultPokemonTypeFilterOption() {
        return new FilterOptionBuilder("Filtering by pokémon type", FilterType.POKEMON_TYPE).value(PokemonType.NONE).build();
    }

    public FilterOption clone() {
        try {
            return (FilterOption) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private static class FilterOptionBuilder {

        private final String label;
        private final FilterType filterType;
        private Object value;

        public FilterOptionBuilder(String label, FilterType filterType) {
            this.label = label;
            this.filterType = filterType;
        }

        public FilterOptionBuilder value(Object value) {
            this.value = value;
            return this;
        }

        public FilterOption build() {
            return new FilterOption(this);
        }

    }

}
