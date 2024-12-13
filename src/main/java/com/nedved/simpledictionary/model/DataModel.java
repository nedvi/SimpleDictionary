package com.nedved.simpledictionary.model;

import java.util.HashMap;

/**
 * Trida datoveho modelu pro jednduchy pristup k datum z ruznych zdroju
 *
 * @author Dominik Nedved
 * @version 13.12.2024
 */
public class DataModel {

    /** Instance aktualni kombinace slov */
    public CurrentWord currentWord = new CurrentWord();

    /** Mapa slovniku */
    private final HashMap<String, Dictionary> dictionaries = new HashMap<>();

    /** Instance aktualniho slovniku */
    private Dictionary currentDictionary;

    /**
     * @return instance aktualniho slovniku
     */
    public Dictionary getCurrentDictionary() {
        return currentDictionary;
    }

    /**
     * Nastavi pomoci parametr nazev slovniku aktualni slovnik
     * @param dictionaryName nazev slovniku
     */
    public void setCurrentDictionary(String dictionaryName) {
        currentDictionary = dictionaries.get(dictionaryName);
    }

    /**
     * @return mapa slovniku
     */
    public HashMap<String, Dictionary> getDictionaries() {
        return dictionaries;
    }

    /**
     * Prida slovnik do mapy slovniku
     *
     * @param dictionaryName nazev slovniku
     * @param dictionary instance slovniku
     */
    public void addDictionary(String dictionaryName, Dictionary dictionary) {
        dictionaries.put(dictionaryName, dictionary);
    }
}
