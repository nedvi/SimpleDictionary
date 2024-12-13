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

public class MainApplication extends Application {

    /** Datovy model */
    public DataModel dataModel;

    FXMLLoader fxmlLoader;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(fxmlLoader.getRoot(), 640, 480);
        stage.setTitle("Simple Dictionary");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() throws IOException {
        System.out.println("Initialization...");

        dataModel = new DataModel();

        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        fxmlLoader.load();

        MainController mainController = fxmlLoader.getController();
        mainController.initModel(dataModel);

        Dictionary englishToCzechDictionary = new Dictionary(StaticData.ENGLISH_TO_CZECH, JsonUtilities.loadHashMapFromFile(StaticData.ENGLISH_TO_CZECH));
        dataModel.addDictionary(StaticData.ENGLISH_TO_CZECH, englishToCzechDictionary);

        Dictionary czechToEnglishDictionary = new Dictionary(StaticData.CZECH_TO_ENGLISH, JsonUtilities.loadHashMapFromFile(StaticData.CZECH_TO_ENGLISH));
        dataModel.addDictionary(StaticData.CZECH_TO_ENGLISH, czechToEnglishDictionary);
//        createDefaultData();
        dataModel.setCurrentDictionary(StaticData.ENGLISH_TO_CZECH);

        mainController.initSearchableCB();

        System.out.println("Initialization completed.");
    }

    public void createDefaultData() throws IOException {
        HashMap<String, String> englishToCzechMap = new HashMap<>();
        englishToCzechMap.put("hello", "ahoj");
        englishToCzechMap.put("world", "svět");

        JsonUtilities.saveHashMapToFile(StaticData.ENGLISH_TO_CZECH, englishToCzechMap);
        dataModel.addDictionary(StaticData.ENGLISH_TO_CZECH, new Dictionary(StaticData.ENGLISH_TO_CZECH, englishToCzechMap));
        dataModel.setCurrentDictionary(StaticData.ENGLISH_TO_CZECH);

        //=======================================================

        HashMap<String, String> czechToEnglishMap = new HashMap<>();
        czechToEnglishMap.put("ahoj", "hello");
        czechToEnglishMap.put("svět", "world");

        JsonUtilities.saveHashMapToFile(StaticData.CZECH_TO_ENGLISH, czechToEnglishMap);
        dataModel.addDictionary(StaticData.CZECH_TO_ENGLISH, new Dictionary(StaticData.CZECH_TO_ENGLISH, czechToEnglishMap));

        System.out.println("Generated default dictionary data.");
    }

}