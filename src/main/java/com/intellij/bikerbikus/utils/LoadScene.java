package com.intellij.bikerbikus.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadScene {

    Parent par;
    public void load(Stage stage, Parent parent, Object controller, String url){
        par=parent;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(url));
        fxmlLoader.setController(controller);
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        stage.getScene().setRoot(parent);

    }
}
