package com.nedved.simpledictionary.controller;

import com.nedved.simpledictionary.model.DataModel;
import com.nedved.simpledictionary.model.StaticData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

public class MainController {

    /** Atribut datoveho modelu */
    protected DataModel dataModel;

    @FXML
    private Label leftLangLabel;

    @FXML
    private Label rightLangLabel;

    @FXML
    private Button languageReverseBTN;

    @FXML
    private SearchableComboBox<String> searchableCB;

    @FXML
    private Label translatedWord;

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

    public void initSearchableCB() {
        updateSearchableCB();
        searchableCB.showingProperty().addListener((observable, hidden, showing) -> {
            if (hidden) {
                String selectedKey = searchableCB.getValue(); // Vybrané slovo (klíč)
                String translation = dataModel.getCurrentDictionary().getWordMap().get(selectedKey); // Překlad z HashMap
                System.out.println(selectedKey + " -> " + translation);
                translatedWord.setText(translation);
                dataModel.currentWord.setCurrentWord(selectedKey, translation);
            }
        });
    }

    public void updateSearchableCB() {
        // Vyber hodnoty v jednom jazyce (např. angličtina)
        ObservableList<String> values = FXCollections.observableArrayList(dataModel.getCurrentDictionary().getWordMap().keySet());

        // Naplň SearchableComboBox hodnotami
        searchableCB.setItems(values);

        searchableCB.setValue(dataModel.currentWord.getWord());
        translatedWord.setText(dataModel.currentWord.getTranslatedWord());
    }

    @FXML
    protected void onLanguageReverseBTNClicked() {
        if (dataModel.getCurrentDictionary().getName().equals(StaticData.ENGLISH_TO_CZECH)) {
            leftLangLabel.setText(StaticData.CZECH_LANG_CZE);
            rightLangLabel.setText(StaticData.ENGLISH_LANG_CZE);
            dataModel.setCurrentDictionary(StaticData.CZECH_TO_ENGLISH);
        } else {
            leftLangLabel.setText(StaticData.ENGLISH_LANG_CZE);
            rightLangLabel.setText(StaticData.CZECH_LANG_CZE);
            dataModel.setCurrentDictionary(StaticData.ENGLISH_TO_CZECH);
        }

        dataModel.currentWord.reverseLanguages();
        updateSearchableCB();
        System.out.println("Language reversed.");
    }

    @FXML
    protected void onNewWordBTNClicked() {
        createNewWordDialog(false);
    }

    @FXML
    private void onEditBTNClicked() {
        createNewWordDialog(true);
    }

    public void createNewWordDialog(boolean editMode) {
        // Vytvoření dialogového okna
        Stage dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);

        // Vytvoření mřížky pro uspořádání prvků
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        // Popisky a textová pole
        Label label1 = new Label(dataModel.getCurrentDictionary().getName().equals(StaticData.ENGLISH_TO_CZECH) ? "Anglicky" : "Česky");
        TextField textField1 = new TextField();
        Label label2 = new Label(dataModel.getCurrentDictionary().getName().equals(StaticData.ENGLISH_TO_CZECH) ? "Česky" : "Anglicky");
        TextField textField2 = new TextField();

        // Přidání prvků do mřížky
        grid.add(label1, 0, 0);
        grid.add(textField1, 1, 0);
        grid.add(label2, 0, 1);
        grid.add(textField2, 1, 1);

        // Tlačítka
        Button btnOk = new Button("OK");
        Button btnCancel = new Button("Cancel");

        // Horizontální box pro tlačítka
        HBox buttonBox = new HBox(10, btnOk, btnCancel);
        grid.add(buttonBox, 1, 2);

        // Nastavení akcí pro tlačítka
        btnOk.setOnAction(e -> {
            String newWord = textField1.getText();
            String translatedWord = textField2.getText();


            if (editMode) {
                dataModel.getCurrentDictionary().editWord(dataModel.currentWord.getWord(), newWord, translatedWord);
            } else {
                dataModel.getCurrentDictionary().addWord(newWord, translatedWord);
            }


            dataModel.currentWord.setCurrentWord(newWord, translatedWord);
            updateReversedDictionary(translatedWord, newWord);
            updateSearchableCB();


            dialog.close();
        });

        btnCancel.setOnAction(e -> dialog.close());

        // Vytvoření a nastavení scény pro dialog
        Scene dialogScene = new Scene(grid, 300, 200);

        if (editMode)
            dialog.setTitle("Upravit slovo");
        else
            dialog.setTitle("Nové slovo");

        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    private void updateReversedDictionary(String newWord, String translatedWord) {
//        Map<String, String> mapInversed =
//                dataModel.getCurrentDictionary().getWordMap().entrySet()
//                        .stream()
//                        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        if (dataModel.getCurrentDictionary().getName().equals(StaticData.ENGLISH_TO_CZECH)) {
            dataModel.getDictionaries().get(StaticData.CZECH_TO_ENGLISH).addWord(newWord, translatedWord);
        } else dataModel.getDictionaries().get(StaticData.ENGLISH_TO_CZECH).addWord(newWord, translatedWord);
    }

    @FXML
    private void onDeleteBTNClicked() {
        dataModel.getCurrentDictionary().removeWord(dataModel.currentWord.getWord());

        if (dataModel.getCurrentDictionary().getName().equals(StaticData.ENGLISH_TO_CZECH)) {
            dataModel.getDictionaries().get(StaticData.CZECH_TO_ENGLISH).removeWord(dataModel.currentWord.getTranslatedWord());
        } else dataModel.getDictionaries().get(StaticData.ENGLISH_TO_CZECH).removeWord(dataModel.currentWord.getTranslatedWord());

        dataModel.currentWord.empty();
        updateSearchableCB();
    }
}