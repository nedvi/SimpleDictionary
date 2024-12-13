package com.nedved.simpledictionary.controller;

import com.nedved.simpledictionary.MainApplication;
import com.nedved.simpledictionary.model.DataModel;
import com.nedved.simpledictionary.model.StaticData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

/**
 * Controller pro zobrazeni hlavni stranky slovniku
 *
 * @author Dominik Nedved
 * @version 13.12.2024
 */
public class MainController {

    /** Atribut datoveho modelu */
    protected DataModel dataModel;

    /** Label vybraneho jazyka */
    @FXML
    private Label leftLangLabel;

    /** Label prelozeneho jazyka */
    @FXML
    private Label rightLangLabel;

    /** Combobox s moznosti vyhledavani */
    @FXML
    private SearchableComboBox<String> searchableCB;

    /** Label vybraneho slova */
    @FXML
    private Label chosenWordLabel;

    /** Label prelozeneho slova */
    @FXML
    private Label translatedWordLabel;

    /** Odkaz na tmavy .css stylelesheet */
    private final String DARK_STYLE_SHEET = String.valueOf(MainApplication.class.getResource("styles/darkStyle.css"));

    /**
     * Metoda, ktera nacte jedinou instanci datoveho modelu.
     * Slouzi pro sdileni teto instance mezi vsemi pripadnymi controllery pro jednoduchou manipulaci s daty.
     *
     * @param dataModel instance datoveho modelu
     */
    public void initModel(DataModel dataModel) {
        if (this.dataModel != null) {
            throw new IllegalStateException("Model already initialized");
        }
        this.dataModel = dataModel ;
    }

    /**
     * Inicializace searchable comboboxu
     */
    public void initSearchableCB() {
        updateSearchableCB();
        searchableCB.showingProperty().addListener((observable, hidden, showing) -> {
            if (hidden) {
                String selectedKey = searchableCB.getValue(); // Vybrané slovo (klíč)
                String translation = dataModel.getCurrentDictionary().getWordMap().get(selectedKey); // Překlad z HashMap
                System.out.println(selectedKey + " -> " + translation);
                chosenWordLabel.setText(selectedKey);
                translatedWordLabel.setText(translation);
                dataModel.currentWord.setCurrentWord(selectedKey, translation);
            }
        });
    }

    /**
     * Metoda pro update searchable comboboxu.
     * Ziska FXCollections observable ArrayList z HashMapy aktualniho slovniku.
     * Diky observable je pak mozno napojit kolekci na searchable combobox.
     */
    public void updateSearchableCB() {
        ObservableList<String> values = FXCollections.observableArrayList(dataModel.getCurrentDictionary().getWordMap().keySet());
        searchableCB.setItems(values);
        searchableCB.setValue(dataModel.currentWord.getWord());
        chosenWordLabel.setText(dataModel.currentWord.getWord());
        translatedWordLabel.setText(dataModel.currentWord.getTranslatedWord());
    }

    /**
     * Akce pro tlacitko prohozeni jazyku.
     * Updatne labely a prenastavi aktualni slovnik.
     */
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
    }

    /**
     * Akce pro tlacitko pridani noveho slova.
     * Zavola funkci createNewWordDialog s parametrem false
     */
    @FXML
    protected void onNewWordBTNClicked() {
        createNewWordDialog(false);
    }

    /**
     * Akce pro tlacitko upravu existujiciho slova.
     * Zavola funkci createNewWordDialog s parametrem true
     */
    @FXML
    private void onEditBTNClicked() {
        createNewWordDialog(true);
    }

    /**
     * Vytvori a zobrazi dialog zavisly na parametru editMode, podle ktereho se zobrazi a nasledne vykona editace nebo vytvoreni noveho slova
     * @param editMode true pro editacni mod, false pro mod vytvoreni noveho slova
     */
    public void createNewWordDialog(boolean editMode) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setResizable(false);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        Label label1 = new Label(dataModel.getCurrentDictionary().getName().equals(StaticData.ENGLISH_TO_CZECH) ? "Anglicky" : "Česky");
        TextField textField1 = new TextField();
        Label label2 = new Label(dataModel.getCurrentDictionary().getName().equals(StaticData.ENGLISH_TO_CZECH) ? "Česky" : "Anglicky");
        TextField textField2 = new TextField();

        grid.add(label1, 0, 0);
        grid.add(textField1, 1, 0);
        grid.add(label2, 0, 1);
        grid.add(textField2, 1, 1);

        Button okBTN = new Button("OK");
        Button cancelBTN = new Button("Cancel");

        HBox buttonBox = new HBox(10, okBTN, cancelBTN);
        grid.add(buttonBox, 1, 2);

        okBTN.setOnAction(e -> {
            String newWord = textField1.getText();
            String translatedWord = textField2.getText();

            if (editMode) {
                dataModel.getCurrentDictionary().editWord(dataModel.currentWord.getWord(), newWord, translatedWord);
                editWordInReversedDictionary(dataModel.currentWord.getTranslatedWord(), translatedWord, newWord);
            } else {
                dataModel.getCurrentDictionary().addWord(newWord, translatedWord);
                addWordToReversedDictionary(translatedWord, newWord);
            }


            dataModel.currentWord.setCurrentWord(newWord, translatedWord);

            updateSearchableCB();


            dialog.close();
        });

        cancelBTN.setOnAction(e -> dialog.close());

        Scene dialogScene = new Scene(grid, 300, 200);

        if (editMode)
            dialog.setTitle("Upravit slovo");
        else
            dialog.setTitle("Nové slovo");

        grid.getStylesheets().add(DARK_STYLE_SHEET);
        grid.getStyleClass().add("alertDP");

        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    /**
     * Metoda pro pridani kombinace slov i do obraceneho slovniku
     *
     * @param newWord nove slovo (jiz pro obraceny slovnik)
     * @param translatedWord prelozene slovo (jiz pro obraceny slovnik)
     */
    private void addWordToReversedDictionary(String newWord, String translatedWord) {
        if (dataModel.getCurrentDictionary().getName().equals(StaticData.ENGLISH_TO_CZECH)) {
            dataModel.getDictionaries().get(StaticData.CZECH_TO_ENGLISH).addWord(newWord, translatedWord);
        } else dataModel.getDictionaries().get(StaticData.ENGLISH_TO_CZECH).addWord(newWord, translatedWord);
    }

    /**
     * Metoda pro upravu kombinace slov i v obracenem slovniku
     *
     * @param oldWord stareSlovo (jiz pro obraceny slovnik)
     * @param newWord nove slovo (jiz pro obraceny slovnik)
     * @param translatedWord prelozene slovo (jiz pro obraceny slovnik)
     */
    private void editWordInReversedDictionary(String oldWord, String newWord, String translatedWord) {
        if (dataModel.getCurrentDictionary().getName().equals(StaticData.ENGLISH_TO_CZECH)) {
            dataModel.getDictionaries().get(StaticData.CZECH_TO_ENGLISH).editWord(oldWord, newWord, translatedWord);
        } else dataModel.getDictionaries().get(StaticData.ENGLISH_TO_CZECH).editWord(oldWord, newWord, translatedWord);
    }

    /**
     * Akce pro vymazani kombinace slov.
     * Provede vymazani i pro obraceny slovnik.
     */
    @FXML
    private void onDeleteBTNClicked() {
        dataModel.getCurrentDictionary().removeWord(dataModel.currentWord.getWord());

        if (dataModel.getCurrentDictionary().getName().equals(StaticData.ENGLISH_TO_CZECH)) {
            dataModel.getDictionaries().get(StaticData.CZECH_TO_ENGLISH).removeWord(dataModel.currentWord.getTranslatedWord());
        } else dataModel.getDictionaries().get(StaticData.ENGLISH_TO_CZECH).removeWord(dataModel.currentWord.getTranslatedWord());

        dataModel.currentWord.empty();
        updateSearchableCB();
    }

    /**
     * Zobrazi informace o programu a autorovi
     */
    @FXML
    private void onInfoBTNClicked() {
        String aboutAuthorSB =
                """
                        Simple Dictionary

                        Verze:\t\t\t0.2 (12.13.2024)
                        Autor:\t\t\tDominik Nedved
                        Discord:\t\t\tnedvi
                        """;

        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle("About");
        aboutAlert.setHeaderText("About");
        aboutAlert.setContentText(aboutAuthorSB);

        DialogPane alertDP = aboutAlert.getDialogPane();
        alertDP.getStylesheets().add(DARK_STYLE_SHEET);
        alertDP.getStyleClass().add("alertDP");

        aboutAlert.show();
    }
}