package ferranti.bikerbikus.controllers1;

import ferranti.bikerbikus.models.Luoghi;
import ferranti.bikerbikus.queries.LuoghiQuery;

import java.util.ArrayList;
import java.util.List;

public class ModificaLuogoController1 {

    protected ModificaLuogoController1(){}

    protected static final List<Luoghi> luoghi = new ArrayList<>();

    public static void loadLuoghi() {
        luoghi.clear();
        luoghi.addAll(LuoghiQuery.findLuoghi());
    }

    public static boolean onActionConferma(String nome, String descrizione, String difficolta) {

            if (LuoghiQuery.insertLuogo(nome,descrizione, Integer.parseInt(difficolta))) {
                loadLuoghi();
                return true;
            }else{
                return false;
            }
        }

    public static void modLuogo(int id, String nome, String descrizione, int difficolta){

                LuoghiQuery.updateLuogo(id, nome, descrizione, difficolta);
                loadLuoghi();
    }
}
