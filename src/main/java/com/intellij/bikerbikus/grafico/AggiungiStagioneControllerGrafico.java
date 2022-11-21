package com.intellij.bikerbikus.grafico;

import com.intellij.bikerbikus.data.UserData;
import com.intellij.bikerbikus.models.Campionato;
import com.intellij.bikerbikus.models.Stagione;
import com.intellij.bikerbikus.utils.LoadScene;
import com.intellij.bikerbikus.utils.Utils;
import com.intellij.bikerbikus.controllers1.AggiungiStagioneController1;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class AggiungiStagioneControllerGrafico extends AggiungiStagioneController1{

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<Campionato> campionati = FXCollections.observableArrayList(AggiungiStagioneController1.loadCampionati());


    @FXML
    Button btnBack;
    @FXML
    Label lblUserName;
    @FXML
    Label lblTipoUtente;
    @FXML
    DatePicker dpInizio;
    @FXML
    DatePicker dpFine;
    @FXML
    ComboBox<Campionato> cmbCampionato;
    @FXML
    Button btnConferma;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/aggiungi-stagione-view.fxml");
        btnBack.setOnAction(event -> new CampionatiControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        AggiungiStagioneController1.loadCampionati();
        cmbCampionato.setItems(campionati);
        dpInizio.setDisable(false);
        dpInizio.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
                dpFine.setDisable(false);
                dpFine.setDayCellFactory(picker -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        LocalDate today = LocalDate.now();
                        setDisable(empty || date.compareTo(today) < 0 || date.compareTo(dpInizio.getValue().plusDays(1)) < 0);
                    }
                });
            }
        });

        btnConferma.setOnAction(event ->
        {
            if (dpInizio.getValue() == null || dpFine.getValue() == null || cmbCampionato.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Inserisci tutti i dati!").show();
            }else{
                Stagione stagione = new Stagione();
                stagione.setNome(Integer.toString(dpInizio.getValue().getYear()));
                stagione.setDataInizio(dpInizio.getValue());
                stagione.setDataFine(dpFine.getValue());
                stagione.setCampionato(cmbCampionato.getValue());
                if (AggiungiStagioneController1.onActionConferma(stagione)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Stagione creata con successo!", ButtonType.OK).show();
                    new CampionatiControllerGrafico().showScene(stage);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Non è stato possibile inserire la stagione").show();
                }
            }
        });
    }
}
