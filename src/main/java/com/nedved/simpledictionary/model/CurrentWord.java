package com.nedved.simpledictionary.model;

public class CurrentWord {

    private String word;

    private String translatedWord;

    public CurrentWord() {}

    public CurrentWord(String word, String translatedWord) {
        this.word = word;
        this.translatedWord = translatedWord;
        System.out.println("Current word combination: " + this.word + " -> " + this.translatedWord);
    }

    public void empty() {
        this.word = null;
        this.translatedWord = null;
    }

    public void setCurrentWord(String word, String translatedWord) {
        this.word = word;
        this.translatedWord = translatedWord;
        System.out.println("Current word combination: " + this.word + " -> " + this.translatedWord);
    }

    public String getWord() {
        return word;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }

    public void reverseLanguages() {
        String tempWord = word;
        word = translatedWord;
        translatedWord = tempWord;
        System.out.println("Current word combination: " + this.word + " -> " + this.translatedWord);
    }
}
