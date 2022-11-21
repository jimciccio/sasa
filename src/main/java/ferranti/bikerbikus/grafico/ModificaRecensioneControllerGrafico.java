package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.controllers1.ModificaRecensioneController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Luoghi;
import ferranti.bikerbikus.models.Recensione;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ModificaRecensioneControllerGrafico extends ModificaRecensioneController1{

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<Recensione> recensioni = FXCollections.observableArrayList(ModificaRecensioneController1.recensioni);


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
    TableView<Recensione> tableRecensioni;
    @FXML
    TableColumn<Luoghi, String> colLuogo;
    @FXML
    TableColumn<Recensione, Integer> colElimina;
    @FXML
    TableColumn<Recensione, String> colRecensione;
    @FXML
    TableColumn<Recensione, LocalDate> colData;
    @FXML
    TableColumn<Recensione, Double> colValutazione;
    @FXML
    TableColumn<Recensione, Integer> colModifica;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/modifica-recensione-view.fxml");
        btnBack.setOnAction(event -> new RecensioniControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        colLuogo.setCellValueFactory(new PropertyValueFactory<>("luogo"));
        btnProfile.setOnAction(event -> new AreaPersonaleControllerGrafico().showScene(stage));
        colRecensione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                Text text = new Text(item);
                text.setWrappingWidth(300);
                text.wrappingWidthProperty().bind(getTableColumn().widthProperty().subtract(20));
                text.setStyle("-fx-text-alignment:justify;");
                setGraphic(text);
            }
        });
        colRecensione.setCellValueFactory(new PropertyValueFactory<>("recensioneString"));
        setItem();
        colModifica.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                final Button btnModifica = new Button();
                btnModifica.setPrefSize(150, 20);
                if (getTableRow() != null && getTableRow().getItem() != null) {
                    btnModifica.setOnAction(event -> onActionModifica(item, getTableRow().getItem().getLuogo().getNome(), getTableRow().getItem().getRecensioneString(), getTableRow().getItem().getData(), getTableRow().getItem().getValutazione()));
                    btnModifica.setText("Modifica");
                }
                setGraphic(item == null ? null : btnModifica);
            }
        });
        colModifica.setCellValueFactory(new PropertyValueFactory<>("id"));
        colElimina.setCellValueFactory(new PropertyValueFactory<>("id"));
        colElimina.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                final Button btnElimina = new Button();
                btnElimina.setPrefSize(150, 20);
                if (getTableRow() != null && getTableRow().getItem() != null) {
                    btnElimina.setOnAction(event -> onActionElimina(item, stage));
                    btnElimina.setText("Elimina");
                }
                setGraphic(item == null ? null : btnElimina);
            }
        });
        tableRecensioni.setItems(recensioni);
        recensioni.clear();
        ModificaRecensioneController1.loadRecensioni();
        recensioni.addAll(ModificaRecensioneController1.recensioni);
    }

    public void setItem(){
        colData.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));
            }
        });
        colValutazione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                final HBox box = new HBox();
                box.setMaxWidth(30);
                box.setMaxHeight(20);
                final Rating rating = new Rating(5);
                rating.setMaxWidth(20);
                rating.setPartialRating(true);
                rating.setMouseTransparent(true);
                rating.setScaleX(0.6);
                rating.setScaleY(0.6);
                if (getTableRow() != null && getTableRow().getItem() != null) {
                    box.getChildren().add(rating);
                    rating.setRating(item);
                }
                setGraphic(item == null ? null : box);
            }
        });
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colValutazione.setCellValueFactory(new PropertyValueFactory<>("valutazione"));
    }

    public static boolean onActionElimina(int idRecensione, Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vuoi davvero eliminare la recensione?");
        alert.initOwner(stage);
        alert.setContentText("Sei sicuro di voler cancellare questa recensione");

        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent() || result.get() != ButtonType.OK) {
            return false;
        } else {
            if(ModificaRecensioneController1.onActionElimina(idRecensione)){
                new Alert(Alert.AlertType.CONFIRMATION, "Recensione eliminata.", ButtonType.OK).show();
                recensioni.clear();
                recensioni.addAll(ModificaRecensioneController1.recensioni);
                return true;

            }else{
                new Alert(Alert.AlertType.ERROR, "Non è stato possibile eliminare la lezione.", ButtonType.OK).show();
                return false;
            }
        }
    }

    public static void onActionModifica(int idRecensione, String nomeLuogo, String recensione, LocalDate data, Double valutazione ) {

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

        Label lblNomeLuogo = new Label(nomeLuogo);
        lblNomeLuogo.setFont(new Font( 28));

        Label labelRecensione = new Label("Recensione :");
        labelRecensione.setFont(new Font( 20));

        TextArea txtArea = new TextArea();
        txtArea.setPrefSize(400,200);
        txtArea.setWrapText(true);
        txtArea.setFont(new Font( 16));
        txtArea.setText(recensione);
        Label lblValutazione = new Label("Valutazione");

        DatePicker picker = new DatePicker();
        picker.setValue(data);
        final Rating rating = new Rating(5);
        rating.setPartialRating(true);
        rating.setMaxWidth(20);
        rating.setScaleX(0.6);
        rating.setScaleY(0.6);
        rating.setRating(valutazione);

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
            if(newValue.equals(recensione) && picker.valueProperty().getValue().equals(data) && rating.getRating() == valutazione){
                loginButton.setDisable(true);
            }
        });

        picker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(picker.valueProperty().getValue().equals(data) && txtArea.getText().equals(recensione) && rating.getRating() == valutazione){
                loginButton.setDisable(true);
            }
        });

        rating.ratingProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            double value = ModificaRecensioneController1.roundToHalf(newValue.doubleValue());
            rating.setRating(value);
            loginButton.setDisable(false);
            if(rating.getRating() == valutazione && picker.valueProperty().getValue().equals(data) && txtArea.getText().equals(recensione)){
                loginButton.setDisable(true);
            }
        });

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == loginButtonType){
                if(ModificaRecensioneController1.onActionModifica(idRecensione, txtArea.getText(), picker.getValue(), rating.getRating())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Recensione modificata.", ButtonType.OK).show();
                    recensioni.clear();
                    recensioni.addAll(ModificaRecensioneController1.recensioni);
                }else{
                    new Alert(Alert.AlertType.ERROR, "Non è stato possibile modificare la recensione.", ButtonType.OK).show();
                }
            }
            return null;
        });
        dialog.showAndWait();
    }
}
