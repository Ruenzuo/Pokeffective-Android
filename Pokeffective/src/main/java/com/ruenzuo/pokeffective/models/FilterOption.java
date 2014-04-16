package com.ruenzuo.pokeffective.models;

import java.util.ArrayList;

/**
 * Created by ruenzuo on 17/04/14.
 */
public class FilterOption {

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

    public static ArrayList<FilterOption> pokemonFilterOptions() {
        ArrayList<FilterOption> options = new ArrayList<FilterOption>();
        FilterOption optionPokedex = new FilterOptionBuilder("Filtering by Pokédex", FilterType.POKEDEX_TYPE).build();
        FilterOption optionType = new FilterOptionBuilder("Filtering by type", FilterType.POKEMON_TYPE).build();
        options.add(optionPokedex);
        options.add(optionType);
        return options;
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
