package com.intellij.bikerbikus.controllers1;

import com.intellij.bikerbikus.data.UserData;
import com.intellij.bikerbikus.models.Recensione;
import com.intellij.bikerbikus.queries.RecensioneQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModificaRecensioneController1 {

    protected ModificaRecensioneController1(){}

    protected static final List<Recensione> recensioni = new ArrayList<>();

    public static void loadRecensioni() {
        recensioni.clear();
        recensioni.addAll(RecensioneQuery.findRecensioniUtente(UserData.getInstance().getUser().getId()));
    }

    public static boolean onActionElimina(int idRecensione){
        if(RecensioneQuery.deleteRecensione(idRecensione)){
            loadRecensioni();
            return true;
        }else{
            return false;
        }
    }

    public static boolean onActionModifica(int idRecensione, String recensione, LocalDate data, Double valutazione ) {

                if(RecensioneQuery.updateRecensione(idRecensione, recensione, data, valutazione)){
                    loadRecensioni();
                    return true;
                }else{
                    return false;
                }

    }

    public static double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }
}