package com.ruenzuo.pokeffective.models;

/**
 * Created by ruenzuo on 20/04/14.
 */
public enum Effectiveness {

    NONE ("None", -1), NO_EFFECT ("No effect", 0), NOT_VERY_EFFECTIVE ("Not very effective", 50),
    NORMAL ("Normal", 100), SUPER_EFFECTIVE ("Super effective", 200);

    private final String print;
    private final int multiplier;

    Effectiveness(String prt, int mlp) {
        print = prt;
        multiplier = mlp;
    }

    public String toString(){
        return print;
    }

    public int toInt(){
        return multiplier;
    }

    public static Effectiveness fromIntValue(int value) {
        switch (value) {
            case 0:
                return NO_EFFECT;
            case 50:
                return NOT_VERY_EFFECTIVE;
            case 100:
                return NORMAL;
            case 200:
                return SUPER_EFFECTIVE;
            default:
                return NONE;
        }
    }

}
