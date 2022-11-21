package com.intellij.bikerbikus.grafico;

import com.intellij.bikerbikus.controllers1.RecensioniController1;
import com.intellij.bikerbikus.utils.LoadScene;
import com.intellij.bikerbikus.data.UserData;
import com.intellij.bikerbikus.models.Luoghi;
import com.intellij.bikerbikus.models.Recensione;
import com.intellij.bikerbikus.models.Utente;
import com.intellij.bikerbikus.queries.LuoghiQuery;
import com.intellij.bikerbikus.utils.Constants;
import com.intellij.bikerbikus.utils.Utils;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;

import static com.intellij.bikerbikus.controllers1.ModificaRecensioneController1.roundToHalf;

public class RecensioniControllerGrafico extends RecensioniController1 {

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<Recensione> recensioni = FXCollections.observableArrayList(RecensioniController1.recensioni);
    protected static final ObservableList<Luoghi> luoghi = FXCollections.observableArrayList(RecensioniController1.loadLuoghi());

    @FXML
    HBox toolbar;
    @FXML
    Button btnBack;
    @FXML
    Label lblUserName;
    @FXML
    Label lblTipoUtente;
    @FXML
    Label lblNome;
    @FXML
    Button btnProfile;
    @FXML
    Button btnModifyRecensioni;
    @FXML
    Button btnAddPlace;
    @FXML
    TableView<Luoghi> tableLuoghi;
    @FXML
    TableColumn<Luoghi, String> colNomeLuogo;
    @FXML
    TableColumn<Luoghi, String> colDescrizione;
    @FXML
    TableColumn<Luoghi, Integer> colDifficolta;
    @FXML
    TableColumn<Luoghi, Double> colValutazioneMedia;
    @FXML
    TableColumn<Luoghi, Integer> colRecensisciButton;
    @FXML
    TableView<Recensione> tableRecensioni;
    @FXML
    TableColumn<Recensione, Utente> colNomeUtente;
    @FXML
    TableColumn<Recensione, String> colRecensione;
    @FXML
    TableColumn<Recensione, LocalDate> colData;
    @FXML
    TableColumn<Recensione, Double> colValutazione;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/recensioni-view.fxml");
        btnBack.setOnAction(event -> new EscursioniControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
            btnAddPlace.setOnAction(event -> new ModificaLuogoControllerGrafico().showScene(stage));
        } else {
            toolbar.getChildren().remove(btnAddPlace);
        }
        btnProfile.setOnAction(event -> new AreaPersonaleControllerGrafico().showScene(stage));
        btnModifyRecensioni.setOnAction(event -> new ModificaRecensioneControllerGrafico().showScene(stage));
        colNomeLuogo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescrizione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                Text text = new Text(item);
                text.setStyle("-fx-text-alignment:justify;");
                text.setWrappingWidth(300);
                text.wrappingWidthProperty().bind(getTableColumn().widthProperty().subtract(20));
                setGraphic(text);
            }
        });
        colDescrizione.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        colValutazioneMedia.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                final HBox box = new HBox();
                box.setMaxWidth(30);
                box.setMaxHeight(20);
                final Rating ratingAverage = new Rating(5);
                ratingAverage.setPartialRating(true);
                ratingAverage.setMaxWidth(20);
                ratingAverage.setScaleX(0.6);
                ratingAverage.setScaleY(0.6);
                ratingAverage.setMouseTransparent(true);
                if (getTableRow() != null && getTableRow().getItem() != null) {
                    box.getChildren().add(ratingAverage);
                    ratingAverage.setRating(item);
                }
                setGraphic(item == null ? null : box);
            }
        });
        colValutazioneMedia.setCellValueFactory(new PropertyValueFactory<>("valutazioneMedia"));
        colDifficolta.setCellValueFactory(new PropertyValueFactory<>("difficolta"));
        setItem();
        setItemBis();
        setItem1();
        tableRecensioni.setItems(recensioni);
        recensioni.clear();
        luoghi.clear();
        luoghi.addAll(RecensioniController1.luoghi);
        LuoghiQuery.insertValutazioneForAll();
        RecensioniController1.loadLuoghi();
        tableLuoghi.getSelectionModel().selectFirst();
        RecensioniController1.loadRecensioni(tableLuoghi.getItems().get(0).getId());
        recensioni.addAll(RecensioniController1.recensioni);
        lblNome.setText(tableLuoghi.getItems().get(0).getNome());

    }

    public void setItem(){
        colRecensisciButton.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                final Button btnRecensisci = new Button("Recensisci");
                btnRecensisci.setPrefSize(150, 20);
                if (getTableRow() != null && getTableRow().getItem() != null) {

                        btnRecensisci.setOnAction(event -> {
                                    if(getTableRow().getItem().getNome().equals(lblNome.getText())){
                                        onActionRecensisci(item, getTableRow().getItem().getNome(), getTableRow().getIndex());
                                    }else {
                                        method(item);
                                        onActionRecensisci(item, getTableRow().getItem().getNome(), getTableRow().getIndex());
                                    }
                                    });
                }
                setGraphic(item == null ? null : btnRecensisci);
            }
        });
    }

    public void setItemBis(){
        colRecensisciButton.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableLuoghi.setRowFactory(tv -> {
            TableRow<Luoghi> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    Luoghi clickedRow = row.getItem();
                    lblNome.setText(tableLuoghi.getItems().get(row.getIndex()).getNome());
                    RecensioniController1.loadRecensioni(clickedRow.getId());
                    recensioni.clear();
                    recensioni.addAll(RecensioniController1.recensioni);
                }
            });
            return row ;
        });
        tableLuoghi.setItems(luoghi);
        colNomeUtente.setCellValueFactory(new PropertyValueFactory<>("utente"));
        colRecensione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                Text text = new Text(item);
                text.setStyle("-fx-text-alignment:justify;");
                text.setWrappingWidth(300);
                text.wrappingWidthProperty().bind(getTableColumn().widthProperty().subtract(20));
                setGraphic(text);
            }
        });
        colRecensione.setCellValueFactory(new PropertyValueFactory<>("recensioneString"));
        colData.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? "" : item.format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));
            }
        });
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
    }

    public void setItem1(){
        colValutazione.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                final HBox box = new HBox();
                box.setMaxWidth(30);
                box.setMaxHeight(20);
                final Rating rating = new Rating(5);
                rating.setPartialRating(true);
                rating.setMaxWidth(20);
                rating.setScaleX(0.6);
                rating.setScaleY(0.6);
                rating.setMouseTransparent(true);
                if (getTableRow() != null && getTableRow().getItem() != null) {
                    box.getChildren().add(rating);
                    rating.setRating(item);
                }
                setGraphic(item == null ? null : box);
            }
        });
        colValutazione.setCellValueFactory(new PropertyValueFactory<>("valutazione"));
    }

    public void method(int id) {
        Executors.newSingleThreadExecutor().submit(() -> RecensioniController1.loadRecensioni(id));

    }
    public  void onActionRecensisci(int idLuogo, String nomeLuogo, int selectedIndex) {

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Aggiungi una Recensione");

        lblNome.setText(tableLuoghi.getItems().get(selectedIndex).getNome());
        tableLuoghi.getSelectionModel().select(selectedIndex);

        ButtonType loginButtonType = new ButtonType("Conferma", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, new ButtonType("annulla",ButtonBar.ButtonData.CANCEL_CLOSE));

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        Label labelRecensione = new Label("Recensione :");
        labelRecensione.setFont(new Font( 20));

        Label lblNomeLuogo = new Label(nomeLuogo);
        lblNomeLuogo.setFont(new Font( 28));

        TextArea txtArea = new TextArea();
        txtArea.setPrefSize(400,200);
        txtArea.setWrapText(true);
        txtArea.setFont(new Font( 16));
        Label lblValutazione = new Label("Valutazione");

        final Rating rating = new Rating();
        rating.setPartialRating(true);

        rating.ratingProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            double value = roundToHalf(newValue.doubleValue());
            rating.setRating(value);
        });

        DatePicker picker = new DatePicker();

        grid.setMinSize(400,400);

        grid.add(lblNomeLuogo, 0, 0);
        grid.add(labelRecensione, 0, 1);
        grid.add(txtArea, 0, 2);
        grid.add(picker, 0, 3);
        grid.add(rating, 0, 5);
        grid.add(lblValutazione, 0, 4);

        dialog.getDialogPane().setContent(grid);

        prop(txtArea, picker, rating, loginButton);

        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == loginButtonType){
                if(RecensioniController1.recensisciLuogo(idLuogo, txtArea.getText(), picker.getValue(), rating.getRating())){
                    recensioni.clear();
                    new Alert(Alert.AlertType.CONFIRMATION, "Recensione effettuata con successo!", ButtonType.OK).show();
                    recensioni.addAll(RecensioniController1.recensioni);
                }else{
                    new Alert(Alert.AlertType.ERROR, "Non è stato possibile inserire la tua recensione. Riprova pi tardi.", ButtonType.OK).show();
                }
            }
                return null;
        });
        dialog.showAndWait();
    }

    public void prop(TextArea txtArea, DatePicker picker, Rating rating, Node loginButton){
        txtArea.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(picker.getValue() == null || newValue.isBlank() || rating.getRating() == 0){
                loginButton.setDisable(true);
            }
        });

        picker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(txtArea.getText().isBlank() || newValue.isBlank() || rating.getRating() == 0){
                loginButton.setDisable(true);
                if(newValue.isBlank()){ picker.setValue(null);}
            }
        });
        rating.ratingProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(picker.getValue() == null || newValue == oldValue || txtArea.getText().isBlank()){
                loginButton.setDisable(true);
            }
        });
    }
}
