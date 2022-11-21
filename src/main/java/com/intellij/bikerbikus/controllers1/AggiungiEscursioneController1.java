package com.intellij.bikerbikus.controllers1;

import com.intellij.bikerbikus.models.Escursione;
import com.intellij.bikerbikus.models.Luoghi;
import com.intellij.bikerbikus.models.Utente;
import com.intellij.bikerbikus.queries.InsertEscursioneQuery;
import com.intellij.bikerbikus.queries.LuoghiQuery;
import com.intellij.bikerbikus.queries.UtentiQuery;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AggiungiEscursioneController1 {

    protected AggiungiEscursioneController1(){}

    protected static final List<LocalTime> orari = new ArrayList<>();
    protected static final List<Utente> accompagnatori = new ArrayList<>();
    protected static final List<Luoghi> luoghi = new ArrayList<>();

    public static boolean onActionConferma(Escursione escursione) {
        boolean expression = false;
            if (InsertEscursioneQuery.execute(escursione)) {
                expression = true;
            }else{
                expression = false;
            }
            return expression;
    }

    public static List<LocalTime> loadOrari() {
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m += 15) {
                orari.add(LocalTime.of(h, m));
            }
        }
        return orari;
    }

    public static List<Utente> loadAccompagnatori() {
        accompagnatori.clear();
        accompagnatori.addAll(UtentiQuery.findMaestri());
        return accompagnatori;
    }

    public static List<Luoghi> loadLuoghi() {
        luoghi.clear();
        luoghi.addAll(LuoghiQuery.findLuoghi());
        return luoghi;
    }
}