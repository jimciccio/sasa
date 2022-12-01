package ferranti.bikerbikus;

import ferranti.bikerbikus.models.Lezione;
import javafx.scene.control.Button;

public class BeanLezioni extends Lezione {

    private Button buttonLezione;
    private String dateString;
    private String tipoString;
    private String privataString;
    private String oraString;
    private String giornoString;


    public Button getButtonLezione() {
        return buttonLezione;
    }


    public void setButtonLezione(Button button) {
        this.buttonLezione = button;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getTipoString() {
        return tipoString;
    }

    public void setTipoString(String tipoString) {
        this.tipoString = tipoString;
    }

    public String getPrivataString() {
        return privataString;
    }

    public void setPrivataString(String privataString) {
        this.privataString = privataString;
    }

    public String getGiornoString() {
        return giornoString;
    }

    public void setGiornoString(String giornoString) {
        this.giornoString = giornoString;
    }

    public String getOraString() {
        return oraString;
    }

    public void setOraString(String oraString) {
        this.oraString = oraString;
    }
}
