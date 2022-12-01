package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.BeanLuoghi;
import ferranti.bikerbikus.BeanRecensioni;
import ferranti.bikerbikus.controllers1.RecensioniController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Luoghi;
import ferranti.bikerbikus.models.Recensione;
import ferranti.bikerbikus.models.Utente;
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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ferranti.bikerbikus.controllers1.ModificaRecensioneController1.roundToHalf;

public class RecensioniControllerGrafico extends RecensioniController1{

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<BeanRecensioni> recensioni = FXCollections.observableArrayList();
    protected static final ObservableList<BeanLuoghi> luoghi = FXCollections.observableArrayList();

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
    TableView<BeanLuoghi> tableLuoghi;
    @FXML
    TableColumn<BeanLuoghi, String> colNomeLuogo;
    @FXML
    TableColumn<BeanLuoghi, String> colDescrizione;
    @FXML
    TableColumn<BeanLuoghi, Integer> colDifficolta;
    @FXML
    TableColumn<BeanLuoghi, Double> colValutazioneMedia;
    @FXML
    TableColumn<BeanLuoghi, Integer> colRecensisciButton;
    @FXML
    TableView<BeanRecensioni> tableRecensioni;
    @FXML
    TableColumn<BeanRecensioni, Utente> colNomeUtente;
    @FXML
    TableColumn<BeanRecensioni, String> colRecensione;
    @FXML
    TableColumn<BeanRecensioni, String> colData;
    @FXML
    TableColumn<BeanRecensioni, Double> colValutazione;

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
        colDescrizione.setCellValueFactory(new PropertyValueFactory<>("buttonDescrizione"));
        colValutazioneMedia.setCellValueFactory(new PropertyValueFactory<>("star"));
        colDifficolta.setCellValueFactory(new PropertyValueFactory<>("difficolta"));
        setItemBis();
        colValutazione.setCellValueFactory(new PropertyValueFactory<>("star"));
        tableRecensioni.setItems(recensioni);
        recensioni.clear();
        luoghi.clear();
        RecensioniController1.loadLuoghi();
        copy(RecensioniController1.luoghi);
        createButton();
        tableLuoghi.getSelectionModel().selectFirst();
        RecensioniController1.loadRecensioni(tableLuoghi.getItems().get(0).getId());
        lblNome.setText(tableLuoghi.getItems().get(0).getNome());
        copyRecensioni(RecensioniController1.recensioni);
        createButton2();

    }

    public void setItemBis(){
        colRecensisciButton.setCellValueFactory(new PropertyValueFactory<>("button"));
        tableLuoghi.setRowFactory(tv -> {
            TableRow<BeanLuoghi> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    Luoghi clickedRow = row.getItem();
                    lblNome.setText(tableLuoghi.getItems().get(row.getIndex()).getNome());
                    RecensioniController1.loadRecensioni(clickedRow.getId());
                    recensioni.clear();
                    copyRecensioni(RecensioniController1.recensioni);
                    createButton2();
                }
            });
            return row ;
        });
        tableLuoghi.setItems(luoghi);
        colNomeUtente.setCellValueFactory(new PropertyValueFactory<>("utente"));
        colRecensione.setCellValueFactory(new PropertyValueFactory<>("buttonRecensione"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dateString"));
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
                    new Alert(Alert.AlertType.CONFIRMATION, "Recensione effettuata con successo!", ButtonType.OK).show();
                    recensioni.clear();
                    luoghi.clear();
                    copyRecensioni(RecensioniController1.recensioni);
                    createButton2();
                    RecensioniController1.loadLuoghi();
                    copy(RecensioniController1.luoghi);
                    createButton();
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

    public void createButton(){
        for(int i = 0; i< luoghi.size(); i++) {
            Button b = new Button("Recensisci");
            Button c = new Button("Descrizione");
            b.setPrefSize(150, 20);
            luoghi.get(i).setButton(b);
            luoghi.get(i).setButtonDescrizione(c);

            int finalI2 = i;
            b.setOnAction(event -> onActionRecensisci(luoghi.get(finalI2).getId(), luoghi.get(finalI2).getNome(), finalI2));

            int finalI1 = i;
            c.setOnAction(event -> showDescription(finalI1));
        }
    }

    public void createButton2(){
        for(int i = 0; i< recensioni.size(); i++) {
            Button b = new Button("Recensione");
            b.setPrefSize(150, 20);
            recensioni.get(i).setButtonRecensione(b);

            int finalI2 = i;
            b.setOnAction(event -> showRecensione(finalI2));
        }
    }

    public void copy(List<Luoghi> l){
        for(int i=0; i<l.size();i++){
            BeanLuoghi bean = new BeanLuoghi();

            bean.setId(l.get(i).getId());
            bean.setNome(l.get(i).getNome());
            bean.setDescrizione(l.get(i).getDescrizione());

            bean.setDifficolta(l.get(i).getDifficolta());
            bean.setValutazioneMedia(l.get(i).getValutazioneMedia());
            bean.setStar(l.get(i).getValutazioneMedia());
            luoghi.add(bean);
        }
    }

    public void copyRecensioni(List<Recensione> l){
        for(int i=0; i<l.size();i++){
            BeanRecensioni bean = new BeanRecensioni();

            bean.setId(l.get(i).getId());
            bean.setUtente(l.get(i).getUtente());
            bean.setRecensioneString(l.get(i).getRecensioneString());

            bean.setDateString(l.get(i).getData().format(DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_PATTERN)));

            bean.setStar(l.get(i).getValutazione());
            recensioni.add(bean);
        }
    }

    public static void showDescription(int item){


        Dialog<String> dialog1 = new Dialog<>();
        dialog1.setTitle("Descrizione");

        ButtonType loginButtonType = new ButtonType("Ok", ButtonBar.ButtonData.LEFT);

        dialog1.getDialogPane().getButtonTypes().addAll(loginButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        Node loginButton = dialog1.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(false);

        TextArea txtArea = new TextArea();
        txtArea.setWrapText(true);
        txtArea.setEditable(false);
        txtArea.setText(luoghi.get(item).getDescrizione());
        grid.add(txtArea, 0, 0);
        dialog1.getDialogPane().setContent(grid);

        dialog1.setResultConverter(dialogButton -> null);
        dialog1.show();

    }

    public static void showRecensione(int item){


        Dialog<String> dialogRecensione = new Dialog<>();
        dialogRecensione.setTitle("Recensione");

        ButtonType loginButtonType = new ButtonType("Ok", ButtonBar.ButtonData.LEFT);

        dialogRecensione.getDialogPane().getButtonTypes().addAll(loginButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        Node loginButton = dialogRecensione.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(false);

        TextArea txtArea = new TextArea();
        txtArea.setWrapText(true);
        txtArea.setEditable(false);
        txtArea.setText(recensioni.get(item).getRecensioneString());
        grid.add(txtArea, 0, 0);
        dialogRecensione.getDialogPane().setContent(grid);

        dialogRecensione.setResultConverter(dialogButton -> null);
        dialogRecensione.show();

    }
}
