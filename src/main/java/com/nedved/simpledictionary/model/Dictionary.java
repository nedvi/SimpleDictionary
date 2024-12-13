package com.nedved.simpledictionary.model;

import java.io.IOException;
import java.util.HashMap;

/**
 * Trida pro vytvoreni a praci se slovnikem
 *
 * @author Dominik Nedved
 * @version 13.12.2024
 */
public class Dictionary {

    /** Atribut nazev slovniku */
    private final String name;

    /** Mapa slov a jejich prekladu */
    private final HashMap<String, String> wordMap;

    /**
     * Konstruktor slovniku
     * @param name nazev slovniku
     * @param wordMap mapa slov a jejich prekladu
     */
    public Dictionary(String name, HashMap<String, String> wordMap) {
        this.name = name;
        this.wordMap = wordMap;
    }

    /**
     * Prida kombinaci slov do slovniku a ihned provede auto save do souboru
     *
     * @param newWord nove slovo
     * @param translatedWord preklad slova
     */
    public void addWord(String newWord, String translatedWord) {
        this.wordMap.put(newWord, translatedWord);
        try {
            JsonUtilities.saveHashMapToFile(name, wordMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Odebere slovo ze slovniku a ihned provede auto save do souboru
     * @param word slovo k odstraneni
     */
    public void removeWord(String word) {
        wordMap.remove(word);
        try {
            JsonUtilities.saveHashMapToFile(name, wordMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Provede editaci slova a ihned provede auto save do souboru
     * @param oldWord stare slovo
     * @param newWord nove slovo
     * @param translatedWord preklad slova
     */
    public void editWord(String oldWord, String newWord, String translatedWord) {
        wordMap.remove(oldWord);
        wordMap.put(newWord, translatedWord);
        try {
            JsonUtilities.saveHashMapToFile(name, wordMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return nazev slovniku
     */
    public String getName() {
        return name;
    }

    /**
     * @return mapa slov a jejich prekladu
     */
    public HashMap<String, String> getWordMap() {
        return wordMap;
    }
}
