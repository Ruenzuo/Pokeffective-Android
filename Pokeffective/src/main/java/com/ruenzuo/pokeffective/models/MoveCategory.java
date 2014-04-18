package com.ruenzuo.pokeffective.models;

import java.io.Serializable;

/**
 * Created by ruenzuo on 18/04/14.
 */
public enum MoveCategory implements Serializable {

    ALL ("All"), NON_DAMAGING ("Non damaging"), PHYSICAL ("Physical"), SPECIAL ("Special");

    private final String print;

    MoveCategory(String prt) {
        print = prt;
    }

    public static CharSequence[] moveCategories() {
        int length = MoveCategory.values().length;
        CharSequence[] categoriesPrint = new String[length];
        for (int i = 0; i < length; i++) {
            categoriesPrint[i] = MoveCategory.values()[i].toString();
        }
        return categoriesPrint;
    }

    public String toString(){
        return print;
    }

}
