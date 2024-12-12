package com.nedved.simpledictionary.controller;

import com.nedved.simpledictionary.model.DataModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainController {

    /** Atribut datoveho modelu */
    protected DataModel dataModel;

    /**
     * Metoda, ktera nacte jedinou instanci datoveho modelu.
     * Slouzi pro sdileni teto instance mezi vsemi controllery pro jednoduchou manipulaci s daty.
     *
     * @param dataModel instance datoveho modelu
     */
    public void initModel(DataModel dataModel) {
        if (this.dataModel != null) {
            throw new IllegalStateException("Model already initialized");
        }
        this.dataModel = dataModel ;
    }

    @FXML
    private Label leftLangLabel;

    @FXML
    private Label rightLangLabel;

    @FXML
    private Button languageReverseBTN;

    @FXML
    protected void onLanguageReverseBTNClicked() {
        System.out.println("Language reverse button clicked.");
    }


//    @FXML
//    protected void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
//    }
}