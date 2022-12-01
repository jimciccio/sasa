package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.BeanLuoghi;
import ferranti.bikerbikus.controllers1.ModificaLuogoController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Luoghi;
import ferranti.bikerbikus.utils.LoadScene;
import ferranti.bikerbikus.utils.Utils;
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
import javafx.stage.Stage;

import java.util.List;

public class ModificaLuogoControllerGrafico extends ModificaLuogoController1{

    private Parent parent;
    final Object controller = this;

    protected static final ObservableList<BeanLuoghi> luog = FXCollections.observableArrayList();

    @FXML
    Button btnBack;
    @FXML
    Label lblUserName;
    @FXML
    TableView<BeanLuoghi> tableLuoghi;
    @FXML
    TableColumn<String, String> colDescrizione;
    @FXML
    TableColumn<BeanLuoghi, Integer> colDifficolta;
    @FXML
    TableColumn<BeanLuoghi, Double> colValutazioneMedia;
    @FXML
    TableColumn<BeanLuoghi, Integer> colModifica;
    @FXML
    Label lblTipoUtente;
    @FXML
    Button btnProfile;
    @FXML
    TableColumn<BeanLuoghi, String> colNomeLuogo;
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

        colNomeLuogo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescrizione.setCellValueFactory(new PropertyValueFactory<>("buttonDescrizione"));
        colValutazioneMedia.setCellValueFactory(new PropertyValueFactory<>("star"));
        colDifficolta.setCellValueFactory(new PropertyValueFactory<>("difficolta"));
        setItem1();
        setItem();
    }

    public void setItem1(){

        colModifica.setCellValueFactory(new PropertyValueFactory<>("button"));
        tableLuoghi.setRowFactory(tv -> {
            TableRow<BeanLuoghi> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    row.getItem();
                }
            });
            return row ;
        });
        tableLuoghi.setItems(luog);
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
        ModificaLuogoController1.loadLuoghi();

        copy(luoghi);
        createButton();
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
            }
            return null;
        });
        dialog.showAndWait();
    }

    public static void showDescription(int item){


        Dialog<String> dialogDescription = new Dialog<>();
        dialogDescription.setTitle("Descrizione");

        ButtonType loginButtonType = new ButtonType("Ok", ButtonBar.ButtonData.LEFT);

        dialogDescription.getDialogPane().getButtonTypes().addAll(loginButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        Node loginButton = dialogDescription.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(false);

        TextArea txtArea = new TextArea();
        txtArea.setWrapText(true);
        txtArea.setEditable(false);
        txtArea.setText(luog.get(item).getDescrizione());
        grid.add(txtArea, 0, 0);
        dialogDescription.getDialogPane().setContent(grid);

        dialogDescription.setResultConverter(dialogButton -> null);
        dialogDescription.show();

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

    public void createButton(){
        for(int i = 0; i< luog.size(); i++) {
            Button b = new Button("Modifica");
            Button c = new Button("Descrizione");
            b.setPrefSize(150, 20);
            luog.get(i).setButton(b);
            luog.get(i).setButtonDescrizione(c);

            int finalI = i;
            b.setOnAction(event -> modificaLuogo(finalI,luog.get(finalI).getId()));
            int finalI1 = i;
            c.setOnAction(event -> showDescription(finalI1));
        }
    }

    public void copy(List<Luoghi> l){
        for(int i=0; i<l.size();i++){
            BeanLuoghi bean = new BeanLuoghi();

            bean.setId(l.get(i).getId());
            bean.setNome(l.get(i).getNome());
            bean.setDescrizione(l.get(i).getDescrizione());

            bean.setDifficolta(l.get(i).getDifficolta());
            bean.setStar(l.get(i).getValutazioneMedia());
            luog.add(bean);
        }
    }
}