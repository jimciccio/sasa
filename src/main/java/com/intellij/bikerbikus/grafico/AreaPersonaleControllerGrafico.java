package com.intellij.bikerbikus.grafico;

import com.intellij.bikerbikus.models.*;
import com.intellij.bikerbikus.utils.LoadScene;
import com.intellij.bikerbikus.controllers1.AreaPersonaleController1;
import com.intellij.bikerbikus.data.UserData;
import com.intellij.bikerbikus.utils.Constants;
import com.intellij.bikerbikus.utils.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AreaPersonaleControllerGrafico extends AreaPersonaleController1{

    private Parent parent;
    private static final String CANCEL_1 = "Disdici";
    final Object controller = this;

    protected static final ObservableList<Lezione> lezioni = FXCollections.observableArrayList();
    protected static final ObservableList<Gara> gare = FXCollections.observableArrayList();
    protected static final ObservableList<Escursione> escursioni = FXCollections.observableArrayList();
    protected static final ObservableList<BiciclettaVendita> bicicletteComprate = FXCollections.observableArrayList();
    protected static final ObservableList<BiciclettaNoleggio> bicicletteNoleggiate = FXCollections.observableArrayList();


    @FXML
    HBox toolbar;
    @FXML
    Button btnBack;
    @FXML
    Button btnLogout;
    @FXML
    Button btnManageUsers;
    @FXML
    Label lblUserName;
    @FXML
    Label lblTipoUtente;
    @FXML
    Text txtNome;
    @FXML
    Text txtCognome;
    @FXML
    Text txtEmail;
    @FXML
    Text txtTipoUtente;
    @FXML
    TabPane tabPanePrenotazioni;
    @FXML
    TableView<Lezione> tableLezioni;
    @FXML
    TableColumn<Lezione, LocalDateTime> colGiornoLezione;
    @FXML
    TableColumn<Lezione, LocalDateTime> colOrarioLezione;
    @FXML
    TableColumn<Lezione, TipoLezione> colTipoLezione;
    @FXML
    TableColumn<Lezione, Boolean> colPrivataLezione;
    @FXML
    TableColumn<Lezione, Utente> colMaestroLezione;
    @FXML
    TableColumn<Lezione, Integer> colDisdiciLezione;
    @FXML
    TableView<Gara> tableGare;
    @FXML
    TableColumn<Gara, LocalDateTime> colGiornoGara;
    @FXML
    TableColumn<Gara, LocalDateTime> colOrarioGara;
    @FXML
    TableColumn<Gara, Stagione> colCampionatoGara;
    @FXML
    TableColumn<Gara, Stagione> colStagioneGara;
    @FXML
    TableView<Escursione> tableEscursioni;
    @FXML
    TableColumn<Escursione, LocalDateTime> colGiornoEscursione;
    @FXML
    TableColumn<Escursione, LocalDateTime> colOrarioEscursione;
    @FXML
    TableColumn<Escursione, Luoghi> colLuogoEscursione;
    @FXML
    TableColumn<Escursione, Luoghi> colDifficoltaEscursione;
    @FXML
    TableColumn<Escursione, Utente> colAccompagnatoreEscursione;
    @FXML
    TableColumn<Escursione, Integer> colDisdiciEscursione;
    @FXML
    TableView<BiciclettaVendita> tableBicicletteComprate;
    @FXML
    TableColumn<BiciclettaVendita, String> colModello;
    @FXML
    TableColumn<BiciclettaVendita, String> colCaratteristiche;
    @FXML
    TableColumn<BiciclettaVendita, LocalDateTime> colDataAcquisto;
    @FXML
    TableColumn<BiciclettaVendita, Integer> colPrezzo;
    @FXML
    TableView<BiciclettaNoleggio> tableBicicletteNoleggiate;
    @FXML
    TableColumn<BiciclettaNoleggio, String> colModelloNoleggio;
    @FXML
    TableColumn<BiciclettaNoleggio, String> colCaratteristicheNoleggio;
    @FXML
    TableColumn<BiciclettaNoleggio, LocalDateTime> colInizioNoleggio;
    @FXML
    TableColumn<BiciclettaNoleggio, LocalDateTime> colFineNoleggio;
    @FXML
    TableColumn<BiciclettaNoleggio, Integer> colPrezzoGiorno;
    @FXML
    TableColumn<BiciclettaNoleggio, Integer> colPrezzoTotale;
    @FXML
    TableColumn<BiciclettaNoleggio, Integer> colDisdiciNoleggio;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/area-personale-view.fxml");
        btnBack.setOnAction(event -> new HomeControllerGrafico().showScene(stage));
        btnLogout.setOnAction(event -> new LoginControllerGrafico().showScene(stage));
        if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
            btnManageUsers.setOnAction(event -> new ManageUsersControllerGrafico().showScene(stage));
        } else {
            toolbar.getChildren().remove(btnManageUsers);
        }
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        txtNome.setText(UserData.getInstance().getUser().getNome());
        txtCognome.setText(UserData.getInstance().getUser().getCognome());
        txtEmail.setText(UserData.getInstance().getUser().getEmail());
        txtTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        tabPanePrenotazioni.widthProperty().addListener((observable, oldValue, newValue) -> {
            tabPanePrenotazioni
                    .setTabMinWidth(tabPanePrenotazioni.getWidth() / tabPanePrenotazioni.getTabs().size() - 20);
            tabPanePrenotazioni
                    .setTabMaxWidth(tabPanePrenotazioni.getWidth() / tabPanePrenotazioni.getTabs().size() - 20);
        });
       tabPanePrenotazioni.getSelectionModel().selectedItemProperty()
                .addListener((ov, oldTab, newTab) ->  selected(tabPanePrenotazioni.getSelectionModel().getSelectedItem().getId()));
        colGiornoLezione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));
            }
        });
        colGiornoLezione.setCellValueFactory(new PropertyValueFactory<>("data"));
        colOrarioLezione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : Utils.formatTime(item.getHour(), item.getMinute()));
            }
        });
        colOrarioLezione.setCellValueFactory(new PropertyValueFactory<>("data"));
        colTipoLezione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(TipoLezione item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.getNome());
            }
        });
        colTipoLezione.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        setItem();
        setItem1();
        setItem2();
        setItem3();
        setItem4();
        setItem4Bis();
        setItem5();
    }

    public void setItem(){
        colPrivataLezione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if(item == null){
                    setText("");
                }else{
                    setText(Boolean.TRUE.equals(item) ? "Si" : "No");
                }
            }
        });
        colPrivataLezione.setCellValueFactory(new PropertyValueFactory<>("privata"));
        colMaestroLezione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Utente item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.getNome() + " " + item.getCognome());
            }
        });
        colMaestroLezione.setCellValueFactory(new PropertyValueFactory<>("maestro"));
    }

    public void setItem1(){
        colDisdiciLezione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                final Button btnDisdiciLezione = new Button(CANCEL_1);
                btnDisdiciLezione.setPrefSize(150, 20);
                btnDisdiciLezione.setOnAction(event -> {

                    Optional<ButtonType> option = new Alert(Alert.AlertType.CONFIRMATION, "Confermi di voler annullare la prenotazione?",
                            ButtonType.NO, ButtonType.YES).showAndWait();
                    if (option.isPresent() && option.get() == ButtonType.YES) {
                        lezioni.clear();
                        AreaPersonaleController1.disdiciLezione(item);
                        lezioni.addAll(AreaPersonaleController1.lezioni);
                    }

                });
                if (getTableRow() != null && getTableRow().getItem() != null) {
                    btnDisdiciLezione.setDisable(getTableRow().getItem().getData().isBefore(LocalDateTime.now()));
                }
                setGraphic(item == null ? null : btnDisdiciLezione);
            }
        });
        colDisdiciLezione.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    public void setItem2(){
        if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
            tabPanePrenotazioni.getTabs().remove(0);
        }else{
            tableLezioni.setItems( lezioni);

        }
        colGiornoGara.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));
            }
        });
        colGiornoGara.setCellValueFactory(new PropertyValueFactory<>("data"));
        colOrarioGara.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : Utils.formatTime(item.getHour(), item.getMinute()));
            }
        });
        colOrarioGara.setCellValueFactory(new PropertyValueFactory<>("data"));
        colCampionatoGara.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Stagione item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.getCampionato().getNome());
            }
        });
        colCampionatoGara
                .setCellValueFactory(cellData -> new SimpleObjectProperty<Stagione>(cellData.getValue().getStagione()));

        colStagioneGara.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Stagione item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.getNome());
            }
        });
        colStagioneGara.setCellValueFactory(new PropertyValueFactory<>("stagione"));
    }

    public void setItem3(){
        tableGare.setItems( gare);
        colGiornoEscursione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));
            }
        });
        colGiornoEscursione.setCellValueFactory(new PropertyValueFactory<>("data"));
        colOrarioEscursione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : Utils.formatTime(item.getHour(), item.getMinute()));
            }
        });
        colOrarioEscursione.setCellValueFactory(new PropertyValueFactory<>("data"));
        colLuogoEscursione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Luoghi item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.getNome());
            }
        });
        colLuogoEscursione.setCellValueFactory(new PropertyValueFactory<>("luogo"));
        colDifficoltaEscursione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Luoghi item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : String.valueOf(item.getDifficolta()));
            }
        });
        colDifficoltaEscursione.setCellValueFactory(new PropertyValueFactory<>("luogo"));
        colAccompagnatoreEscursione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Utente item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.getNome() + " " + item.getCognome());
            }
        });
        colAccompagnatoreEscursione.setCellValueFactory(new PropertyValueFactory<>("accompagnatore"));
    }

    public void setItem4(){
        colDisdiciEscursione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                final Button btnDisdiciEscursione = new Button(CANCEL_1);
                btnDisdiciEscursione.setPrefSize(150, 20);
                btnDisdiciEscursione.setOnAction(event -> {

                    Optional<ButtonType> option = new Alert(Alert.AlertType.CONFIRMATION, "Confermi di voler annullare la prenotazione?",
                            ButtonType.NO, ButtonType.YES).showAndWait();
                    if (option.isPresent() && option.get() == ButtonType.YES) {
                        escursioni.clear();
                        AreaPersonaleController1.disdiciEscursione(item);
                        escursioni.addAll(AreaPersonaleController1.escursioni);
                    }
                });
                if (getTableRow() != null && getTableRow().getItem() != null) {
                    btnDisdiciEscursione.setDisable(getTableRow().getItem().getData().isBefore(LocalDateTime.now()));

                }
                setGraphic(item == null ? null :btnDisdiciEscursione);
            }
        });
        colDisdiciEscursione.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableEscursioni.setItems( escursioni);
        colModello.setCellValueFactory(new PropertyValueFactory<>("modello"));
        colCaratteristiche.setCellValueFactory(new PropertyValueFactory<>("caratteristiche"));
        colDataAcquisto.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));
            }
        });
        colDataAcquisto.setCellValueFactory(new PropertyValueFactory<>("dataAcquisto"));
        colPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        tableBicicletteComprate.setItems(bicicletteComprate);
        colModelloNoleggio.setCellValueFactory(new PropertyValueFactory<>("modello"));
        colCaratteristicheNoleggio.setCellValueFactory(new PropertyValueFactory<>("caratteristiche"));

    }

    public void setItem4Bis(){
        colInizioNoleggio.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));
            }
        });
        colInizioNoleggio.setCellValueFactory(new PropertyValueFactory<>("InizioNoleggio"));
    }

    public void setItem5(){
        colFineNoleggio.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));
            }
        });
        colFineNoleggio.setCellValueFactory(new PropertyValueFactory<>("FineNoleggio"));
        colPrezzoGiorno.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        colPrezzoTotale.setCellValueFactory(new PropertyValueFactory<>("prezzoFinale"));
        tableBicicletteNoleggiate.setItems( bicicletteNoleggiate);
        colDisdiciNoleggio.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                final Button btnDisdiciNoleggio = new Button(CANCEL_1);
                btnDisdiciNoleggio.setPrefSize(150, 20);
                btnDisdiciNoleggio.setOnAction(event -> {

                    Optional<ButtonType> option = new Alert(Alert.AlertType.CONFIRMATION, "Confermi di voler annullare il noleggio?",
                            ButtonType.NO, ButtonType.YES).showAndWait();
                    if (option.isPresent() && option.get() == ButtonType.YES) {
                        bicicletteNoleggiate.clear();
                        AreaPersonaleController1.disdiciNoleggio(item);
                        bicicletteNoleggiate.addAll(AreaPersonaleController1.bicicletteNoleggiate);
                    }
                });
                if (getTableRow() != null && getTableRow().getItem() != null) {
                    btnDisdiciNoleggio.setDisable(getTableRow().getItem().getFineNoleggio().isBefore(LocalDateTime.now()));
                }
                setGraphic(item == null ? null :btnDisdiciNoleggio);
            }
        });
        colDisdiciNoleggio.setCellValueFactory(new PropertyValueFactory<>("idNoleggio"));
        selected(tabPanePrenotazioni.getSelectionModel().getSelectedItem().getId());
    }

    public void selected(String id){
        switch (id) {
            case "tabLezioni": {
                lezioni.clear();
                AreaPersonaleController1.showLezioni();
                lezioni.addAll(AreaPersonaleController1.lezioni);
                return;
            }
            case "tabGare": {
                gare.clear();
                AreaPersonaleController1.showGare();
                gare.addAll(AreaPersonaleController1.gare);
                return;
            }
            case "tabEscursioni": {
                escursioni.clear();
                AreaPersonaleController1.showEscursioni();
                escursioni.addAll(AreaPersonaleController1.escursioni);
                return;
            }
            case "tabBicicletteComprate": {
                bicicletteComprate.clear();
                AreaPersonaleController1.showBiciComprate();
                bicicletteComprate.addAll(AreaPersonaleController1.bicicletteComprate);
                return;
            }
            case "tabBicicletteNoleggiate": {
                bicicletteNoleggiate.clear();
                AreaPersonaleController1.showBiciNoleggiate();
                bicicletteNoleggiate.addAll(AreaPersonaleController1.bicicletteNoleggiate);
                return;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + id);
        }
    }
}
