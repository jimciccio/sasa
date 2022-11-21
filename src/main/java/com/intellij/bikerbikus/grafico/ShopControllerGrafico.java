package com.intellij.bikerbikus.grafico;

import com.intellij.bikerbikus.controllers1.ShopController1;
import com.intellij.bikerbikus.utils.LoadScene;
import com.intellij.bikerbikus.data.UserData;
import com.intellij.bikerbikus.models.BiciclettaNoleggio;
import com.intellij.bikerbikus.models.BiciclettaVendita;
import com.intellij.bikerbikus.utils.Utils;
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
import java.time.LocalDate;

public class ShopControllerGrafico extends ShopController1 {

    private Parent parent;
    final Object controller = this;
    String compra = "Compra";

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
    TabPane tabPaneBici;
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
        tabPaneBici.widthProperty().addListener((observable, oldValue, newValue) -> {
            tabPaneBici
                    .setTabMinWidth(tabPaneBici.getWidth() / tabPaneBici.getTabs().size() - 20);
            tabPaneBici
                    .setTabMaxWidth(tabPaneBici.getWidth() / tabPaneBici.getTabs().size() - 20);
        });
        tabPaneBici.getSelectionModel().selectedItemProperty()
                .addListener((ov, oldTab, newTab) -> {
                    bicicletteVendita.clear();
                    bicicletteNoleggio.clear();
                    ShopController1.loadBiciclette(tabPaneBici.getSelectionModel().getSelectedItem().getId());
                    bicicletteVendita.addAll(ShopController1.bicicletteVendita);
                    bicicletteNoleggio.addAll(ShopController1.bicicletteNoleggio);
                });
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
        colCompra.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                final Button btnPrenota = new Button(compra);
                btnPrenota.setPrefSize(150, 20);
                if (getTableRow() != null && getTableRow().getItem() != null) {
                        btnPrenota.setText(compra);
                        btnPrenota.setOnAction(event -> buy(getTableRow().getIndex()));
                }
                setGraphic(item == null ? null : btnPrenota);
            }
        });
        colCompra.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableNuove.setItems(bicicletteVendita);
        colModelloNoleggio.setCellValueFactory(new PropertyValueFactory<>("modello"));
        colCaratteristicheNoleggio.setCellValueFactory(new PropertyValueFactory<>("caratteristiche"));
        colPrezzoNoleggio.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        item();

        tableNoleggiate.setItems(bicicletteNoleggio);
        bicicletteVendita.clear();
        bicicletteNoleggio.clear();
        ShopController1.loadBiciclette(tabPaneBici.getSelectionModel().getSelectedItem().getId());
        bicicletteVendita.addAll(ShopController1.bicicletteVendita);
    }

    public  void item(){
        colNoleggio.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                final Button btnPrenota = new Button(compra);
                btnPrenota.setPrefSize(150, 20);
                if (getTableRow() != null && getTableRow().getItem() != null) {
                    btnPrenota.setText("Noleggia");
                    btnPrenota.setOnAction(event -> rent(getTableRow().getIndex()));
                }
                setGraphic(item == null ? null : btnPrenota);
            }
        });
        colNoleggio.setCellValueFactory(new PropertyValueFactory<>("id"));
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

        picker.setDayCellFactory(picker1 -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(picker::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {

                if(ShopController1.rentBicicletta(item, picker.valueProperty().getValue())){
                    bicicletteNoleggio.clear();
                    new Alert(Alert.AlertType.CONFIRMATION, "Noleggio effettuato con successo.", ButtonType.OK).show();
                    bicicletteNoleggio.addAll(ShopController1.bicicletteNoleggio);
                }else{
                    new Alert(Alert.AlertType.ERROR, "Non è stato possibile effettuare il noleggio.", ButtonType.OK).show();
                }
            }
            return null;
        });

        dialog.showAndWait();
    }
}
