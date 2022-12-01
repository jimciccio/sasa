package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.BeanRecensioni;
import ferranti.bikerbikus.controllers1.ModificaRecensioneController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Luoghi;
import ferranti.bikerbikus.models.Recensione;
import ferranti.bikerbikus.queries.LuoghiQuery;
import ferranti.bikerbikus.utils.Constants;
import ferranti.bikerbikus.utils.LoadScene;
import ferranti.bikerbikus.utils.Utils;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ModificaRecensioneControllerGrafico extends ModificaRecensioneController1{

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<BeanRecensioni> recensioni = FXCollections.observableArrayList();


    @FXML
    HBox toolbar;
    @FXML
    Label lblNome;
    @FXML
    Button btnProfile;
    @FXML
    Button btnBack;
    @FXML
    Label lblUserName;
    @FXML
    Label lblTipoUtente;

    @FXML
    TableView<BeanRecensioni> tableRecensioni;
    @FXML
    TableColumn<Luoghi, String> colLuogo;
    @FXML
    TableColumn<BeanRecensioni, Button> colElimina;
    @FXML
    TableColumn<BeanRecensioni, Button> colRecensione;
    @FXML
    TableColumn<BeanRecensioni, LocalDate> colData;
    @FXML
    TableColumn<BeanRecensioni, Double> colValutazione;
    @FXML
    TableColumn<BeanRecensioni, Button> colModifica;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/modifica-recensione-view.fxml");
        btnBack.setOnAction(event -> new RecensioniControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        colLuogo.setCellValueFactory(new PropertyValueFactory<>("luogo"));
        btnProfile.setOnAction(event -> new AreaPersonaleControllerGrafico().showScene(stage));

        colRecensione.setCellValueFactory(new PropertyValueFactory<>("buttonRecensione"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        colValutazione.setCellValueFactory(new PropertyValueFactory<>("star"));
        colModifica.setCellValueFactory(new PropertyValueFactory<>("buttonModifica"));
        colElimina.setCellValueFactory(new PropertyValueFactory<>("buttonElimina"));

        tableRecensioni.setItems(recensioni);
        recensioni.clear();
        ModificaRecensioneController1.loadRecensioni();
        copyRecensioni(ModificaRecensioneController1.recensioni);
        createButton2();
    }


    public static boolean onActionElimina1(int idRecensione){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vuoi davvero eliminare la recensione?");
        alert.setContentText("Sei sicuro di voler cancellare questa recensione");

        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent() || result.get() != ButtonType.OK) {
            return false;
        } else {
            if(ModificaRecensioneController1.onActionElimina(idRecensione)){
                new Alert(Alert.AlertType.CONFIRMATION, "Recensione eliminata.", ButtonType.OK).show();
                recensioni.clear();
                LuoghiQuery.insertValutazioneForAll();
                copyRecensioni(ModificaRecensioneController1.recensioni);
                createButton2();
                return true;
            }else{
                new Alert(Alert.AlertType.ERROR, "Non è stato possibile eliminare la lezione.", ButtonType.OK).show();
                return false;
            }
        }
    }

    public static void onActionModifica1(int item, int idRecensione) {

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Modifica la Recensione");

        ButtonType loginButtonType = new ButtonType("Conferma", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, new ButtonType("annulla",ButtonBar.ButtonData.CANCEL_CLOSE));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        Label lblNomeLuogo = new Label(recensioni.get(item).getLuogo().getNome());
        lblNomeLuogo.setFont(new Font( 28));

        Label labelRecensione = new Label("Recensione :");
        labelRecensione.setFont(new Font( 20));

        TextArea txtArea = new TextArea();
        txtArea.setPrefSize(400,200);
        txtArea.setWrapText(true);
        txtArea.setFont(new Font( 16));
        txtArea.setText(recensioni.get(item).getRecensioneString());
        Label lblValutazione = new Label("Valutazione");

        DatePicker picker = new DatePicker();
        picker.setValue(recensioni.get(item).getData());
        final Rating rating = new Rating(5);
        rating.setPartialRating(true);
        rating.setMaxWidth(20);
        rating.setScaleX(0.6);
        rating.setScaleY(0.6);
        rating.setRating(recensioni.get(item).getValutazione());

        grid.setMinSize(400,400);
        grid.add(lblNomeLuogo, 0, 0);

        grid.add(labelRecensione, 0, 1);
        grid.add(txtArea, 0, 2);
        grid.add(picker, 0, 3);
        grid.add(lblValutazione, 0, 4);
        grid.add(rating, 0, 5);

        dialog.getDialogPane().setContent(grid);

        txtArea.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(newValue.equals(recensioni.get(item).getRecensioneString()) && picker.valueProperty().getValue().equals(recensioni.get(item).getData()) && rating.getRating() == recensioni.get(item).getValutazione()){
                loginButton.setDisable(true);
            }
        });

        picker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(picker.valueProperty().getValue().equals(recensioni.get(item).getData()) && txtArea.getText().equals(recensioni.get(item).getRecensioneString()) && rating.getRating() == recensioni.get(item).getValutazione()){
                loginButton.setDisable(true);
            }
        });

        rating.ratingProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            double value = ModificaRecensioneController1.roundToHalf(newValue.doubleValue());
            rating.setRating(value);
            loginButton.setDisable(false);
            if(rating.getRating() == recensioni.get(item).getValutazione() && picker.valueProperty().getValue().equals(recensioni.get(item).getData()) && txtArea.getText().equals(recensioni.get(item).getRecensioneString())){
                loginButton.setDisable(true);
            }
        });

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == loginButtonType){
                if(ModificaRecensioneController1.onActionModifica(idRecensione, txtArea.getText(), picker.getValue(), rating.getRating())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Recensione modificata.", ButtonType.OK).show();
                    recensioni.clear();
                    LuoghiQuery.insertValutazioneForAll();
                    copyRecensioni(ModificaRecensioneController1.recensioni);
                    createButton2();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Non è stato possibile modificare la recensione.", ButtonType.OK).show();
                }
            }
            return null;
        });
        dialog.showAndWait();
    }

    public static void showRecensione(int item){


        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Recensione");

        ButtonType loginButtonType = new ButtonType("Ok", ButtonBar.ButtonData.LEFT);

        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(false);

        TextArea txtArea = new TextArea();
        txtArea.setWrapText(true);
        txtArea.setEditable(false);
        txtArea.setText(recensioni.get(item).getRecensioneString());
        grid.add(txtArea, 0, 0);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> null);
        dialog.show();

    }

    public static void createButton2(){
        for(int i = 0; i< recensioni.size(); i++) {
            Button a = new Button("Recensione");
            a.setPrefSize(150, 20);
            recensioni.get(i).setButtonRecensione(a);

            Button b = new Button("Modifica");
            b.setPrefSize(150, 20);
            recensioni.get(i).setButtonModifica(b);

            Button c = new Button("Elimina");
            c.setPrefSize(150, 20);
            recensioni.get(i).setButtonElimina(c);

            int finalI2 = i;
            a.setOnAction(event -> showRecensione(finalI2));
            b.setOnAction(event -> onActionModifica1(finalI2, recensioni.get(finalI2).getId()));
            c.setOnAction(event -> onActionElimina1(recensioni.get(finalI2).getId()));
        }
    }

    public static void copyRecensioni(List<Recensione> l){
        for(int i=0; i<l.size();i++){
            BeanRecensioni bean = new BeanRecensioni();

            bean.setId(l.get(i).getId());
            bean.setUtente(l.get(i).getUtente());
            bean.setRecensioneString(l.get(i).getRecensioneString());

            bean.setDateString(l.get(i).getData().format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));
            bean.setData(l.get(i).getData());
            bean.setLuogo(l.get(i).getLuogo());

            bean.setStar(l.get(i).getValutazione());
            bean.setValutazione(l.get(i).getValutazione());
            recensioni.add(bean);
        }
    }
}
