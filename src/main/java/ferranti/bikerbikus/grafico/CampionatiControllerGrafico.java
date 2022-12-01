package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.BeanStagione;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CampionatiControllerGrafico extends CampionatiController1{

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<BeanStagione> stagioni = FXCollections.observableArrayList();


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
    TableView<BeanStagione> tableStagioni;
    @FXML
    TableColumn<BeanStagione, Campionato> colCampionato;
    @FXML
    TableColumn<BeanStagione, String> colStagione;
    @FXML
    TableColumn<BeanStagione, String> colDataInizio;
    @FXML
    TableColumn<BeanStagione, String> colDataFine;
    @FXML
    TableColumn<BeanStagione, Button> colDettagli;

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

        colCampionato.setCellValueFactory(
                cellData -> new SimpleObjectProperty<>(cellData.getValue().getCampionato()));
        colStagione.setCellValueFactory(new PropertyValueFactory<>("nome"));

        colDataInizio.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataInizio().format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN))));

        colDataFine.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDataFine().format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN))));

        colDettagli.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getButtonStagione()));


        tableStagioni.setItems(stagioni);
        CampionatiController1.loadStagioni();
        copyStagioni(CampionatiController1.stagioni);
        createButton(stage);
    }

    public void createButton(Stage stage){
        for(int i = 0; i< CampionatiController1.stagioni.size(); i++) {
            Button b = new Button("Dettagli");
            b.setPrefSize(150, 20);
            stagioni.get(i).setButtonStagione(b);
            int finalI = i;
            b.setOnAction(event -> new StagioneControllerGrafico(stagioni.get(finalI)).showScene(stage));
        }
    }

    public void copyStagioni(List<Stagione> l){
        for(int i=0; i<l.size();i++){
            BeanStagione bean = new BeanStagione();

            bean.setId(l.get(i).getId());
            bean.setNome(l.get(i).getNome());
            bean.setDataInizio(l.get(i).getDataInizio());
            bean.setDataFine(l.get(i).getDataFine());
            bean.setCampionato(l.get(i).getCampionato());
            stagioni.add(bean);
        }
    }
}