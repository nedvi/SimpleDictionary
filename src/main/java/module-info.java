module com.example.simpledictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens com.nedved.simpledictionary to javafx.fxml;
    exports com.nedved.simpledictionary;
    exports com.nedved.simpledictionary.controller;
    exports com.nedved.simpledictionary.model;
    opens com.nedved.simpledictionary.controller to javafx.fxml;
}