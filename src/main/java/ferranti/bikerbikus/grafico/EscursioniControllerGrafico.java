package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.BeanEscursioni;
import ferranti.bikerbikus.controllers1.EscursioniController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Escursione;
import ferranti.bikerbikus.utils.LoadScene;
import ferranti.bikerbikus.utils.Utils;
import javafx.beans.property.SimpleObjectProperty;
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
import java.util.List;
import java.util.Locale;

public class EscursioniControllerGrafico extends EscursioniController1 {

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<BeanEscursioni> escursioni = FXCollections.observableArrayList();


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
    TableView<BeanEscursioni> tableEscursioni;
    @FXML
    TableColumn<BeanEscursioni, String> colGiorno;
    @FXML
    TableColumn<BeanEscursioni, String> colOrario;
    @FXML
    TableColumn<BeanEscursioni, String> colLuogo;
    @FXML
    TableColumn<BeanEscursioni, String> colDifficolta;
    @FXML
    TableColumn<BeanEscursioni, String> colAccompagnatore;
    @FXML
    TableColumn<BeanEscursioni, Button> colPrenotazione;

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
            copyEscursioni(EscursioniController1.escursioni);
            createButton();
        });
        btnNextMonth.setOnAction(event -> {
            escursioni.clear();
            super.onActionNextMonth();
            lblMese.setText(Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
            lblAnno.setText(Integer.toString(getCurrentYearMonth().getYear()));
            copyEscursioni(EscursioniController1.escursioni);
            createButton();
        });
        btnRecensioni.setOnAction(event -> new RecensioniControllerGrafico().showScene(stage));

        colGiorno.setCellValueFactory(cellData -> new SimpleObjectProperty<>(Utils.uppercase(cellData.getValue().getData().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault())) + " "
                + cellData.getValue().getData().getDayOfMonth()));

        colOrario.setCellValueFactory(cellData -> new SimpleObjectProperty<>(Utils.formatTime(cellData.getValue().getData().getHour(), cellData.getValue().getData().getMinute())));

        colLuogo.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getLuogo().getNome()));
        colDifficolta.setCellValueFactory(cellData -> new SimpleObjectProperty<>(String.valueOf(cellData.getValue().getLuogo().getDifficolta())));

        colAccompagnatore.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAccompagnatore().getNome() + " " + cellData.getValue().getAccompagnatore().getCognome()));

        colPrenotazione.setCellValueFactory(new PropertyValueFactory<>("buttonEscursione"));
        tableEscursioni.setItems(escursioni);
        escursioni.clear();
        super.loadEscursioni();
        copyEscursioni(EscursioniController1.escursioni);
        createButton();
    }



    public void item1(int item){
        if(eliminaEscursione(item)){
            escursioni.clear();
            new Alert(Alert.AlertType.CONFIRMATION, "Escursione eliminata con successo! Tutti gli utenti verranno avvisati", ButtonType.OK).show();
            copyEscursioni(EscursioniController1.escursioni);
            createButton();
        }else{
            new Alert(Alert.AlertType.ERROR, "Non è stato possibile eliminare la lezione. Riprovare più tardi.", ButtonType.OK).show();
        }
    }

    public void item2(int item){
        if(prenotaEscursione(item)){
            escursioni.clear();
            new Alert(Alert.AlertType.CONFIRMATION, "Prenotazione effettuata con successo!", ButtonType.OK).show();
            copyEscursioni(EscursioniController1.escursioni);
            createButton();
        }else{
            new Alert(Alert.AlertType.ERROR, "Non è stato possibile prenotare l'escursione!", ButtonType.OK).show();
        }
    }

    public void createButton(){
        for(int i = 0; i< EscursioniController1.escursioni.size(); i++) {
            Button b = new Button("Prenota");
            b.setPrefSize(150, 20);
            escursioni.get(i).setButtonEscursione(b);

            int finalI = i;
            b.setDisable(escursioni.get(i).getData().isBefore(LocalDateTime.now()));
            if(escursioni.get(i).getAccompagnatore().getId() == UserData.getInstance().getUser().getId()){
                b.setText("Elimina");
                b.setOnAction(event -> item1(escursioni.get(finalI).getId()));
            }else{
                b.setOnAction(event -> item2(escursioni.get(finalI).getId()));
            }
        }
    }

    public void copyEscursioni(List<Escursione> l){
        for(int i=0; i<l.size();i++){
            BeanEscursioni bean = new BeanEscursioni();

            bean.setId(l.get(i).getId());
            bean.setData(l.get(i).getData());
            bean.setLuogo(l.get(i).getLuogo());
            bean.setAccompagnatore(l.get(i).getAccompagnatore());
            escursioni.add(bean);
        }
    }
}