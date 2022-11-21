package com.intellij.bikerbikus.grafico;

import com.intellij.bikerbikus.controllers1.ManageUsersController1;
import com.intellij.bikerbikus.utils.LoadScene;
import com.intellij.bikerbikus.data.UserData;
import com.intellij.bikerbikus.models.Utente;
import com.intellij.bikerbikus.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ManageUsersControllerGrafico extends ManageUsersController1 {

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<Utente> utenti = FXCollections.observableArrayList(ManageUsersController1.loadUtenti());


    @FXML
    Button btnBack;
    @FXML
    Label lblUserName;
    @FXML
    TableView<Utente> tableUtenti;
    @FXML
    Label lblTipoUtente;
    @FXML
    TableColumn<Utente, String> colNome;
    @FXML
    TableColumn<Utente, String> colCognome;
    @FXML
    TableColumn<Utente, String> colEmail;
    @FXML
    TableColumn<Utente, Integer> colUpgrade;
    @FXML
    TableColumn<Utente, String> colTipo;


    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/manage-users-view.fxml");
        btnBack.setOnAction(event -> new AreaPersonaleControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoUtente"));
        colUpgrade.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                final Button btnPrenota = new Button("Promuovi");
                btnPrenota.setPrefSize(150, 20);
                if (getTableRow() != null && getTableRow().getItem() != null) {
                    btnPrenota.setOnAction(event -> upgrade(item));
                }
                setGraphic(item == null ? null : btnPrenota);
            }
        });
        colUpgrade.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableUtenti.setItems(utenti);
        utenti.clear();
        loadUtenti();
        utenti.addAll(ManageUsersController1.utenti);
    }

    public static void upgrade(int item){

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Promuovi utente");
        dialog.setHeaderText("Vuoi promuovere l'utente?");

        ButtonType loginButtonType = new ButtonType("Conferma", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                utenti.clear();
                ManageUsersController1.upgradeUser(item);
                utenti.addAll(ManageUsersController1.utenti);
            }
            return null;
        });

        dialog.showAndWait();
    }
}
