package com.intellij.bikerbikus.grafico;

import com.intellij.bikerbikus.controllers1.AggiungiEscursioneController1;
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
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AggiungiEscursioneControllerGrafico extends AggiungiEscursioneController1 {

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<LocalTime> orari = FXCollections.observableArrayList(AggiungiEscursioneController1.loadOrari());
    protected static final ObservableList<Utente> accompagnatori = FXCollections.observableArrayList(AggiungiEscursioneController1.loadAccompagnatori());
    protected static final ObservableList<Luoghi> luoghi = FXCollections.observableArrayList(AggiungiEscursioneController1.loadLuoghi());

    @FXML
    Button btnBack;
    @FXML
    Label lblUserName;
    @FXML
    Label lblTipoUtente;
    @FXML
    ComboBox<LocalTime> cmbOrario;
    @FXML
    DatePicker dpGiorno;
    @FXML
    ComboBox<Luoghi> cmbLuogo;
    @FXML
    ComboBox<Utente> cmbAccompagnatore;
    @FXML
    Button btnConferma;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/aggiungi-escursione-view.fxml");
        btnBack.setOnAction(event -> new EscursioniControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        dpGiorno.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now().plusDays(1);
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
        AggiungiEscursioneController1.loadOrari();
        cmbOrario.setItems(orari);
        AggiungiEscursioneController1.loadAccompagnatori();
        cmbAccompagnatore.setItems(accompagnatori);
        AggiungiEscursioneController1.loadLuoghi();
        cmbLuogo.setItems(luoghi);
        btnConferma.setOnAction(event ->
        {
            if (dpGiorno.getValue() == null || cmbOrario.getValue() == null || cmbLuogo.getValue() == null || cmbAccompagnatore.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Inserisci tutti i dati!").show();
            }else{
                Escursione escursione = new Escursione();
                escursione.setData(LocalDateTime.of(dpGiorno.getValue(), cmbOrario.getValue()));
                escursione.setLuogo(cmbLuogo.getValue());
                escursione.setAccompagnatore(cmbAccompagnatore.getValue());

                if (AggiungiEscursioneController1.onActionConferma(escursione)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Escursione creata con successo!", ButtonType.OK).show();
                    new EscursioniControllerGrafico().showScene(stage);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Non è stato possibile inserire l'escursione").show();
                }
            }
        });
    }
}
