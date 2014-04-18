package com.ruenzuo.pokeffective.models;

import java.io.Serializable;

/**
 * Created by ruenzuo on 18/04/14.
 */
public enum MoveLearnMethod implements Serializable {

    ALL ("All"), LEVEL_UP ("Level up"), EGG ("Egg"), TUTOR ("Tutor"), MACHINE ("Machine");

    private final String print;

    MoveLearnMethod(String prt) {
        print = prt;
    }

    public static CharSequence[] moveLearnMethods() {
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
