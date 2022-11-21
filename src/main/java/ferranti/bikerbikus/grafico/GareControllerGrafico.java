package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.controllers1.GareController1;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class GareControllerGrafico extends GareController1{

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<Gara> gare = FXCollections.observableArrayList(GareController1.gare);

    @FXML
    HBox toolbar;
    @FXML
    Button btnBack;
    @FXML
    Button btnProfile;
    @FXML
    Button btnPrevMonth;
    @FXML
    Button btnNextMonth;
    @FXML
    Label lblUserName;
    @FXML
    Label lblTipoUtente;
    @FXML
    Button btnAddGara;
    @FXML
    Label lblMese;
    @FXML
    Label lblAnno;
    @FXML
    TableView<Gara> tableGare;
    @FXML
    TableColumn<Gara, LocalDateTime> colGiorno;
    @FXML
    TableColumn<Gara, LocalDateTime> colOrario;
    @FXML
    TableColumn<Gara, Stagione> colStagione;
    @FXML
    TableColumn<Gara, Integer> colPrenotazione;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/gare-view.fxml");
        lblMese.setText(
                Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
        lblAnno.setText(Integer.toString(getCurrentYearMonth().getYear()));
        btnBack.setOnAction(event -> new HomeControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
            btnAddGara.setOnAction(event -> new AggiungiGaraControllerGrafico().showScene(stage));
        } else {
            toolbar.getChildren().remove(btnAddGara);
        }
        btnPrevMonth.setOnAction(event -> {
            gare.clear();
            super.onActionPrevMonth();
            lblMese.setText(Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
            lblAnno.setText(Integer.toString(getCurrentYearMonth().getYear()));
            gare.addAll(GareController1.gare);

        });
        btnNextMonth.setOnAction(event -> {
            gare.clear();
            super.onActionNextMonth();
            lblMese.setText(Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
            lblAnno.setText(Integer.toString(getCurrentYearMonth().getYear()));
            gare.addAll(GareController1.gare);
        });
        btnProfile.setOnAction(event -> new AreaPersonaleControllerGrafico().showScene(stage));
        colGiorno.setCellValueFactory(new PropertyValueFactory<>("data"));

        colGiorno.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? ""
                        : Utils.uppercase(item.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault())) + " "
                        + item.getDayOfMonth());
            }
        });
        colOrario.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : Utils.formatTime(item.getHour(), item.getMinute()));
            }
        });
        colOrario.setCellValueFactory(new PropertyValueFactory<>("data"));
        colStagione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Stagione item, boolean empty) {
                super.updateItem(item, empty);
                final Hyperlink hyperlink = new Hyperlink(item == null ? "" : item.toString());
                hyperlink.setOnAction(event -> new StagioneControllerGrafico(item).showScene(stage));
                setGraphic(item == null ? null : hyperlink);
            }
        });

        colStagione.setCellValueFactory(new PropertyValueFactory<>("stagione"));
        setItem();
    }

    public void setItem(){

        colPrenotazione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                final Button btnPrenota = new Button("Prenota");
                btnPrenota.setPrefSize(150, 20);
                btnPrenota.setOnAction(event -> {
                    if(prenotaGara(item)){
                        gare.clear();
                        new Alert(Alert.AlertType.CONFIRMATION, "Prenotazione effettuata con successo!", ButtonType.OK).show();
                        gare.addAll(GareController1.gare);
                    }else{
                        new Alert(Alert.AlertType.ERROR, "Non è stato possibile prenotare la gara.", ButtonType.OK).show();
                    }
                });
                if (getTableRow() != null && getTableRow().getItem() != null) {
                    btnPrenota.setDisable(getTableRow().getItem().getData().isBefore(LocalDateTime.now()));
                }
                setGraphic(item == null ? null : btnPrenota);
            }
        });
        colPrenotazione.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableGare.setItems(gare);
        gare.clear();
        super.loadGare();
        gare.addAll(GareController1.gare);
    }
}
