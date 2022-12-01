package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.controllers1.AggiungiLezioneController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Lezione;
import ferranti.bikerbikus.models.TipoLezione;
import ferranti.bikerbikus.models.Utente;
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

public class AggiungiLezioneControllerGrafico extends AggiungiLezioneController1{

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<LocalTime> orari = FXCollections.observableArrayList(AggiungiLezioneController1.loadOrari());
    protected static final ObservableList<TipoLezione> tipi = FXCollections.observableArrayList(AggiungiLezioneController1.loadTipi());
    protected static final ObservableList<Utente> maestri = FXCollections.observableArrayList(AggiungiLezioneController1.loadMaestri());

    @FXML
    Button btnBack;
    @FXML
    Label lblUserName;
    @FXML
    Label lblTipoUtente;
    @FXML
    DatePicker dpGiorno;
    @FXML
    ComboBox<LocalTime> cmbOrario;
    @FXML
    ComboBox<TipoLezione> cmbTipo;
    @FXML
    CheckBox cbPrivata;
    @FXML
    ComboBox<Utente> cmbMaestro;
    @FXML
    Button btnConferma;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/aggiungi-lezione-view.fxml");
        btnBack.setOnAction(event -> new LezioniControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        AggiungiLezioneController1.loadOrari();
        cmbOrario.setItems(orari);
        AggiungiLezioneController1.loadTipi();
        cmbTipo.setItems(tipi);
        AggiungiLezioneController1.loadMaestri();
        cmbMaestro.setItems(maestri);
        btnConferma.setOnAction(event ->
        {
            if (dpGiorno.getValue() == null || cmbOrario.getValue() == null || cmbTipo.getValue() == null || cmbMaestro.getValue() == null) {
                new Alert(Alert.AlertType.WARNING, "Inserisci tutti i dati!").show();
            }else{

                Lezione lezione = new Lezione();
                lezione.setData(LocalDateTime.of(dpGiorno.getValue(), cmbOrario.getValue()));
                lezione.setTipo(cmbTipo.getValue());
                lezione.setPrivata(cbPrivata.isSelected());
                lezione.setMaestro(cmbMaestro.getValue());

                if (AggiungiLezioneController1.onActionConferma(lezione)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Lezione creata con successo!", ButtonType.OK).show();
                    new LezioniControllerGrafico().showScene(stage);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Non è stato possibile inserire la lezione").show();
                }
            }
        });
    }
}
