package com.ruenzuo.pokeffective.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ruenzuo on 16/04/14.
 */
@Table(name = "Pokemon")
public class Pokemon extends Model implements Cloneable, Serializable {

    @Column(name = "identifier")
    private int identifier;
    @Column(name = "name")
    private String name;
    private int pokedexNumber;
    @Column(name = "firstType")
    private PokemonType firstType;
    @Column(name = "secondType")
    private PokemonType secondType;

    public Pokemon(){
        super();
    }

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

    public static List<Pokemon> getAll() {
        return new Select().from(Pokemon.class).execute();
    }

    public Pokemon clone() {
        try {
            return (Pokemon) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
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
