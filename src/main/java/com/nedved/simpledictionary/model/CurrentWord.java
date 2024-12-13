package com.nedved.simpledictionary.model;

/**
 * Trida pro uchovavani informaci o aktualni kombinaci slov
 *
 * @author Dominik Nedved
 * @version 13.12.2024
 */
public class CurrentWord {

    /** Aktualni slovo */
    private String word;

    /** P */
    private String translatedWord;

    /**
     *  Vyprazdni aktualni kombinaci
     */
    public void empty() {
        this.word = null;
        this.translatedWord = null;
    }

    /**
     * Nastavi aktualni kombinaci
     * @param word aktualni slovo
     * @param translatedWord prelozene slovo
     */
    public void setCurrentWord(String word, String translatedWord) {
        this.word = word;
        this.translatedWord = translatedWord;
    }

    /**
     * @return aktualni slovo
     */
    public String getWord() {
        return word;
    }

    /**
     * @return prelozene slovo
     */
    public String getTranslatedWord() {
        return translatedWord;
    }

    /**
     * Metoda pro prohozeni jazyka aktualne zvolene kombinace slov
     */
    public void reverseLanguages() {
        String tempWord = word;
        word = translatedWord;
        translatedWord = tempWord;
    }
}
