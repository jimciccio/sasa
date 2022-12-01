package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.controllers1.StagioneController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.GaraExtended;
import ferranti.bikerbikus.models.Stagione;
import ferranti.bikerbikus.models.UtenteExtended;
import ferranti.bikerbikus.utils.Constants;
import ferranti.bikerbikus.utils.LoadScene;
import ferranti.bikerbikus.utils.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StagioneControllerGrafico extends StagioneController1{

    private Parent parent;
    private Stagione stagione;
    final Object controller = this;

    protected static final ObservableList<GaraExtended> gare = FXCollections.observableArrayList(StagioneController1.gare);
    protected static final ObservableList<UtenteExtended> utente = FXCollections.observableArrayList(StagioneController1.utente);
    protected static final ObservableList<UtenteExtended> utenteGara = FXCollections.observableArrayList(StagioneController1.utenteGara);


    @FXML
    Button btnBack;
    @FXML
    Label lblUserName;
    @FXML
    Label lblTipoUtente;
    @FXML
    Button btnProfile;
    @FXML
    Label lblNome;
    @FXML
    TableView<GaraExtended> tableGare;
    @FXML
    TableColumn<GaraExtended, String> colData;
    @FXML
    TableColumn<GaraExtended, Integer> colPartecipanti;
    @FXML
    TableColumn<GaraExtended, String> colNomeVincitore;
    @FXML
    TableColumn<GaraExtended, String> colCognomeVincitore;
    @FXML
    TableColumn<GaraExtended, GaraExtended> colDettagli;
    @FXML
    TableView<UtenteExtended> tableClassifica;
    @FXML
    TableColumn<UtenteExtended, Integer> colPosizione;
    @FXML
    TableColumn<UtenteExtended, String> colNome;
    @FXML
    TableColumn<UtenteExtended, String> colCognome;
    @FXML
    TableColumn<UtenteExtended, Integer> colGare;
    @FXML
    TableColumn<UtenteExtended, Integer> colPunti;

    public StagioneControllerGrafico(Stagione stagione) {
        this.stagione = stagione;
    }

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/stagione-view.fxml");
        btnBack.setOnAction(event -> new CampionatiControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        btnProfile.setOnAction(event -> new AreaPersonaleControllerGrafico().showScene(stage));
        lblNome.setText("Campionato " + stagione.getCampionato().getNome() + " - Stagione " + stagione.getNome());

        colData.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getData().format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN))));
        colPartecipanti.setCellValueFactory(new PropertyValueFactory<>("partecipanti"));
        colNomeVincitore.setCellValueFactory(new PropertyValueFactory<>("nomeVincitore"));
        colCognomeVincitore.setCellValueFactory(new PropertyValueFactory<>("cognomeVincitore"));
        colDettagli.setCellValueFactory(new PropertyValueFactory<>("button"));
        tableGare.setItems(gare);

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        colPosizione.setCellValueFactory(new PropertyValueFactory<>("posizioneFinale"));
        colGare.setCellValueFactory(new PropertyValueFactory<>("gare"));
        colPunti.setCellValueFactory(new PropertyValueFactory<>("punteggio"));
        tableClassifica.setItems(utente);
        gare.clear();
        utente.clear();
        StagioneController1.loadStagione(stagione);
        StagioneController1.loadClassifica(stagione);
        gare.addAll(StagioneController1.gare);
        utente.addAll(StagioneController1.utente);
        colPunti.setSortType(TableColumn.SortType.DESCENDING);
        tableClassifica.getSortOrder().add(colPunti);
        createButton();
    }

    public static void showDetails(GaraExtended idGara, LocalDateTime dataGara) {

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Gara del " + dataGara.getDayOfMonth()+"-"+dataGara.getMonthValue()+"-"+dataGara.getYear());
        dialog.setHeaderText("Classifica");

        ButtonType loginButtonType = new ButtonType("Ok", ButtonBar.ButtonData.LEFT);

        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(false);

        TableView<UtenteExtended> tabGara = new TableView<>();
        tabGara.setEditable(true);
        tabGara.setMinWidth(500);

        TableColumn<UtenteExtended, String> colNome = new TableColumn<>("Nome");
        colNome.setMinWidth(100);
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        TableColumn<UtenteExtended, String> colCognome = new TableColumn<>("Cognome");
        colCognome.setMinWidth(100);
        colCognome.setCellValueFactory(new PropertyValueFactory<>("cognome"));

        TableColumn<UtenteExtended, Integer> colPosizione = new TableColumn<>("Posizione");
        colPosizione.setMinWidth(100);
        colPosizione.setCellValueFactory(new PropertyValueFactory<>("posizioneGara"));

        TableColumn<UtenteExtended, String> colTempo = new TableColumn<>("Tempo");
        colTempo.setMinWidth(200);

        TableColumn<UtenteExtended, String> colPs1 = new TableColumn<>("Ps1");
        colPs1.setMinWidth(50);
        colPs1.setCellValueFactory(new PropertyValueFactory<>("ps1"));

        TableColumn<UtenteExtended, String> colPs2 = new TableColumn<>("Ps2");
        colPs2.setMinWidth(50);
        colPs2.setCellValueFactory(new PropertyValueFactory<>("ps2"));
        TableColumn<UtenteExtended, String> colPs3 = new TableColumn<>("Ps3");
        colPs3.setMinWidth(50);
        colPs3.setCellValueFactory(new PropertyValueFactory<>("ps3"));
        TableColumn<UtenteExtended, String> colTempoFinale = new TableColumn<>("Finale");
        colTempoFinale.setMinWidth(50);
        colTempoFinale.setCellValueFactory(new PropertyValueFactory<>("tempo"));

        colTempo.getColumns().addAll(colPs1, colPs2, colPs3, colTempoFinale);

        TableColumn<UtenteExtended, Integer> colPunti = new TableColumn<>("Punti");
        colPunti.setMinWidth(100);
        colPunti.setCellValueFactory(new PropertyValueFactory<>("punteggio"));
        tabGara.getColumns().addAll(colNome, colCognome, colPosizione, colTempo, colPunti);

        grid.add(tabGara, 0, 0);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            utenteGara.clear();
            return null;
        });
        dialog.show();
        if(StagioneController1.loadClassificaGara(idGara.getId())){
            utenteGara.clear();
            tabGara.setItems(utenteGara);
            utenteGara.addAll(StagioneController1.utenteGara);
            colPosizione.setSortType(TableColumn.SortType.ASCENDING);
            tabGara.getSortOrder().add(colPosizione);
        }else{
            new Alert(Alert.AlertType.ERROR, "Non è possibile visualizzare la classifica.", ButtonType.OK).show();
        }
    }

    public void createButton(){
        for(int i = 0; i< StagioneControllerGrafico.gare.size(); i++) {
            Button b = new Button("Dettagli");
            b.setPrefSize(150, 20);
            gare.get(i).setButton(b);
            int finalI = i;
            b.setOnAction(event -> showDetails(gare.get(finalI),gare.get(finalI).getData()));

        }
    }
}
