package com.intellij.bikerbikus;

import com.intellij.bikerbikus.grafico.LoginControllerGrafico;
import com.intellij.bikerbikus.queries.CheckNoleggi;
import com.intellij.bikerbikus.swing.Login2000;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class BikerBikus extends Application {
    private Parent parent;
    @FXML
    Button btnFirst;
    @FXML
    Button btnSecond;


    @Override
    public void start(Stage stage) {

        stage.setMaximized(true);
        stage.setTitle("BikerBikus");
        stage.setScene(new Scene(new Pane()));
        stage.show();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/choose-your-graphic-view.fxml"));
        fxmlLoader.setController(this);
        CheckNoleggi.checkNoleggi();
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        stage.getScene().setRoot(parent);
        G1.g();
        btnFirst.setOnAction(event -> new LoginControllerGrafico().showScene(stage));
        btnSecond.setOnAction(event -> {

            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
            } catch(Exception ignored){}
            JFrame fram = new JFrame();
            new Login2000().mostra(fram);
            stage.close();
        });


    }
    public static void main(String[] args) {
        launch();
    }
}