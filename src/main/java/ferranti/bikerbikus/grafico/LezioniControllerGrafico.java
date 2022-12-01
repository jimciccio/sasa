package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.BeanLezioni;
import ferranti.bikerbikus.controllers1.LezioniController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Lezione;
import ferranti.bikerbikus.models.TipoLezione;
import ferranti.bikerbikus.models.Utente;
import ferranti.bikerbikus.utils.Constants;
import ferranti.bikerbikus.utils.LoadScene;
import ferranti.bikerbikus.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class LezioniControllerGrafico extends LezioniController1{

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<BeanLezioni> lezioni = FXCollections.observableArrayList();


    @FXML
    HBox toolbar;
    @FXML
    Label lblUserName;
    @FXML
    Label lblTipoUtente;
    @FXML
    Button btnAddLezione;
    @FXML
    Button btnProfile;
    @FXML
    Button btnPrevMonth;
    @FXML
    Button btnNextMonth;
    @FXML
    Label lblMese;
    @FXML
    Button btnBack;
    @FXML
    Label lblAnno;
    @FXML
    TableView<BeanLezioni> tableLezioni;
    @FXML
    TableColumn<BeanLezioni, LocalDateTime> colGiorno;
    @FXML
    TableColumn<BeanLezioni, LocalDateTime> colOrario;
    @FXML
    TableColumn<BeanLezioni, TipoLezione> colTipo;
    @FXML
    TableColumn<BeanLezioni, String> colPrivata;
    @FXML
    TableColumn<BeanLezioni, Utente> colMaestro;
    @FXML
    TableColumn<BeanLezioni, Button> colPrenotazione;


    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/lezioni-view.fxml");
        lblMese.setText(Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
        lblAnno.setText(Integer.toString(getCurrentYearMonth().getYear()));
        btnBack.setOnAction(event -> new HomeControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
            btnAddLezione.setOnAction(event -> new AggiungiLezioneControllerGrafico().showScene(stage));
        } else {
            toolbar.getChildren().remove(btnAddLezione);
        }

        btnNextMonth.setOnAction(event -> {
            lezioni.clear();
            super.onActionNextMonth();
            lblMese.setText(Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
            lblAnno.setText(Integer.toString(getCurrentYearMonth().getYear()));
            copyLezioni(LezioniController1.lezioniController);
            createButton();

        });
        btnProfile.setOnAction(event -> new AreaPersonaleControllerGrafico().showScene(stage));
        btnPrevMonth.setOnAction(event -> {
            lezioni.clear();
            super.onActionPrevMonth();
            lblMese.setText(Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
            lblAnno.setText(Integer.toString(getCurrentYearMonth().getYear()));
            copyLezioni(LezioniController1.lezioniController);
            createButton();

        });

        colGiorno.setCellValueFactory(new PropertyValueFactory<>("giornoString"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoString"));
        colOrario.setCellValueFactory(new PropertyValueFactory<>("oraString"));
        colPrivata.setCellValueFactory(new PropertyValueFactory<>("privataString"));
        colMaestro.setCellValueFactory(new PropertyValueFactory<>("maestro"));
        setTable();
        createButton();
    }



    public void setTable(){

        colPrenotazione.setCellValueFactory(new PropertyValueFactory<>("buttonLezione"));
        tableLezioni.setItems(lezioni);
        if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
            lezioni.clear();
            super.loadLezioniMaestro();
            copyLezioni(LezioniController1.lezioniController);
            createButton();
        } else {
            lezioni.clear();
            super.loadLezioni();
            copyLezioni(LezioniController1.lezioniController);
            createButton();
        }
    }

    public void item1(int item){
        if(eliminaLezione(item)){
            lezioni.clear();
            new Alert(Alert.AlertType.CONFIRMATION, "Lezione eliminata con successo! Gli utenti che hanno prenotato la lezione saranno avvisati.", ButtonType.OK).show();
            copyLezioni(LezioniController1.lezioniController);
            createButton();
        }else{
            new Alert(Alert.AlertType.ERROR, "Non è stato possibile eliminare la lezione.", ButtonType.OK).show();
        }
    }

    public void item2(int item){
        if(prenotaLezione(item)){
            lezioni.clear();
            new Alert(Alert.AlertType.CONFIRMATION, "Prenotazione effettuata con successo!", ButtonType.OK).show();
            copyLezioni(LezioniController1.lezioniController);
            createButton();
        }else{
            new Alert(Alert.AlertType.ERROR, "Non è stato possibile prenotare la lezione", ButtonType.OK).show();
        }
    }

    public void createButton(){
        for(int i=0; i<LezioniController1.lezioniController.size();i++) {
            Button b = new Button("Prenota");
            b.setPrefSize(150, 20);
            lezioni.get(i).setButtonLezione(b);

            int finalI = i;
            b.setDisable(lezioni.get(i).getData().isBefore(LocalDateTime.now()));
            if(lezioni.get(i).getMaestro().getId() == UserData.getInstance().getUser().getId()){
                b.setText("Elimina");
                b.setOnAction(event -> item1(lezioni.get(finalI).getId()));
            }else{
                b.setOnAction(event -> item2(lezioni.get(finalI).getId()));
            }
        }
    }

    public void copyLezioni(List<Lezione> l){
        for(int i=0; i<l.size();i++){
            BeanLezioni bean = new BeanLezioni();

            bean.setId(l.get(i).getId());
            bean.setData(l.get(i).getData());
            bean.setDateString(l.get(i).getData().format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));
            bean.setMaestro(l.get(i).getMaestro());
            bean.setTipoString(l.get(i).getTipo().getNome());
            bean.setOraString(Utils.formatTime(l.get(i).getData().getHour(), l.get(i).getData().getMinute()));
            bean.setGiornoString(Utils.uppercase(l.get(i).getData().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault())) + " " + l.get(i).getData().getDayOfMonth());

            bean.setPrivataString(Boolean.TRUE.equals(l.get(i).isPrivata()) ? "Si" : "No");
            bean.setEliminata(l.get(i).getEliminata());
            lezioni.add(bean);
        }
    }
}