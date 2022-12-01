package ferranti.bikerbikus;

import ferranti.bikerbikus.models.Gara;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

public class BeanGare extends Gara {

    private Button buttonGara;
    private Hyperlink link;

    public Button getButtonGara() {
        return buttonGara;
    }

    public void setButtonGara(Button buttonGara) {
        this.buttonGara = buttonGara;
    }

    public Hyperlink getLink() {
        return link;
    }

    public void setLink(Hyperlink link) {
        this.link = link;
    }
}
