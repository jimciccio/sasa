package com.intellij.bikerbikus.grafico;

import com.intellij.bikerbikus.controllers1.EscursioniController1;
import com.intellij.bikerbikus.utils.LoadScene;
import com.intellij.bikerbikus.data.UserData;
import com.intellij.bikerbikus.models.Escursione;
import com.intellij.bikerbikus.models.Luoghi;
import com.intellij.bikerbikus.models.Utente;
import com.intellij.bikerbikus.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class EscursioniControllerGrafico extends EscursioniController1 {

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<Escursione> escursioni = FXCollections.observableArrayList(EscursioniController1.escursioni);


    @FXML
    Label lblTipoUtente;
    @FXML
    HBox toolbar;
    @FXML
    Button btnBack;
    @FXML
    Label lblUserName;
    @FXML
    Button btnAddEscursione;
    @FXML
    Button btnProfile;
    @FXML
    Button btnPrevMonth;
    @FXML
    Button btnNextMonth;
    @FXML
    Button btnRecensioni;
    @FXML
    Label lblMese;
    @FXML
    Label lblAnno;
    @FXML
    TableView<Escursione> tableEscursioni;
    @FXML
    TableColumn<Escursione, LocalDateTime> colGiorno;
    @FXML
    TableColumn<Escursione, LocalDateTime> colOrario;
    @FXML
    TableColumn<Escursione, Luoghi> colLuogo;
    @FXML
    TableColumn<Escursione, Luoghi> colDifficolta;
    @FXML
    TableColumn<Escursione, Utente> colAccompagnatore;
    @FXML
    TableColumn<Escursione, Integer> colPrenotazione;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/escursioni-view.fxml");
        lblMese.setText(
                Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
        lblAnno.setText(Integer.toString(getCurrentYearMonth().getYear()));
        btnBack.setOnAction(event -> new HomeControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
            btnAddEscursione.setOnAction(event -> new AggiungiEscursioneControllerGrafico().showScene(stage));
        } else {
            toolbar.getChildren().remove(btnAddEscursione);
        }
        btnProfile.setOnAction(event -> new AreaPersonaleControllerGrafico().showScene(stage));
        btnPrevMonth.setOnAction(event -> {
            escursioni.clear();
            super.onActionPrevMonth();
            lblMese.setText(Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
            lblAnno.setText(Integer.toString(getCurrentYearMonth().getYear()));
            escursioni.addAll(EscursioniController1.escursioni);
        });
        btnNextMonth.setOnAction(event -> {
            escursioni.clear();
            super.onActionNextMonth();
            lblMese.setText(Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
            lblAnno.setText(Integer.toString(getCurrentYearMonth().getYear()));
            escursioni.addAll(EscursioniController1.escursioni);
        });
        btnRecensioni.setOnAction(event -> new RecensioniControllerGrafico().showScene(stage));
        colGiorno.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? ""
                        : Utils.uppercase(item.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault())) + " "
                        + item.getDayOfMonth());
            }
        });
        colGiorno.setCellValueFactory(new PropertyValueFactory<>("data"));
        colOrario.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : Utils.formatTime(item.getHour(), item.getMinute()));
            }
        });
        colOrario.setCellValueFactory(new PropertyValueFactory<>("data"));
        colLuogo.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Luoghi item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.getNome());
            }
        });
        colLuogo.setCellValueFactory(new PropertyValueFactory<>("luogo"));
        colDifficolta.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Luoghi item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : String.valueOf(item.getDifficolta()));
            }
        });
        colDifficolta.setCellValueFactory(new PropertyValueFactory<>("luogo"));

        setItem();

    }

    public void setItem(){
        colAccompagnatore.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Utente item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.getNome() + " " + item.getCognome());
            }
        });
        colAccompagnatore.setCellValueFactory(new PropertyValueFactory<>("accompagnatore"));
        colPrenotazione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                final Button btnPrenotaEscursione = new Button("Prenota");
                btnPrenotaEscursione.setPrefSize(150, 20);
                if (getTableRow() != null && getTableRow().getItem() != null) {
                    btnPrenotaEscursione.setDisable(getTableRow().getItem().getData().isBefore(LocalDateTime.now()));
                    if(getTableRow().getItem().getAccompagnatore().getId() == UserData.getInstance().getUser().getId()){
                        btnPrenotaEscursione.setText("Elimina");
                        btnPrenotaEscursione.setOnAction(event -> item1(item));
                    }else{
                        btnPrenotaEscursione.setOnAction(event -> item2(item));
                    }
                }
                setGraphic(item == null ? null : btnPrenotaEscursione);
            }
        });
        colPrenotazione.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableEscursioni.setItems(escursioni);
        escursioni.clear();
        super.loadEscursioni();
        escursioni.addAll(EscursioniController1.escursioni);
    }

    public void item1(int item){
        if(eliminaEscursione(item)){
            escursioni.clear();
            new Alert(Alert.AlertType.CONFIRMATION, "Escursione eliminata con successo! Tutti gli utenti verranno avvisati", ButtonType.OK).show();
            escursioni.addAll(EscursioniController1.escursioni);
        }else{
            new Alert(Alert.AlertType.ERROR, "Non è stato possibile eliminare la lezione. Riprovare più tardi.", ButtonType.OK).show();
        }
    }

    public void item2(int item){
        if(prenotaEscursione(item)){
            escursioni.clear();
            new Alert(Alert.AlertType.CONFIRMATION, "Prenotazione effettuata con successo!", ButtonType.OK).show();
            escursioni.addAll(EscursioniController1.escursioni);
        }else{
            new Alert(Alert.AlertType.ERROR, "Non è stato possibile prenotare l'escursione!", ButtonType.OK).show();
        }
    }
}
