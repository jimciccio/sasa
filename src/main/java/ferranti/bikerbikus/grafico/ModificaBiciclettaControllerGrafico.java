package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.controllers1.ModificaBiciclettaController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.BiciclettaNoleggio;
import ferranti.bikerbikus.models.BiciclettaVendita;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalTime;

public class ModificaBiciclettaControllerGrafico extends ModificaBiciclettaController1{

    private Parent parent;
    final Object controller = this;
    static String modStr ="Modifica";

    protected static final ObservableList<LocalTime> orari = FXCollections.observableArrayList(ModificaBiciclettaController1.orari);
    protected static final ObservableList<BiciclettaVendita> bicicletteVendita = FXCollections.observableArrayList(ModificaBiciclettaController1.loadModelli());
    protected static final ObservableList<BiciclettaNoleggio> bicicletteNoleggiate = FXCollections.observableArrayList(ModificaBiciclettaController1.loadModelliNoleggiabili());


    @FXML
    Button btnBack;
    @FXML
    Label lblUserName;
    @FXML
    Label lblTipoUtente;
    @FXML
    TableView<BiciclettaVendita> tableBiciclette;
    @FXML
    TableColumn<BiciclettaVendita, String> colModello;
    @FXML
    TableColumn<BiciclettaVendita, String> colCaratteristiche;
    @FXML
    TableColumn<BiciclettaVendita, Integer> colPrezzo;
    @FXML
    TableColumn<BiciclettaVendita, Integer> colDisponibili;
    @FXML
    TableColumn<BiciclettaVendita, Integer> colModifica;
    @FXML
    TableView<BiciclettaNoleggio> tableBicicletteNoleggiate;
    @FXML
    TableColumn<BiciclettaNoleggio, String> colModelloNoleggiate;
    @FXML
    TableColumn<BiciclettaNoleggio, String> colCaratteristicheNoleggiate;
    @FXML
    TableColumn<BiciclettaNoleggio, Integer> colPrezzoNoleggiate;
    @FXML
    TableColumn<BiciclettaNoleggio, String> colPrenotazioneNoleggiate;
    @FXML
    TableColumn<BiciclettaNoleggio, Integer> colModificaNoleggiate;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/modifica-bicicletta-view.fxml");
        btnBack.setOnAction(event -> new ShopControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        colModello.setCellValueFactory(new PropertyValueFactory<>("modello"));
        colCaratteristiche.setCellValueFactory(new PropertyValueFactory<>("caratteristiche"));
        colPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        colDisponibili.setCellValueFactory(new PropertyValueFactory<>("disponibili"));

        colModifica.setCellValueFactory(new PropertyValueFactory<>("button"));
        tableBiciclette.setItems(bicicletteVendita);
        colModelloNoleggiate.setCellValueFactory(new PropertyValueFactory<>("modello"));
        colCaratteristicheNoleggiate.setCellValueFactory(new PropertyValueFactory<>("caratteristiche"));
        colPrezzoNoleggiate.setCellValueFactory(new PropertyValueFactory<>("prezzo"));

        colPrenotazioneNoleggiate.setCellValueFactory(new PropertyValueFactory<>("status"));

        colModificaNoleggiate.setCellValueFactory(new PropertyValueFactory<>("button"));
        tableBicicletteNoleggiate.setItems(bicicletteNoleggiate);
        bicicletteVendita.clear();
        bicicletteNoleggiate.clear();

        ModificaBiciclettaController1.loadModelli();
        ModificaBiciclettaController1.loadModelliNoleggiabili();

        bicicletteVendita.addAll(ModificaBiciclettaController1.bicicletteVendita);
        bicicletteNoleggiate.addAll(ModificaBiciclettaController1.bicicletteNoleggiate);
        createButtonVendita();
        createButtonNoleggio();
    }




    public static void modifyBiciclettaNuova(int item){

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Modifica la Bicicletta");
        dialog.setHeaderText("Modifica :");

        ButtonType loginButtonType = new ButtonType("Conferma", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField modello = new TextField();
        modello.setPromptText(ModificaBiciclettaController1.bicicletteVendita.get(item).getModello());

        TextField caratteristiche = new TextField();
        caratteristiche.setPromptText(ModificaBiciclettaController1.bicicletteVendita.get(item).getCaratteristiche());

        TextField prezzo = new TextField();
        prezzo.setPromptText(Integer.toString(ModificaBiciclettaController1.bicicletteVendita.get(item).getPrezzo()));

        TextField disponibili = new TextField();
        disponibili.setPromptText(Integer.toString(ModificaBiciclettaController1.bicicletteVendita.get(item).getDisponibili()));

        grid.add(new Label("Modello:"), 0, 0);
        grid.add(new Label("Caratteristiche:"), 0, 1);
        grid.add(new Label("Prezzo:"), 0, 2);
        grid.add(new Label("Disponibili:"), 0, 3);
        grid.add(modello, 1, 0);
        grid.add(caratteristiche, 1, 1);
        grid.add(prezzo, 1, 2);
        grid.add(disponibili, 1, 3);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        setItem(modello, caratteristiche, prezzo, disponibili, loginButton);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                if(modello.getText().equals("")){
                    modello.setText(modello.getPromptText());
                }
                if(caratteristiche.getText().equals("")){
                    caratteristiche.setText(caratteristiche.getPromptText());
                }
                if(prezzo.getText().equals("")){
                    prezzo.setText(prezzo.getPromptText());
                }
                if(disponibili.getText().equals("")){
                    disponibili.setText(disponibili.getPromptText());
                }
                ModificaBiciclettaController1.modBiciNuova(ModificaBiciclettaController1.bicicletteVendita.get(item).getId(), modello.getText(), caratteristiche.getText(),
                        Integer.parseInt(prezzo.getText()), Integer.parseInt(disponibili.getText()));

                bicicletteVendita.clear();
                bicicletteVendita.addAll(ModificaBiciclettaController1.bicicletteVendita);
                createButtonVendita();
            }
            return null;
        });
        dialog.showAndWait();
    }

    public static void setItem(TextField modello, TextField caratteristiche, TextField prezzo, TextField disponibili,  Node loginButton){

        modello.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(newValue.equals("") && caratteristiche.getText().equals("") && prezzo.getText().equals("")
                    && disponibili.getText().equals("")){
                loginButton.setDisable(true);
            }
        });

        caratteristiche.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(newValue.equals("") && modello.getText().equals("") && prezzo.getText().equals("")
                    && disponibili.getText().equals("")){
                loginButton.setDisable(true);
            }
        });

        prezzo.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(newValue.equals("") && modello.getText().equals("") && caratteristiche.getText().equals("")
                    && disponibili.getText().equals("")){
                loginButton.setDisable(true);
            }
        });
        disponibili.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(newValue.equals("") && modello.getText().equals("") && caratteristiche.getText().equals("")
                    && prezzo.getText().equals("")){
                loginButton.setDisable(true);
            }
        });
    }

    public  void modifyBiciclettaNoleggiata(int item){

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Modifica la Bicicletta");
        dialog.setHeaderText("Modifica :");

        ButtonType loginButtonType = new ButtonType("Conferma", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        boolean oldValue1 = ModificaBiciclettaController1.bicicletteNoleggiate.get(item).getManutenzione() != 0;

        TextField modello = new TextField();
        modello.setPromptText(ModificaBiciclettaController1.bicicletteNoleggiate.get(item).getModello());

        TextField caratteristiche = new TextField();
        caratteristiche.setPromptText(ModificaBiciclettaController1.bicicletteNoleggiate.get(item).getCaratteristiche());

        TextField prezzo = new TextField();
        prezzo.setPromptText(Integer.toString(ModificaBiciclettaController1.bicicletteNoleggiate.get(item).getPrezzo()));

        CheckBox manutenzione = new CheckBox();

        grid.add(new Label("Modello:"), 0, 0);
        grid.add(new Label("Caratteristiche:"), 0, 1);
        grid.add(new Label("Prezzo:"), 0, 2);
        grid.add(modello, 1, 0);
        grid.add(caratteristiche, 1, 1);
        grid.add(prezzo, 1, 2);

        if(ModificaBiciclettaController1.bicicletteNoleggiate.get(item).getNoleggiabile()!=0){
            grid.add(new Label("Manutenzione:"), 0, 3);
            grid.add(manutenzione, 1, 3);
        }
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        setItem1(modello, caratteristiche, prezzo, manutenzione, loginButton, oldValue1, item);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                if(modello.getText().equals("")){
                    modello.setText(modello.getPromptText());
                }
                if(caratteristiche.getText().equals("")){
                    caratteristiche.setText(caratteristiche.getPromptText());
                }
                if(prezzo.getText().equals("")){
                    prezzo.setText(prezzo.getPromptText());
                }
                ModificaBiciclettaController1.modBiciNoleggiata(ModificaBiciclettaController1.bicicletteNoleggiate.get(item).getId(), modello.getText(), caratteristiche.getText(), Integer.parseInt(prezzo.getText()), manutenzione.isSelected());
                bicicletteNoleggiate.clear();
                bicicletteNoleggiate.addAll(ModificaBiciclettaController1.bicicletteNoleggiate);
                createButtonNoleggio();
            }
            return null;
        });
        dialog.showAndWait();
    }

    public static void setItem1(TextField modello, TextField caratteristiche, TextField prezzo, CheckBox manutenzione, Node loginButton, boolean oldValue1, int item){
        modello.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(newValue.equals("") && caratteristiche.getText().equals("") && prezzo.getText().equals("") && manutenzione.isSelected() == oldValue1){
                loginButton.setDisable(true);
            }
        });

        caratteristiche.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(newValue.equals("") && modello.getText().equals("") && prezzo.getText().equals("") && manutenzione.isSelected() ==oldValue1){
                loginButton.setDisable(true);
            }
        });

        prezzo.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(newValue.equals("") && modello.getText().equals("") && caratteristiche.getText().equals("") && manutenzione.isSelected() == oldValue1){
                loginButton.setDisable(true);
            }
        });

        if(ModificaBiciclettaController1.bicicletteNoleggiate.get(item).getManutenzione()==1){
            manutenzione.setSelected(true);
        }

        manutenzione.selectedProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(false);
            if(manutenzione.isSelected()== oldValue1 && modello.getText().equals("") && caratteristiche.getText().equals("") && prezzo.getText().equals("")){
                loginButton.setDisable(true);
            }
        });
    }

    public static void createButtonVendita(){
        for(int i = 0; i< ModificaBiciclettaController1.bicicletteVendita.size(); i++) {
            Button b = new Button(modStr);
            b.setPrefSize(150, 20);
            bicicletteVendita.get(i).setButton(b);

            int finalI = i;
            b.setOnAction(event -> modifyBiciclettaNuova(finalI));
        }
    }

    public  void createButtonNoleggio(){
        for(int i = 0; i< ModificaBiciclettaController1.bicicletteNoleggiate.size(); i++) {
            Button b = new Button(modStr);
            b.setPrefSize(150, 20);
            bicicletteNoleggiate.get(i).setButton(b);

            int finalI = i;
            b.setOnAction(event -> modifyBiciclettaNoleggiata(finalI));

            if(bicicletteNoleggiate.get(i).getNoleggiabile()==1){
                if(bicicletteNoleggiate.get(i).getManutenzione()==1){
                    bicicletteNoleggiate.get(i).setStatus("Manutenzione");
                }else{
                    bicicletteNoleggiate.get(i).setStatus("Noleggiabile");
                }
            }else{
                bicicletteNoleggiate.get(i).setStatus("Noleggiata");
            }
        }
    }
}
