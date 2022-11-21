package com.intellij.bikerbikus.grafico;

import com.intellij.bikerbikus.controllers1.LoginController1;
import com.intellij.bikerbikus.utils.LoadScene;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginControllerGrafico {

    private Parent parent;
    final Object controller = this;

    @FXML
    TextField txtEmail;
    @FXML
    PasswordField txtPassword;
    @FXML
    Button btnLogin;
    @FXML
    Hyperlink linkRegister;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/login-view.fxml");

        btnLogin.setOnAction(
                t -> {

                    if( LoginController1.onActionLogin(txtEmail.getText(), txtPassword.getText())){
                        new HomeControllerGrafico().showScene(stage);
                    }else{
                        new Alert(AlertType.ERROR, "Credenziali errate", ButtonType.OK).show();
                    }
                });


        linkRegister.setOnAction(t -> new RegisterControllerGrafico().showScene(stage));
    }
}
