package com.nedved.simpledictionary.model;

import java.io.IOException;
import java.util.HashMap;

public class Dictionary {

    private final String name;

    private final HashMap<String, String> wordMap;


    public Dictionary(String name, HashMap<String, String> wordMap) {
        this.name = name;
        this.wordMap = wordMap;
    }

    public void addWord(String newWord, String translatedWord) {
        this.wordMap.put(newWord, translatedWord);
        try {
            JsonUtilities.saveHashMapToFile(name, wordMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeWord(String word) {
        wordMap.remove(word);
        try {
            JsonUtilities.saveHashMapToFile(name, wordMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void editWord(String oldWord, String newWord, String translatedWord) {
        wordMap.remove(oldWord);
        wordMap.put(newWord, translatedWord);

        try {
            JsonUtilities.saveHashMapToFile(name, wordMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //============= Getters, Setters =============

    public HashMap<String, String> getWordMap() {
        return wordMap;
    }

    public String getName() {
        return name;
    }
}
