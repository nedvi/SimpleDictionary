package com.nedved.simpledictionary.model;

import java.io.*;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtilities {


    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Save HashMap to a file
    public static void saveHashMapToFile(String dictionaryName, HashMap<String, String> map) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("dictionaries/" + dictionaryName + ".json"), map);
    }

    // Load HashMap from a file
    public static HashMap<String, String> loadHashMapFromFile(String dictionaryName) {
        HashMap<String, String> fileHashMap = null;
        try {
            fileHashMap = objectMapper.readValue(new File("dictionaries/" + dictionaryName + ".json"), new TypeReference<HashMap<String, String>>() {});
            System.out.println("Dictionary loaded.");
            return fileHashMap;
        } catch (IOException e) {
            System.out.println("No dictionary found!");
            return null;
        }
    }
}
