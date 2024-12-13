package com.nedved.simpledictionary.model;

import java.util.HashMap;

public class DataModel {

    public CurrentWord currentWord = new CurrentWord();

    private final HashMap<String, Dictionary> dictionaries = new HashMap<>();

    private Dictionary currentDictionary;

    public Dictionary getCurrentDictionary() {
        return currentDictionary;
    }

    public void setCurrentDictionary(String dictionaryName) {
        currentDictionary = dictionaries.get(dictionaryName);
    }

    public HashMap<String, Dictionary> getDictionaries() {
        return dictionaries;
    }

    public void addDictionary(String dictionaryName, Dictionary dictionary) {
        dictionaries.put(dictionaryName, dictionary);
    }
}
