package com.ruenzuo.pokeffective.models;

import java.util.ArrayList;

/**
 * Created by ruenzuo on 20/04/14.
 */
public class Effective {

    private PokemonType pokemonType;
    private Effectiveness effectiveness;
    private ArrayList<STAB> STABs;

    public Effective(EffectiveBuilder builder) {
        this.pokemonType = builder.pokemonType;
        this.effectiveness = builder.effectiveness;
        this.STABs = builder.STABs;
    }

    public PokemonType getPokemonType() {
        return pokemonType;
    }

    public void setPokemonType(PokemonType pokemonType) {
        this.pokemonType = pokemonType;
    }

    public Effectiveness getEffectiveness() {
        return effectiveness;
    }

    public void setEffectiveness(Effectiveness effectiveness) {
        this.effectiveness = effectiveness;
    }

    public void addSTAB(STAB anSTAB) {
        STABs.add(anSTAB);
    }

    public ArrayList<STAB> getSTABs() {
        return STABs;
    }

    public static class EffectiveBuilder {

        private final PokemonType pokemonType;
        private final Effectiveness effectiveness;
        private ArrayList<STAB> STABs;

        public EffectiveBuilder(PokemonType pokemonType, Effectiveness effectiveness) {
            this.pokemonType = pokemonType;
            this.effectiveness = effectiveness;
        }

        public EffectiveBuilder STABs(ArrayList<STAB> STABs) {
            this.STABs = STABs;
            return this;
        }

        public Effective build() {
            return new Effective(this);
        }

    }

}
