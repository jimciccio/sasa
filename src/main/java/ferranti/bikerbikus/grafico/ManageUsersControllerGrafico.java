package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.controllers1.ManageUsersController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Utente;
import ferranti.bikerbikus.utils.LoadScene;
import ferranti.bikerbikus.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ManageUsersControllerGrafico extends ManageUsersController1{

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

        colUpgrade.setCellValueFactory(new PropertyValueFactory<>("button"));
        tableUtenti.setItems(utenti);
        utenti.clear();
        loadUtenti();
        utenti.addAll(ManageUsersController1.utenti);
        createButton();
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
                createButton();
            }
            return null;
        });
        dialog.showAndWait();
    }

    public static void createButton(){
        for(int i = 0; i< ManageUsersController1.utenti.size(); i++) {
            Button b = new Button("Promuovi");
            b.setPrefSize(150, 20);
            utenti.get(i).setButton(b);

            int finalI = i;
            b.setOnAction(event -> upgrade(utenti.get(finalI).getId()));
        }
    }
}
