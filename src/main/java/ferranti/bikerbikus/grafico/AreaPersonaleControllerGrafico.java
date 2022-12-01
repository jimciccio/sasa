package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.BeanEscursioni;
import ferranti.bikerbikus.BeanLezioni;
import ferranti.bikerbikus.controllers1.AreaPersonaleController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.*;
import ferranti.bikerbikus.utils.Constants;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class AreaPersonaleControllerGrafico extends AreaPersonaleController1{

    private Parent parent;
    private static final String CANCEL_1 = "Disdici";
    final Object controller = this;

    protected static final ObservableList<BeanLezioni> lezioni = FXCollections.observableArrayList();
    protected static final ObservableList<Gara> gare = FXCollections.observableArrayList();
    protected static final ObservableList<BeanEscursioni> escursioni = FXCollections.observableArrayList();
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
    TableView<BeanLezioni> tableLezioni;
    @FXML
    TableColumn<BeanLezioni, LocalDateTime> colGiornoLezione;
    @FXML
    TableColumn<BeanLezioni, LocalDateTime> colOrarioLezione;
    @FXML
    TableColumn<BeanLezioni, TipoLezione> colTipoLezione;
    @FXML
    TableColumn<BeanLezioni, Boolean> colPrivataLezione;
    @FXML
    TableColumn<BeanLezioni, Utente> colMaestroLezione;
    @FXML
    TableColumn<BeanLezioni, Integer> colDisdiciLezione;
    @FXML
    TableView<Gara> tableGare;
    @FXML
    TableColumn<Gara, String> colGiornoGara;
    @FXML
    TableColumn<Gara, String> colOrarioGara;
    @FXML
    TableColumn<Gara, String> colCampionatoGara;
    @FXML
    TableColumn<Gara, String> colStagioneGara;
    @FXML
    TableView<BeanEscursioni> tableEscursioni;
    @FXML
    TableColumn<BeanEscursioni, LocalDateTime> colGiornoEscursione;
    @FXML
    TableColumn<BeanEscursioni, LocalDateTime> colOrarioEscursione;
    @FXML
    TableColumn<BeanEscursioni, Luoghi> colLuogoEscursione;
    @FXML
    TableColumn<BeanEscursioni, Luoghi> colDifficoltaEscursione;
    @FXML
    TableColumn<BeanEscursioni, Utente> colAccompagnatoreEscursione;
    @FXML
    TableColumn<BeanEscursioni, Integer> colDisdiciEscursione;
    @FXML
    TableView<BiciclettaVendita> tableBicicletteComprate;
    @FXML
    TableColumn<BiciclettaVendita, String> colModello;
    @FXML
    TableColumn<BiciclettaVendita, String> colCaratteristiche;
    @FXML
    TableColumn<BiciclettaVendita, String> colDataAcquisto;
    @FXML
    TableColumn<BiciclettaVendita, Integer> colPrezzo;
    @FXML
    TableView<BiciclettaNoleggio> tableBicicletteNoleggiate;
    @FXML
    TableColumn<BiciclettaNoleggio, String> colModelloNoleggio;
    @FXML
    TableColumn<BiciclettaNoleggio, String> colCaratteristicheNoleggio;
    @FXML
    TableColumn<BiciclettaNoleggio, String> colInizioNoleggio;
    @FXML
    TableColumn<BiciclettaNoleggio, String> colFineNoleggio;
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

        colGiornoLezione.setCellValueFactory(new PropertyValueFactory<>("giornoString"));

        colOrarioLezione.setCellValueFactory(new PropertyValueFactory<>("oraString"));

        colTipoLezione.setCellValueFactory(new PropertyValueFactory<>("tipoString"));
        setItem();
        setItem1();
        setItem2();
        setItem3();
        setItem4();
        setItem4Bis();
        setItem5();
    }

    public void setItem(){

        colPrivataLezione.setCellValueFactory(new PropertyValueFactory<>("privataString"));
        colMaestroLezione.setCellValueFactory(new PropertyValueFactory<>("maestro"));
    }

    public void setItem1(){

        colDisdiciLezione.setCellValueFactory(new PropertyValueFactory<>("buttonLezione"));
    }

    public void setItem2(){
        if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
            tabPanePrenotazioni.getTabs().remove(0);
        }else{
            tableLezioni.setItems( lezioni);

        }
        colGiornoGara.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getData().format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN))));
        colOrarioGara.setCellValueFactory(cellData -> new SimpleObjectProperty<>(Utils.formatTime(cellData.getValue().getData().getHour(), cellData.getValue().getData().getMinute())));


        colCampionatoGara.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStagione().getCampionato().getNome()));


        colStagioneGara.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStagione().getNome()));

    }

    public void setItem3(){
        tableGare.setItems( gare);

        colGiornoEscursione.setCellValueFactory(new PropertyValueFactory<>("giornoString"));
        colOrarioEscursione.setCellValueFactory(new PropertyValueFactory<>("oraString"));
        colLuogoEscursione.setCellValueFactory(new PropertyValueFactory<>("luogo"));
        colDifficoltaEscursione.setCellValueFactory(new PropertyValueFactory<>("difficoltaString"));
        colAccompagnatoreEscursione.setCellValueFactory(new PropertyValueFactory<>("accompagnatoreString"));
    }

    public void setItem4(){

        colDisdiciEscursione.setCellValueFactory(new PropertyValueFactory<>("buttonEscursione"));
        tableEscursioni.setItems( escursioni);
        colModello.setCellValueFactory(new PropertyValueFactory<>("modello"));
        colCaratteristiche.setCellValueFactory(new PropertyValueFactory<>("caratteristiche"));

        colDataAcquisto.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataAcquisto().format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN))));
        colPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        tableBicicletteComprate.setItems(bicicletteComprate);
        colModelloNoleggio.setCellValueFactory(new PropertyValueFactory<>("modello"));
        colCaratteristicheNoleggio.setCellValueFactory(new PropertyValueFactory<>("caratteristiche"));

    }

    public void setItem4Bis(){

        colInizioNoleggio.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getInizioNoleggio().format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN))));

    }

    public void setItem5(){

        colFineNoleggio.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFineNoleggio().format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN))));

        colPrezzoGiorno.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        colPrezzoTotale.setCellValueFactory(new PropertyValueFactory<>("prezzoFinale"));
        tableBicicletteNoleggiate.setItems( bicicletteNoleggiate);

        colDisdiciNoleggio.setCellValueFactory(new PropertyValueFactory<>("button"));
        selected(tabPanePrenotazioni.getSelectionModel().getSelectedItem().getId());
    }

    public void selected(String id){
        switch (id) {
            case "tabLezioni": {
                lezioni.clear();
                AreaPersonaleController1.showLezioni();
                copyLezioni(AreaPersonaleController1.lezioni);
                createButtonLezioni();
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
                copyEscursioni(AreaPersonaleController1.escursioni);
                createButtonEscursioni();
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
                createButtonNoleggio();
                return;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + id);
        }
    }


    public void createButtonLezioni(){
        for(int i = 0; i< AreaPersonaleController1.lezioni.size(); i++) {
            Button b = new Button(CANCEL_1);
            b.setPrefSize(150, 20);
            lezioni.get(i).setButtonLezione(b);

            int finalI = i;
            b.setDisable(lezioni.get(i).getData().isBefore(LocalDateTime.now()));
            b.setOnAction(event -> disdiciLezione(lezioni.get(finalI).getId()));

            b.setOnAction(event -> {

                Optional<ButtonType> option = new Alert(Alert.AlertType.CONFIRMATION, "Confermi di voler annullare la prenotazione?",
                        ButtonType.NO, ButtonType.YES).showAndWait();
                if (option.isPresent() && option.get() == ButtonType.YES) {
                    AreaPersonaleController1.disdiciLezione(lezioni.get(finalI).getId());
                    lezioni.clear();
                    copyLezioni(AreaPersonaleController1.lezioni);
                    createButtonLezioni();
                }
            });
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
            bean.setGiornoString(l.get(i).getData().format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));
            bean.setPrivataString(Boolean.TRUE.equals(l.get(i).isPrivata()) ? "Si" : "No");
            bean.setEliminata(l.get(i).getEliminata());
            lezioni.add(bean);
        }
    }

    public void createButtonEscursioni(){
        for(int i = 0; i< AreaPersonaleController1.escursioni.size(); i++) {
            Button b = new Button(CANCEL_1);
            b.setPrefSize(150, 20);
            escursioni.get(i).setButtonEscursione(b);

            int finalI = i;
            b.setDisable(escursioni.get(i).getData().isBefore(LocalDateTime.now()));
                    b.setOnAction(event -> {
                        Optional<ButtonType> option = new Alert(Alert.AlertType.CONFIRMATION, "Confermi di voler annullare la prenotazione?",
                                ButtonType.NO, ButtonType.YES).showAndWait();
                        if (option.isPresent() && option.get() == ButtonType.YES) {
                            AreaPersonaleController1.disdiciEscursione(escursioni.get(finalI).getId());
                            escursioni.clear();
                            copyEscursioni(AreaPersonaleController1.escursioni);
                            createButtonEscursioni();
                        }
                    });
        }
    }

    public void copyEscursioni(List<Escursione> l){
        for(int i=0; i<l.size();i++){
            BeanEscursioni bean = new BeanEscursioni();

            bean.setId(l.get(i).getId());
            bean.setData(l.get(i).getData());
            bean.setLuogo(l.get(i).getLuogo());
            bean.setAccompagnatore(l.get(i).getAccompagnatore());

            bean.setGiornoString(l.get(i).getData().format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));
            bean.setOraString(Utils.formatTime(l.get(i).getData().getHour(), l.get(i).getData().getMinute()));
            bean.setDifficoltaString(String.valueOf(l.get(i).getLuogo().getDifficolta()));
            bean.setAccompagnatoreString(l.get(i).getAccompagnatore().getNome() + " " + l.get(i).getAccompagnatore().getCognome());
            escursioni.add(bean);
        }
    }

    public static void createButtonNoleggio(){

        for(int i = 0; i< AreaPersonaleController1.bicicletteNoleggiate.size(); i++) {
            Button noleggia = new Button(CANCEL_1);
            noleggia.setPrefSize(150, 20);
            bicicletteNoleggiate.get(i).setButton(noleggia);

            int finalI = i;
            noleggia.setOnAction(event -> {

                Optional<ButtonType> option = new Alert(Alert.AlertType.CONFIRMATION, "Confermi di voler annullare il noleggio?",
                        ButtonType.NO, ButtonType.YES).showAndWait();
                if (option.isPresent() && option.get() == ButtonType.YES) {
                    bicicletteNoleggiate.clear();
                    AreaPersonaleController1.disdiciNoleggio(AreaPersonaleController1.bicicletteNoleggiate.get(finalI).getIdNoleggio());
                    bicicletteNoleggiate.addAll(AreaPersonaleController1.bicicletteNoleggiate);
                    createButtonNoleggio();
                }
            });
            noleggia.setDisable(AreaPersonaleController1.bicicletteNoleggiate.get(finalI).getFineNoleggio().isBefore(LocalDateTime.now()));
        }
    }
}
