package com.nedved.simpledictionary.model;

public class CurrentWord {
    private String word;

    private String translatedWord;


    public CurrentWord() {

    }

    public CurrentWord(String word, String translatedWord) {
        this.word = word;
        this.translatedWord = translatedWord;
    }

    public void setCurrentWord(String word, String translatedWord) {
        this.word = word;
        this.translatedWord = translatedWord;
    }


}
