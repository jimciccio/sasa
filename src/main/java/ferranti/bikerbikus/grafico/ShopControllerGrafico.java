package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.controllers1.ShopController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.BiciclettaNoleggio;
import ferranti.bikerbikus.models.BiciclettaVendita;
import ferranti.bikerbikus.utils.LoadScene;
import ferranti.bikerbikus.utils.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ShopControllerGrafico extends ShopController1{

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<BiciclettaVendita> bicicletteVendita = FXCollections.observableArrayList(ShopController1.bicicletteVendita);
    protected static final ObservableList<BiciclettaNoleggio> bicicletteNoleggio = FXCollections.observableArrayList(ShopController1.bicicletteNoleggio);


    @FXML
    Button btnBack;
    @FXML
    HBox toolbar;
    @FXML
    Label lblUserName;
    @FXML
    Label lblTipoUtente;
    @FXML
    Button btnAddItemShop;
    @FXML
    Button btnModify;
    @FXML
    Button btnProfile;

    @FXML
    TableView<BiciclettaVendita> tableNuove;
    @FXML
    TableColumn<BiciclettaVendita, String> colModello;
    @FXML
    TableColumn<BiciclettaVendita, String> colCaratteristiche;
    @FXML
    TableColumn<BiciclettaVendita, Integer> colPrezzo;
    @FXML
    TableColumn<BiciclettaVendita, Integer> colCompra;
    @FXML
    TableView<BiciclettaNoleggio> tableNoleggiate;
    @FXML
    TableColumn<BiciclettaNoleggio, String> colModelloNoleggio;
    @FXML
    TableColumn<BiciclettaNoleggio, String> colCaratteristicheNoleggio;
    @FXML
    TableColumn<BiciclettaNoleggio, Integer> colPrezzoNoleggio;
    @FXML
    TableColumn<BiciclettaNoleggio, Integer> colNoleggio;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/shop-view.fxml");
        btnBack.setOnAction(event -> new HomeControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());

                    bicicletteVendita.clear();
                    bicicletteNoleggio.clear();
                    loadBicicletteComprabili();
                    loadBicicletteNoleggiabili();
                    bicicletteVendita.addAll(ShopController1.bicicletteVendita);
                    bicicletteNoleggio.addAll(ShopController1.bicicletteNoleggio);

        if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
            btnAddItemShop.setOnAction(event -> new AggiungiBiciclettaControllerGrafico().showScene(stage));
            btnModify.setOnAction(event -> new ModificaBiciclettaControllerGrafico().showScene(stage));
        } else {
            toolbar.getChildren().remove(btnAddItemShop);
            toolbar.getChildren().remove(btnModify);
        }
        btnProfile.setOnAction(event -> new AreaPersonaleControllerGrafico().showScene(stage));

        colModello.setCellValueFactory(new PropertyValueFactory<>("modello"));
        colCaratteristiche.setCellValueFactory(new PropertyValueFactory<>("caratteristiche"));
        colPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        colCompra.setCellValueFactory(new PropertyValueFactory<>("button"));

        colModelloNoleggio.setCellValueFactory(new PropertyValueFactory<>("modello"));
        colCaratteristicheNoleggio.setCellValueFactory(new PropertyValueFactory<>("caratteristiche"));
        colPrezzoNoleggio.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        colNoleggio.setCellValueFactory(new PropertyValueFactory<>("button"));

        tableNuove.setItems(bicicletteVendita);
        tableNoleggiate.setItems(bicicletteNoleggio);

        bicicletteVendita.clear();
        bicicletteNoleggio.clear();

        bicicletteVendita.addAll(ShopController1.bicicletteVendita);
        bicicletteNoleggio.addAll(ShopController1.bicicletteNoleggio);
        createButton();
    }



    public static void buy(int item){

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Compra la bicicletta");
        dialog.setHeaderText("Vuoi acquistare la bicicletta?");

        ButtonType loginButtonType = new ButtonType("Compra", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                if(ShopController1.buyBicicletta(item)){
                    bicicletteVendita.clear();
                    new Alert(Alert.AlertType.CONFIRMATION, "Acquisto effettuato con successo.", ButtonType.OK).show();
                    bicicletteVendita.addAll(ShopController1.bicicletteVendita);
                    createButton();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Non è stato possibile effettuare l'acquisto.", ButtonType.OK).show();
                }
            }
            return null;
        });
        dialog.showAndWait();
    }

    public static void rent(int item){

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Scegli noleggio");
        dialog.setHeaderText("Fino a quando desideri noleggiare?");

        ButtonType loginButtonType = new ButtonType("Prenota", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        DatePicker picker = new DatePicker();

        grid.add(new Label("Data:"), 0, 0);
        grid.add(picker, 1, 0);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        picker.valueProperty().addListener((observable, oldValue, newValue) -> loginButton.setDisable(false));


        dialog.getDialogPane().setContent(grid);

        Platform.runLater(picker::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {

                if(ShopController1.rentBicicletta(item, picker.valueProperty().getValue())){
                    bicicletteNoleggio.clear();
                    new Alert(Alert.AlertType.CONFIRMATION, "Noleggio effettuato con successo.", ButtonType.OK).show();
                    bicicletteNoleggio.addAll(ShopController1.bicicletteNoleggio);
                    createButton();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Non è stato possibile effettuare il noleggio.", ButtonType.OK).show();
                }
            }
            return null;
        });

        dialog.showAndWait();
    }


    public static void createButton(){
        for(int i = 0; i< ShopController1.bicicletteVendita.size(); i++) {
            Button compra = new Button("Compra");
            compra.setPrefSize(150, 20);
            bicicletteVendita.get(i).setButton(compra);
            int finalI = i;
            compra.setOnAction(event -> buy(finalI));
        }
        for(int i = 0; i< ShopController1.bicicletteNoleggio.size(); i++) {
            Button noleggia = new Button("Noleggia");
            noleggia.setPrefSize(150, 20);
            bicicletteNoleggio.get(i).setButton(noleggia);

            int finalI = i;
            noleggia.setOnAction(event -> rent(finalI));
        }
    }
}
