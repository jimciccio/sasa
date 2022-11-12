package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.controllers1.RegisterController1;
import ferranti.bikerbikus.utils.LoadScene;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterControllerGrafico {

    private Parent parent;
    final Object controller = this;

    @FXML
    TextField txtNome;
    @FXML
    TextField txtCognome;
    @FXML
    TextField txtEmail;
    @FXML
    PasswordField txtPassword;
    @FXML
    Hyperlink linkLogin;
    @FXML
    Button btnRegister;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/register-view.fxml");
        linkLogin.setOnAction(
                t -> new LoginControllerGrafico().showScene(stage));
        btnRegister.setOnAction(
                t -> {

                    if( RegisterController1.onActionRegister(txtNome.getText(), txtCognome.getText(), txtEmail.getText(), txtPassword.getText())){
                        new Alert(Alert.AlertType.CONFIRMATION, "Utente Creato", ButtonType.OK).showAndWait();
                        new HomeControllerGrafico().showScene(stage);
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Mancano alcune credenziali", ButtonType.OK).show();
                    }
                });
    }
}
