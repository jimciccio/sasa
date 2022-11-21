package com.intellij.bikerbikus.grafico;

import com.intellij.bikerbikus.controllers1.LezioniController1;
import com.intellij.bikerbikus.utils.LoadScene;
import com.intellij.bikerbikus.data.UserData;
import com.intellij.bikerbikus.models.Lezione;
import com.intellij.bikerbikus.models.TipoLezione;
import com.intellij.bikerbikus.models.Utente;
import com.intellij.bikerbikus.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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

public class LezioniControllerGrafico extends LezioniController1 {

    private Parent parent;
    final Object controller = this;
    int a;

    protected static final ObservableList<Lezione> lezioni = FXCollections.observableArrayList(LezioniController1.lezioniController);
    protected static final ObservableList<Button> bottoni = FXCollections.observableArrayList();

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
    TableView<Lezione> tableLezioni;
    @FXML
    TableColumn<Lezione, LocalDateTime> colGiorno;
    @FXML
    TableColumn<Lezione, LocalDateTime> colOrario;
    @FXML
    TableColumn<Lezione, TipoLezione> colTipo;
    @FXML
    TableColumn<Lezione, Boolean> colPrivata;
    @FXML
    TableColumn<Lezione, Utente> colMaestro;
    @FXML
    TableColumn<Lezione, Integer> colPrenotazione;

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
            colPrenotazione.setText("Elimina");
        } else {
            toolbar.getChildren().remove(btnAddLezione);
        }
        btnNextMonth.setOnAction(event -> {
            lezioni.clear();
            super.onActionNextMonth();
            lblMese.setText(Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
            lblAnno.setText(Integer.toString(getCurrentYearMonth().getYear()));
            lezioni.addAll(LezioniController1.lezioniController);
        });
        btnProfile.setOnAction(event -> new AreaPersonaleControllerGrafico().showScene(stage));
        btnPrevMonth.setOnAction(event -> {
            lezioni.clear();
            super.onActionPrevMonth();
            lblMese.setText(Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
            lblAnno.setText(Integer.toString(getCurrentYearMonth().getYear()));
            lezioni.addAll(LezioniController1.lezioniController);
        });





        setItem();
        setItem1();
        setTable();
        item3();

        lezioni.addListener(new ListChangeListener() { //add an event listerer for the observable list
            @Override
            public void onChanged(ListChangeListener.Change c) { //Method that will execute when any changes occured
                System.out.println("Changes found ...  "); // Show a message that a change occured
            }
        });
    }

    public void setItem(){
        colGiorno.setCellValueFactory(new PropertyValueFactory<>("data"));
        colOrario.setCellFactory(param -> new TableCell<>() {
                    @Override
                    protected void updateItem(LocalDateTime item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item == null ? "" : Utils.formatTime(item.getHour(), item.getMinute()));
                    }
                }
        );
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colGiorno.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? ""
                        : Utils.uppercase(item.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault())) + " "
                        + item.getDayOfMonth());
            }
        });
        colOrario.setCellValueFactory(new PropertyValueFactory<>("data"));
        colTipo.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(TipoLezione item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.getNome());
            }
        });
    }

    public void setItem1(){
        colPrivata.setCellFactory(param -> new TableCell<>() {
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
        colPrivata.setCellValueFactory(new PropertyValueFactory<>("privata"));
        colMaestro.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Utente item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.getNome() + " " + item.getCognome());
            }
        });
        colMaestro.setCellValueFactory(new PropertyValueFactory<>("maestro"));

    }

    public void setTable(){
        colPrenotazione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                final Button btnPrenota = new Button("Prenota");
                btnPrenota.setPrefSize(150, 20);
                if (getTableRow() != null && getTableRow().getItem() != null) {
                    btnPrenota.setDisable(getTableRow().getItem().getData().isBefore(LocalDateTime.now()));
                    if(getTableRow().getItem().getMaestro().getId() == UserData.getInstance().getUser().getId()){
                        btnPrenota.setText("Elimina");
                        btnPrenota.setOnAction(event -> item1(item));
                    }else{
                        btnPrenota.setOnAction(event -> item2(item));
                    }
                }
                setGraphic(item == null ? null : btnPrenota);
            }
        });
        colPrenotazione.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableLezioni.setItems(lezioni);
        if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
            lezioni.clear();
            super.loadLezioniMaestro();
            lezioni.addAll(LezioniController1.lezioniController);
        } else {
            lezioni.clear();
            super.loadLezioni();
            lezioni.addAll(LezioniController1.lezioniController);
        }
    }

    public void item1(int item){
        if(eliminaLezione(item)){
            lezioni.clear();
            new Alert(Alert.AlertType.CONFIRMATION, "Lezione eliminata con successo! Gli utenti che hanno prenotato la lezione saranno avvisati.", ButtonType.OK).show();
            lezioni.addAll(LezioniController1.lezioniController);
        }else{
            new Alert(Alert.AlertType.ERROR, "Non è stato possibile eliminare la lezione.", ButtonType.OK).show();
        }
    }

    public void item2(int item){
        if(prenotaLezione(item)){
            lezioni.clear();
            new Alert(Alert.AlertType.CONFIRMATION, "Prenotazione effettuata con successo!", ButtonType.OK).show();
            lezioni.addAll(LezioniController1.lezioniController);
        }else{
            new Alert(Alert.AlertType.ERROR, "Non è stato possibile prenotare la lezione", ButtonType.OK).show();
        }
    }


    public void item3(){


    }


}