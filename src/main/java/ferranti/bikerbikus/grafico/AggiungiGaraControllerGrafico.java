package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.controllers1.AggiungiGaraController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Gara;
import ferranti.bikerbikus.models.Stagione;
import ferranti.bikerbikus.utils.LoadScene;
import ferranti.bikerbikus.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AggiungiGaraControllerGrafico extends AggiungiGaraController1{

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<LocalTime> orari = FXCollections.observableArrayList(AggiungiGaraController1.loadOrari());
    protected static final ObservableList<Stagione> stagioni = FXCollections.observableArrayList(AggiungiGaraController1.loadStagioni());

    @FXML
    ComboBox<LocalTime> cmbOrario;
    @FXML
    Button btnBack;
    @FXML
    Label lblUserName;
    @FXML
    Label lblTipoUtente;
    @FXML
    DatePicker dpGiorno;
    @FXML
    ComboBox<Stagione> cmbStagione;
    @FXML
    Button btnConferma;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/aggiungi-gara-view.fxml");
        btnBack.setOnAction(event -> new GareControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        AggiungiGaraController1.loadOrari();
        cmbOrario.setItems(orari);
        AggiungiGaraController1.loadStagioni();
        cmbStagione.setItems(stagioni);
        cmbStagione.setOnAction(event -> {
            cmbStagione.getSelectionModel().getSelectedItem();
            dpGiorno.setDisable(false);
        });

        btnConferma.setOnAction(event ->
        {
            if (dpGiorno.getValue() == null || cmbOrario.getValue() == null || cmbStagione.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Inserisci tutti i dati!").show();
            }else{
                Gara gara = new Gara();
                gara.setStagione( cmbStagione.getValue());
                gara.setData(LocalDateTime.of(dpGiorno.getValue(), cmbOrario.getValue()));
                if (AggiungiGaraController1.onActionConferma(gara)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Gara creata con successo!", ButtonType.OK).show();
                    new GareControllerGrafico().showScene(stage);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Non è stato possibile inserire la gara").show();
                }
            }
        });
    }
}
