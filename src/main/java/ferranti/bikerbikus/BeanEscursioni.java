package ferranti.bikerbikus;

import ferranti.bikerbikus.models.Escursione;
import javafx.scene.control.Button;

public class BeanEscursioni extends Escursione {

    private Button buttonEscursione;
    private String oraString;
    private String giornoString;
    private String difficoltaString;
    private String accompagnatoreString;

    public Button getButtonEscursione() {
        return buttonEscursione;
    }

    public void setButtonEscursione(Button buttonEscursione) {
        this.buttonEscursione = buttonEscursione;
    }

    public String getOraString() {
        return oraString;
    }

    public void setOraString(String oraString) {
        this.oraString = oraString;
    }

    public String getGiornoString() {
        return giornoString;
    }

    public void setGiornoString(String giornoString) {
        this.giornoString = giornoString;
    }

    public String getDifficoltaString() {
        return difficoltaString;
    }

    public void setDifficoltaString(String difficoltaString) {
        this.difficoltaString = difficoltaString;
    }

    public String getAccompagnatoreString() {
        return accompagnatoreString;
    }

    public void setAccompagnatoreString(String accompagnatoreString) {
        this.accompagnatoreString = accompagnatoreString;
    }
}
