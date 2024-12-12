package com.nedved.simpledictionary;

import com.nedved.simpledictionary.controller.MainController;
import com.nedved.simpledictionary.model.DataModel;
import com.nedved.simpledictionary.model.Dictionary;
import com.nedved.simpledictionary.model.JsonUtilities;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        fxmlLoader.load();

        MainController mainController = fxmlLoader.getController();
        mainController.initModel(dataModel);

        if(JsonUtilities.loadHashMapFromFile() == null) {
            createDefaultData();
        }

        System.out.println("Initialization completed.");
    }

    public void createDefaultData() throws IOException {
        HashMap<String, String> englishToCzechMap = new HashMap<>();
        englishToCzechMap.put("hello", "ahoj");
        englishToCzechMap.put("world", "svět");
        JsonUtilities.saveHashMapToFile(englishToCzechMap);
        dataModel.getDictionaries().put("englishToCzech", new Dictionary(englishToCzechMap));
    }
}