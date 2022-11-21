package com.intellij.bikerbikus.controllers1;

import com.intellij.bikerbikus.models.Campionato;
import com.intellij.bikerbikus.models.Stagione;
import com.intellij.bikerbikus.queries.CampionatiQuery;

import java.util.ArrayList;
import java.util.List;

public class AggiungiStagioneController1 {

    protected AggiungiStagioneController1(){}

    protected static final List<Campionato> campionati = new ArrayList<>();

    public static boolean onActionConferma(Stagione stagione) {
        boolean expression = false;
        if (CampionatiQuery.insertStagione(stagione)) {
            expression = true;
            }else{
            expression = false;
            }
        return expression;
    }

    public static List<Campionato> loadCampionati() {
        campionati.clear();
        campionati.addAll(CampionatiQuery.findCampionati());
        return campionati;
    }
}
