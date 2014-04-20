package com.ruenzuo.pokeffective.models;

/**
 * Created by ruenzuo on 20/04/14.
 */
public class STAB {

    private Pokemon pokemon;
    private Move move;

    public STAB(STABBuilder builder) {
        this.pokemon = builder.pokemon;
        this.move = builder.move;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public static class STABBuilder {

        private final Pokemon pokemon;
        private final Move move;

        public STABBuilder(Pokemon pokemon, Move move) {
            this.pokemon = pokemon;
            this.move = move;
        }

        public STAB build() {
            return new STAB(this);
        }

    }

}
