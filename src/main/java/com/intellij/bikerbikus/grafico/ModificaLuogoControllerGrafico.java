package com.intellij.bikerbikus.grafico;

import com.intellij.bikerbikus.models.Luoghi;
import com.intellij.bikerbikus.utils.LoadScene;
import com.intellij.bikerbikus.controllers1.ModificaLuogoController1;
import com.intellij.bikerbikus.data.UserData;
import com.intellij.bikerbikus.utils.Utils;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

public class ModificaLuogoControllerGrafico extends ModificaLuogoController1{

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<Luoghi> luoghi = FXCollections.observableArrayList(ModificaLuogoController1.luoghi);

    @FXML
    Button btnBack;
    @FXML
    Label lblUserName;
    @FXML
    TableView<Luoghi> tableLuoghi;
    @FXML
    TableColumn<Luoghi, String> colDescrizione;
    @FXML
    TableColumn<Luoghi, Integer> colDifficolta;
    @FXML
    TableColumn<Luoghi, Double> colValutazioneMedia;
    @FXML
    TableColumn<Luoghi, Integer> colModifica;
    @FXML
    Label lblTipoUtente;
    @FXML
    Button btnProfile;
    @FXML
    TableColumn<Luoghi, String> colNomeLuogo;
    @FXML
    TextField txtNome;
    @FXML
    TextField  txtDifficolta;
    @FXML
    Button btnConferma;
    @FXML
    TextArea  txtDescrizione;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/modifica-luogo-view.fxml");
        btnBack.setOnAction(event -> new RecensioniControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        btnProfile.setOnAction(event -> new AreaPersonaleControllerGrafico().showScene(stage));
        colDescrizione.setCellFactory(param -> new TableCell<>() {
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
        colNomeLuogo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colValutazioneMedia.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                final HBox box = new HBox();
                box.setMaxHeight(20);
                box.setMaxWidth(30);
                final Rating rating = new Rating(5);
                rating.setPartialRating(true);
                rating.setMaxWidth(20);
                rating.setScaleY(0.6);
                rating.setScaleX(0.6);
                rating.setMouseTransparent(true);
                if (getTableRow() != null && getTableRow().getItem() != null) {
                    box.getChildren().add(rating);
                    rating.setRating(item);
                }
                setGraphic(item == null ? null : box);
            }
        });
        colDescrizione.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        colValutazioneMedia.setCellValueFactory(new PropertyValueFactory<>("valutazioneMedia"));
        colDifficolta.setCellValueFactory(new PropertyValueFactory<>("difficolta"));
        setItem1();
        setItem();
    }

    public void setItem1(){
        colModifica.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                final Button btnModifica = new Button("Modifica");
                btnModifica.setPrefSize(150, 20);
                if (getTableRow() != null && getTableRow().getItem() != null) {
                    btnModifica.setOnAction(event -> modificaLuogo(getTableRow().getIndex(),item));
                }
                setGraphic(item == null ? null : btnModifica);
            }
        });
        colModifica.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableLuoghi.setRowFactory(tv -> {
            TableRow<Luoghi> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                        row.getItem();
                }
            });
            return row ;
        });
        tableLuoghi.setItems(luoghi);
    }

    public void setItem(){
        btnConferma.setOnAction(event -> {
            if (txtNome.getText().equals("") || txtDescrizione.getText().equals("") || txtDifficolta.getText().equals("")) {
                new Alert(Alert.AlertType.WARNING, "Inserisci tutti i dati!").show();
            } else {
                if(ModificaLuogoController1.onActionConferma(txtNome.getText(), txtDescrizione.getText(), txtDifficolta.getText())){
                    luoghi.clear();
                    new Alert(Alert.AlertType.CONFIRMATION, "Luogo creato con successo!", ButtonType.OK).show();
                    luoghi.addAll(ModificaLuogoController1.luoghi);
                    txtNome.setText("");
                    txtDescrizione.setText("");
                    txtDifficolta.setText("");
                }else{
                    new Alert(Alert.AlertType.ERROR, "Non è stato possibile creare il luogo!", ButtonType.OK).show();
                }
            }
        });
        luoghi.clear();
        ModificaLuogoController1.loadLuoghi();
        luoghi.addAll(ModificaLuogoController1.luoghi);
    }



    public static void modificaLuogo(int item, int idLuogo){

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Modifica il luogo");
        dialog.setHeaderText("Modifica");

        ButtonType loginButtonType = new ButtonType("Conferma", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nome = new TextField();
        nome.setPromptText(ModificaLuogoController1.luoghi.get(item).getNome());

        TextArea descrizione = new TextArea();
        descrizione.setPromptText(ModificaLuogoController1.luoghi.get(item).getDescrizione());

        TextField difficolta = new TextField();
        difficolta.setPromptText(Integer.toString(ModificaLuogoController1.luoghi.get(item).getDifficolta()));

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(new Label("Descrizione:"), 0, 1);
        grid.add(new Label("Difficoltà:"), 0, 2);
        grid.add(nome, 1, 0);
        grid.add(descrizione, 1, 1);
        grid.add(difficolta, 1, 2);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        nome.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(newValue.equals("") && descrizione.getText().equals("") && difficolta.getText().equals("")){
                loginButton.setDisable(true);
            }
        });

        descrizione.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(newValue.equals("") && nome.getText().equals("") && difficolta.getText().equals("") ){
                loginButton.setDisable(true);
            }
        });

        difficolta.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(newValue.equals("") && nome.getText().equals("") && descrizione.getText().equals("") ){
                loginButton.setDisable(true);
            }
        });

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {

            nome.setText(controlloa(nome.getText(),nome.getPromptText()));
            descrizione.setText(controllob(descrizione.getText(),descrizione.getPromptText()));
            difficolta.setText(controlloc(difficolta.getText(),difficolta.getPromptText()));


            if (dialogButton == loginButtonType) {
                ModificaLuogoController1.modLuogo(idLuogo, nome.getText(),descrizione.getText(),Integer.parseInt(difficolta.getText()));
                luoghi.clear();
                luoghi.addAll(ModificaLuogoController1.luoghi);
            }
            return null;
        });
        dialog.showAndWait();
    }

    public static String controlloa(String a, String a1){
        if(a.equals("")){
            return a1;
        }else{
            return a;
        }
    }
    public static String controllob(String b, String b1){
        if(b.equals("")){
            return b1;
        }else{
            return b;
        }
    }
    public static String controlloc(String c, String c1){
        if(c.equals("")){
            return c1;
        }else{
            return c;
        }
    }
}
