package ferranti.bikerbikus.grafico;

import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.utils.LoadScene;
import ferranti.bikerbikus.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeControllerGrafico {

    private Parent parent;
    final Object controller = this;

    @FXML
    Label lblUserName;
    @FXML
    Label lblTipoUtente;
    @FXML
    Button btnLezioni;
    @FXML
    Button btnProfile;
    @FXML
    Button btnCampionati;
    @FXML
    Button btnGare;
    @FXML
    Button btnShop;
    @FXML
    Button btnEscursioni;


    public void showScene(Stage stage) {
        LoadScene loadScene = new LoadScene();
        loadScene.load(stage,parent,controller, "/views/home-view.fxml");
        lblUserName.setText(
                Utils.uppercase(UserData.getInstance().getUser().getNome()) + " " + Utils.uppercase(UserData.getInstance().getUser().getCognome()));
        lblTipoUtente.setText(UserData.getInstance().getUser().getTipoUtente().getNome());
        btnProfile.setOnAction(event -> new AreaPersonaleControllerGrafico().showScene(stage));
        btnLezioni.setOnAction(event -> new LezioniControllerGrafico().showScene(stage));
        btnCampionati.setOnAction(event -> new CampionatiControllerGrafico().showScene(stage));
        btnGare.setOnAction(event -> new GareControllerGrafico().showScene(stage));
        btnEscursioni.setOnAction(event -> new EscursioniControllerGrafico().showScene(stage));
        btnShop.setOnAction(event -> new ShopControllerGrafico().showScene(stage));
    }
}
