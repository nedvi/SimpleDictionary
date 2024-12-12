package com.nedved.simpledictionary.model;

import java.util.HashMap;

public class DataModel {
    private final HashMap<String, Dictionary> dictionaries = new HashMap<>();

    public HashMap<String, Dictionary> getDictionaries() {
        return dictionaries;
    }
}
