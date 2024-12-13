package com.nedved.simpledictionary;

import com.nedved.simpledictionary.controller.MainController;
import com.nedved.simpledictionary.model.DataModel;
import com.nedved.simpledictionary.model.Dictionary;
import com.nedved.simpledictionary.model.JsonUtilities;
import com.nedved.simpledictionary.model.StaticData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

/**
 * Hlavni trida programu SimpleDictionary
 *
 * @author Dominik Nedved
 * @version 13.12.2024
 */
public class MainApplication extends Application {

    /** Datovy model */
    public DataModel dataModel;

    /** FXMLLoader */
    public FXMLLoader fxmlLoader;

    /**
     * Spousteci metoda
     *
     * @param args parametry prikazove radky
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(fxmlLoader.getRoot(), 640, 480);
        stage.setTitle("Simple Dictionary");
        stage.setScene(scene);
        stage.setMinWidth(640);
        stage.setMinHeight(480);
        stage.show();
    }

    /**
     * Inicializacni metoda, stara se o FXML loader
     * a vola nacitani ze souboru - v pripade nenalezeni souboru vytvori defaultni data.
     *
     * @throws IOException e
     */
    @Override
    public void init() throws IOException {
        System.out.println("Initialization...");

        dataModel = new DataModel();

        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        fxmlLoader.load();

        MainController mainController = fxmlLoader.getController();
        mainController.initModel(dataModel);

        HashMap<String, String> englishToCzechMap = JsonUtilities.loadHashMapFromFile(StaticData.ENGLISH_TO_CZECH);
        HashMap<String, String> czechToEnglishMap = JsonUtilities.loadHashMapFromFile(StaticData.CZECH_TO_ENGLISH);

        Dictionary englishToCzechDictionary;
        Dictionary czechToEnglishDictionary;
        if (englishToCzechMap == null || czechToEnglishMap == null) {
            Dictionary[] defaultDictionaries = createDefaultData();
            englishToCzechDictionary = defaultDictionaries[0];
            czechToEnglishDictionary = defaultDictionaries[1];
        } else {
            englishToCzechDictionary = new Dictionary(StaticData.ENGLISH_TO_CZECH, englishToCzechMap);
            czechToEnglishDictionary = new Dictionary(StaticData.CZECH_TO_ENGLISH, czechToEnglishMap);
        }

        dataModel.addDictionary(StaticData.ENGLISH_TO_CZECH, englishToCzechDictionary);
        dataModel.addDictionary(StaticData.CZECH_TO_ENGLISH, czechToEnglishDictionary);
        dataModel.setCurrentDictionary(StaticData.ENGLISH_TO_CZECH);

        mainController.initSearchableCB();

        System.out.println("Initialization completed.");
    }

    /**
     * Metoda pro vytvoreni defaultnich dat pri nenalezeni ulozneych souboru
     *
     * @throws IOException e
     */
    public Dictionary[] createDefaultData() throws IOException {
        Dictionary[] defaultDictionaries = new Dictionary[2];

        HashMap<String, String> englishToCzechMap = new HashMap<>();
        englishToCzechMap.put("hello", "ahoj");
        englishToCzechMap.put("world", "svět");

        JsonUtilities.saveHashMapToFile(StaticData.ENGLISH_TO_CZECH, englishToCzechMap);
        defaultDictionaries[0] = new Dictionary(StaticData.ENGLISH_TO_CZECH, englishToCzechMap);

        HashMap<String, String> czechToEnglishMap = new HashMap<>();
        czechToEnglishMap.put("ahoj", "hello");
        czechToEnglishMap.put("svět", "world");

        JsonUtilities.saveHashMapToFile(StaticData.CZECH_TO_ENGLISH, czechToEnglishMap);
        defaultDictionaries[1] = new Dictionary(StaticData.CZECH_TO_ENGLISH, czechToEnglishMap);

        System.out.println("Generated default dictionary data.");

        return defaultDictionaries;
    }

}