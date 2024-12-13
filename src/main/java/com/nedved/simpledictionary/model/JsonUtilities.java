package com.nedved.simpledictionary.model;

import java.io.*;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Trida s metodami pro ulozeni a nacteni JSON souboru
 *
 * @author Dominik Nedved
 * @version 13.12.2024
 */
public class JsonUtilities {

    /** Instance ObjectMapperu*/
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Ulozi mapu do JSON souboru
     *
     * @param dictionaryName nazev slovniku
     * @param map mapa slov a jejich prekladu
     * @throws IOException vyjimka pri chybe pri zapisu
     */
    public static void saveHashMapToFile(String dictionaryName, HashMap<String, String> map) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(dictionaryName + ".json"), map);
    }

    /**
     * Nacte z JSON souboru mapu slov a jejich prekladu
     *
     * @param dictionaryName nazev slovniku
     * @return mapa slov a jejich prekladu
     */
    public static HashMap<String, String> loadHashMapFromFile(String dictionaryName) {
        HashMap<String, String> fileHashMap;
        try {
            fileHashMap = objectMapper.readValue(new File(dictionaryName + ".json"), new TypeReference<>() {});
            System.out.println("Dictionary loaded.");
            return fileHashMap;
        } catch (IOException e) {
            System.out.println("No dictionary found!");
            return null;
        }
    }
}
