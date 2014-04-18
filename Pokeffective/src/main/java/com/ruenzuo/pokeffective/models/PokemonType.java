package com.ruenzuo.pokeffective.models;

import java.io.Serializable;

/**
 * Created by ruenzuo on 16/04/14.
 */
public enum PokemonType implements Serializable {

    NONE ("None", 0xff000000), NORMAL ("Normal", 0xffc7c7cc), FIGHTING ("Fighting", 0xfffc880f), FLYING ("Flying", 0xff55dae1),
    POISON ("Poison", 0xffed4694), GROUND ("Ground", 0xfffec418), ROCK ("Rock", 0xffd6cec3), BUG ("Bug", 0xffa5de37),
    GHOST ("Ghost", 0xff7b72e9), STEEL ("Steel", 0xff8e8e93), FIRE ("Fire", 0xfffd6631), WATER ("Water", 0xff1b9af7),
    GRASS ("Grass", 0xff4cd964), ELECTRIC ("Electric", 0xffffe93b), PSYCHIC ("Psychic", 0xffdb49d8), ICE ("Ice", 0xff28cdfb),
    DRAGON ("Dragon", 0xffff667a), DARK ("Dark", 0xff4a4a4a), FAIRY ("Fairy", 0xfffcb4e6);

    private final String print;
    private final int color;

    PokemonType(String prt, int clr) {
        print = prt;
        color = clr;
    }

    public static CharSequence[] pokemonTypes() {
        int length = PokemonType.values().length;
        CharSequence[] typesPrint = new String[length];
        for (int i = 0; i < length; i++) {
            typesPrint[i] = PokemonType.values()[i].toString();
        }
        return typesPrint;
    }

    public String toString(){
        return print;
    }

    public int toColor(){
        return color;
    }

}