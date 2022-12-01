package ferranti.bikerbikus;

import ferranti.bikerbikus.models.Luoghi;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.controlsfx.control.Rating;

public class BeanLuoghi extends Luoghi {

    private Button button;
    private Rating star;
    private Button buttonDescrizione;


    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Button getButtonDescrizione() {
        return buttonDescrizione;
    }

    public void setButtonDescrizione(Button buttonDescrizione) {
        this.buttonDescrizione = buttonDescrizione;
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
}
