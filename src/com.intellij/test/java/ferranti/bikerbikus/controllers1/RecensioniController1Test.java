package ferranti.bikerbikus.controllers1;

import ferranti.bikerbikus.models.Luoghi;
import ferranti.bikerbikus.models.Recensione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecensioniController1Test {

    @Test
    void loadLuoghi() {

        List observableList = RecensioniController1.loadLuoghi();

        assertTrue(observableList.size()>1);

    }
}