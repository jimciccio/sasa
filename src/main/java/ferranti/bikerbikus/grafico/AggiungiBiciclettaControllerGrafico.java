package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.controllers1.AggiungiBiciclettaController1;
import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.BiciclettaNoleggio;
import ferranti.bikerbikus.models.BiciclettaVendita;
import ferranti.bikerbikus.utils.LoadScene;
import ferranti.bikerbikus.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AggiungiBiciclettaControllerGrafico {

    private Parent parent;
    final Object controller = this;

    @FXML
    TextField txtCaratteristicheNuove;
    @FXML
    Label lblUserName;
    @FXML
    Label lblTipoUtente;
    @FXML
    TextField txtModelloNuovo;
    @FXML
    TextField txtPrezzoNuovo;
    @FXML
    TextField txtDisponibili;
    @FXML
    Button btnBackAggB;
    @FXML
    Button btnConfermaNuovaAggB;
    @FXML
    TextField txtModelloNoleggiabile;
    @FXML
    TextField txtCaratteristicheNoleggiabile;
    @FXML
    Button btnConfermaNoleggiabile;
    @FXML
    TextField txtPrezzoNoleggiabile;

    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/aggiungi-bicicletta-view.fxml");
        btnBackAggB.setOnAction(event -> new ShopControllerGrafico().showScene(stage));
        lblUserName.setText(Utils.uppercase(UserData.getInstance().getUser().getNome()) + " "
                + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        btnConfermaNuovaAggB.setOnAction(event -> {
            if (txtModelloNuovo.getText().isBlank() || txtCaratteristicheNuove.getText().isBlank() || txtPrezzoNuovo.getText().isBlank() ||txtDisponibili.getText().isBlank()) {
                new Alert(Alert.AlertType.WARNING, "Inserisci tutti i dati!").show();
            }else{
            BiciclettaVendita biciclettaVendita = new BiciclettaVendita();
            biciclettaVendita.setDisponibili(Integer.parseInt(txtDisponibili.getText()));
            biciclettaVendita.setModello(txtModelloNuovo.getText());
            biciclettaVendita.setCaratteristiche(txtCaratteristicheNuove.getText());
            biciclettaVendita.setPrezzo(Integer.parseInt(txtPrezzoNuovo.getText()));

            if(AggiungiBiciclettaController1.checkBiciclettaNuova(biciclettaVendita)){
                new Alert(Alert.AlertType.CONFIRMATION, "Modello "+biciclettaVendita.getModello()+" già esistente", ButtonType.OK).show();

            }else {
                if (AggiungiBiciclettaController1.onActionConfermaNuova(biciclettaVendita)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Bicicletta creata con successo!", ButtonType.OK).show();
                    new AggiungiBiciclettaControllerGrafico().showScene(stage);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Non è stato possibile inserire la bicicletta").show();
                }
            }
            }
        });
        item(stage);
    }
    public void item(Stage stages){
        btnConfermaNoleggiabile.setOnAction(event -> {
            if (txtModelloNoleggiabile.getText().isBlank() || txtCaratteristicheNoleggiabile.getText().isBlank() || txtPrezzoNoleggiabile.getText().isBlank()) {
                new Alert(Alert.AlertType.WARNING, "Inserisci tutti i dati!").show();
            }else{
                BiciclettaNoleggio biciclettaNoleggio = new BiciclettaNoleggio();
                biciclettaNoleggio.setPrezzo(Integer.parseInt(txtPrezzoNoleggiabile.getText()));
                biciclettaNoleggio.setModello(txtModelloNoleggiabile.getText());
                biciclettaNoleggio.setCaratteristiche(txtCaratteristicheNoleggiabile.getText());

                if (AggiungiBiciclettaController1.onActionConfermaNoleggiabile(biciclettaNoleggio)) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Bicicletta creata con successo!", ButtonType.OK).show();
                    new AggiungiBiciclettaControllerGrafico().showScene(stages);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Non è stato possibile inserire la bicicletta").show();
                }
            }
        });
    }
}
