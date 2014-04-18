package com.ruenzuo.pokeffective.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by ruenzuo on 18/04/14.
 */
@Table(name = "Move")
public class Move extends Model implements Cloneable, Serializable {

    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private PokemonType pokemonType;
    @Column(name = "category")
    private MoveCategory category;
    @Column(name = "power")
    private int power;
    @Column(name = "accuracy")
    private int accuracy;
    @Column(name = "Pokemon", onDelete = Column.ForeignKeyAction.SET_NULL)
    private Pokemon pokemon;

    public Move(){
        super();
    }

    private Move(MoveBuilder builder) {
        this.name = builder.name;
        this.pokemonType = builder.pokemonType;
        this.category = builder.category;
        this.power = builder.power;
        this.accuracy = builder.accuracy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PokemonType getPokemonType() {
        return pokemonType;
    }

    public void setPokemonType(PokemonType pokemonType) {
        this.pokemonType = pokemonType;
    }

    public MoveCategory getCategory() {
        return category;
    }

    public void setCategory(MoveCategory category) {
        this.category = category;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Move clone() {
        try {
            return (Move) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static class MoveBuilder {

        private final String name;
        private PokemonType pokemonType;
        private MoveCategory category;
        private int power;
        private int accuracy;

        public MoveBuilder(String name) {
            this.name = name;
        }

        public MoveBuilder pokemonType(PokemonType pokemonType) {
            this.pokemonType = pokemonType;
            return this;
        }

        public MoveBuilder category(MoveCategory category) {
            this.category = category;
            return this;
        }

        public MoveBuilder power(int power) {
            this.power = power;
            return this;
        }

        public MoveBuilder accuracy(int accuracy) {
            this.accuracy = accuracy;
            return this;
        }

        public Move build() {
            return new Move(this);
        }

    }

}
