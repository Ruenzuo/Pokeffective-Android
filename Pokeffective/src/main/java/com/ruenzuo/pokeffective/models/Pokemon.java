package com.ruenzuo.pokeffective.models;

/**
 * Created by ruenzuo on 16/04/14.
 */
public class Pokemon {

    private int identifier;
    private String name;
    private int pokedexNumber;
    private PokemonType firstType;
    private PokemonType secondType;

    private Pokemon(PokemonBuilder builder) {
        this.identifier = builder.identifier;
        this.name = builder.name;
        this.pokedexNumber = builder.pokedexNumber;
        this.firstType = builder.firstType;
        this.secondType = builder.secondType;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPokedexNumber() {
        return pokedexNumber;
    }

    public void setPokedexNumber(int pokedexNumber) {
        this.pokedexNumber = pokedexNumber;
    }

    public PokemonType getFirstType() {
        return firstType;
    }

    public void setFirstType(PokemonType firstType) {
        this.firstType = firstType;
    }

    public PokemonType getSecondType() {
        return secondType;
    }

    public void setSecondType(PokemonType secondType) {
        this.secondType = secondType;
    }

    public static class PokemonBuilder {

        private final int identifier;
        private final String name;
        private int pokedexNumber;
        private PokemonType firstType;
        private PokemonType secondType;

        public PokemonBuilder(int identifier, String name) {
            this.identifier = identifier;
            this.name = name;
        }

        public PokemonBuilder pokedexNumber(int pokedexNumber) {
            this.pokedexNumber = pokedexNumber;
            return this;
        }

        public PokemonBuilder firstType(PokemonType firstType) {
            this.firstType = firstType;
            return this;
        }

        public PokemonBuilder secondType(PokemonType secondType) {
            this.secondType = secondType;
            return this;
        }

        public Pokemon build() {
            return new Pokemon(this);
        }

    }

}
