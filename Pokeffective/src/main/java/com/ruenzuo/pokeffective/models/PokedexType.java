package com.ruenzuo.pokeffective.models;

/**
 * Created by ruenzuo on 16/04/14.
 */

public enum PokedexType {

    NONE ("None"), NATIONAL ("National"), KANTO ("Kanto"), JHOTO ("Jhoto"), HOENN ("Hoenn"),
    ORIGINAL_SINNOH ("Sinnoh"), EXTENDED_SINNOH ("Extended Sinno"),
    UPDATED_JHOTO ("Updated Jhoto"), ORIGINAL_UNOVA ("Unova"), EXTENDED_UNOVA ("Extended Unova"),
    CONQUEST_GALLERY ("Conquest Gallery"), KALOS_CENTRAL ("Kalos Central"), KALOS_COASTAL ("Calos Coastal"),
    KALOS_MOUNTAIN ("Calos Mountain");

    private final String print;

    PokedexType(String prt) {
        print = prt;
    }

    public static CharSequence[] pokedexTypes()
    {
        CharSequence[] typesPrint = new String[9];
        typesPrint[0] = NATIONAL.toString();
        typesPrint[1] = KANTO.toString();
        typesPrint[2] = JHOTO.toString();
        typesPrint[3] = HOENN.toString();
        typesPrint[4] = ORIGINAL_SINNOH.toString();
        typesPrint[5] = ORIGINAL_UNOVA.toString();
        typesPrint[6] = KALOS_CENTRAL.toString();
        typesPrint[7] = KALOS_COASTAL.toString();
        typesPrint[8] = KALOS_MOUNTAIN.toString();
        return typesPrint;
    }

    public String toString(){
        return print;
    }

}