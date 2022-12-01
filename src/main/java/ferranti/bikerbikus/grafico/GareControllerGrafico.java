package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.BeanGare;
import ferranti.bikerbikus.controllers1.GareController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Gara;
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

public class GareControllerGrafico extends GareController1{

    private Parent parent;
    final Object controller = this;
    Stage stag;

    protected static final ObservableList<BeanGare> gare = FXCollections.observableArrayList();

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
    TableView<BeanGare> tableGare;
    @FXML
    TableColumn<BeanGare, String> colGiorno;
    @FXML
    TableColumn<BeanGare, String> colOrario;
    @FXML
    TableColumn<BeanGare, Hyperlink> colStagione;
    @FXML
    TableColumn<BeanGare, Button> colPrenotazione;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/gare-view.fxml");
        stag=stage;
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
            copyGare(GareController1.gare);
            createButton();

        });
        btnNextMonth.setOnAction(event -> {
            gare.clear();
            super.onActionNextMonth();
            lblMese.setText(Utils.uppercase(getCurrentYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
            lblAnno.setText(Integer.toString(getCurrentYearMonth().getYear()));
            copyGare(GareController1.gare);
            createButton();
        });
        btnProfile.setOnAction(event -> new AreaPersonaleControllerGrafico().showScene(stage));
        colGiorno.setCellValueFactory(cellData -> new SimpleObjectProperty<>(Utils.uppercase(cellData.getValue().getData().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault())) + " "
                + cellData.getValue().getData().getDayOfMonth()));

        colOrario.setCellValueFactory(cellData -> new SimpleObjectProperty<>(Utils.formatTime(cellData.getValue().getData().getHour(), cellData.getValue().getData().getMinute())));

        colStagione.setCellValueFactory(new PropertyValueFactory<>("link"));
        colPrenotazione.setCellValueFactory(new PropertyValueFactory<>("buttonGara"));
        tableGare.setItems(gare);
        gare.clear();
        super.loadGare();
        copyGare(GareController1.gare);
        createButton();
    }



    public void createButton(){
        for(int i = 0; i< GareController1.gare.size(); i++) {
            Button b = new Button("Prenota");
            b.setPrefSize(150, 20);

            int finalI = i;
            b.setDisable(gare.get(i).getData().isBefore(LocalDateTime.now()));
            gare.get(i).setButtonGara(b);
            b.setOnAction(event -> {
                if(prenotaGara(gare.get(finalI).getId())){
                    gare.clear();
                    new Alert(Alert.AlertType.CONFIRMATION, "Prenotazione effettuata con successo!", ButtonType.OK).show();
                    copyGare(GareController1.gare);
                    createButton();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Non è stato possibile prenotare la gara.", ButtonType.OK).show();
                }
            });

            Hyperlink hyperlink = new Hyperlink(gare.get(i).getStagione().toString());
            hyperlink.setOnAction(event -> new StagioneControllerGrafico(gare.get(finalI).getStagione()).showScene(stag));
            gare.get(i).setLink(hyperlink);

        }
    }

    public void copyGare(List<Gara> l){
        for(int i=0; i<l.size();i++){
            BeanGare bean = new BeanGare();

            bean.setId(l.get(i).getId());
            bean.setStagione(l.get(i).getStagione());
            bean.setData(l.get(i).getData());
            gare.add(bean);
        }
    }
}
