package ferranti.bikerbikus;

import ferranti.bikerbikus.models.Recensione;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.controlsfx.control.Rating;

public class BeanRecensioni extends Recensione {

    private Rating star;
    private Button buttonRecensione;
    private Button buttonModifica;
    private Button buttonElimina;


    private String dateString;

    public Button getButtonRecensione() {
        return buttonRecensione;
    }

    public void setButtonRecensione(Button buttonRecensione) {
        this.buttonRecensione = buttonRecensione;
    }

    public Button getButtonModifica() {
        return buttonModifica;
    }

    public void setButtonModifica(Button buttonModifica) {
        this.buttonModifica = buttonModifica;
    }

    public Button getButtonElimina() {
        return buttonElimina;
    }

    public void setButtonElimina(Button buttonElimina) {
        this.buttonElimina = buttonElimina;
    }

    public Rating getStar() {
        return star;
    }

    public void setStar(Double star1) {

        final HBox box = new HBox();
        box.setMaxHeight(20);
        box.setMaxWidth(30);
        Rating rating = new Rating(5);
        rating.setPartialRating(true);
        rating.setMaxWidth(20);
        rating.setScaleY(0.6);
        rating.setScaleX(0.6);
        rating.setMouseTransparent(true);
        box.getChildren().add(rating);
        rating.setRating(star1);

        this.star = rating;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}