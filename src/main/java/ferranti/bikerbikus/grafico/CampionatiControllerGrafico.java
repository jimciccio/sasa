package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.controllers1.CampionatiController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Campionato;
import ferranti.bikerbikus.models.Stagione;
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
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CampionatiControllerGrafico extends CampionatiController1{

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<Stagione> stagioni = FXCollections.observableArrayList(CampionatiController1.loadStagioni());


    @FXML
    HBox toolbar;
    @FXML
    Button btnBack;
    @FXML
    Button btnAddSeason;
    @FXML
    Label lblUserName;
    @FXML
    Label lblTipoUtente;
    @FXML
    Button btnProfile;
    @FXML
    TableView<Stagione> tableStagioni;
    @FXML
    TableColumn<Stagione, Campionato> colCampionato;
    @FXML
    TableColumn<Stagione, String> colStagione;
    @FXML
    TableColumn<Stagione, LocalDate> colDataInizio;
    @FXML
    TableColumn<Stagione, LocalDate> colDataFine;
    @FXML
    TableColumn<Stagione, Stagione> colDettagli;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/campionati-view.fxml");
        btnBack.setOnAction(event -> new HomeControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
            btnAddSeason.setOnAction(event -> new AggiungiStagioneControllerGrafico().showScene(stage));
        } else {
            toolbar.getChildren().remove(btnAddSeason);
        }
        btnProfile.setOnAction(event -> new AreaPersonaleControllerGrafico().showScene(stage));
        colCampionato.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Campionato item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.getNome());
            }
        });
        colCampionato.setCellValueFactory(
                cellData -> new SimpleObjectProperty<Campionato>(cellData.getValue().getCampionato()));
        colStagione.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDataInizio.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));
            }
        });
        colDataInizio.setCellValueFactory(new PropertyValueFactory<>("dataInizio"));
        colDataFine.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));
            }
        });
        colDataFine.setCellValueFactory(new PropertyValueFactory<>("dataFine"));
        colDettagli.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Stagione item, boolean empty) {
                super.updateItem(item, empty);
                final Hyperlink hyperlink = new Hyperlink("Dettagli");
                hyperlink.setOnAction(event -> new StagioneControllerGrafico(item).showScene(stage));
                setGraphic(item == null ? null : hyperlink);
            }
        });
        colDettagli.setCellValueFactory(cellData -> new SimpleObjectProperty<Stagione>(cellData.getValue()));
        tableStagioni.setItems(stagioni);
        CampionatiController1.loadStagioni();
    }
}
