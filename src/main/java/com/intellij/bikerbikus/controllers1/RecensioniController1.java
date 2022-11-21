package com.intellij.bikerbikus.controllers1;

import com.intellij.bikerbikus.data.UserData;
import com.intellij.bikerbikus.models.Luoghi;
import com.intellij.bikerbikus.models.Recensione;
import com.intellij.bikerbikus.queries.InsertRecensioneQuery;
import com.intellij.bikerbikus.queries.LuoghiQuery;
import com.intellij.bikerbikus.queries.RecensioneQuery;
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
