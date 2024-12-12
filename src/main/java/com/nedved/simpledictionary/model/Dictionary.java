package com.nedved.simpledictionary.model;

import java.util.HashMap;

public class Dictionary {
    private final HashMap<String, String> wordMap;

    private final CurrentWord currentWord;

    public Dictionary(HashMap<String, String> wordMap) {
        this.wordMap = wordMap;
        this.currentWord = new CurrentWord();
    }

    public void addWord(String newWord, String translatedWord) {
        this.wordMap.put(newWord, translatedWord);
        currentWord.setCurrentWord(newWord, translatedWord);
    }

    public void removeWord() {

    }

    public void editWord() {

    }

    public HashMap<String, String> getWordMap() {
        return wordMap;
    }


}
