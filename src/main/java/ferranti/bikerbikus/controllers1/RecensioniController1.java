package ferranti.bikerbikus.controllers1;

import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Luoghi;
import ferranti.bikerbikus.models.Recensione;
import ferranti.bikerbikus.queries.InsertRecensioneQuery;
import ferranti.bikerbikus.queries.LuoghiQuery;
import ferranti.bikerbikus.queries.RecensioneQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecensioniController1 {

    protected RecensioniController1(){}

    protected static final List<Recensione> recensioni = new ArrayList<>();
    protected static final List<Luoghi> luoghi = new ArrayList<>();

    public static List<Luoghi> loadLuoghi() {
        luoghi.clear();
        luoghi.addAll(LuoghiQuery.findLuoghi());
        return luoghi;
    }

    public static List<Recensione> loadRecensioni(int idLuogo) {
        recensioni.clear();
        recensioni.addAll(RecensioneQuery.findRecensioniLuogo(idLuogo));
        return recensioni;
    }

    public static boolean recensisciLuogo(int idLuogo, String testo, LocalDate data, double rating) {

                if(InsertRecensioneQuery.insertRecensione(idLuogo, UserData.getInstance().getUser().getId(), testo, data, rating)){
                    LuoghiQuery.insertValutazione(idLuogo);
                    loadRecensioni(idLuogo);
                    return true;
                }else{
                    return false;
                }
    }
}
