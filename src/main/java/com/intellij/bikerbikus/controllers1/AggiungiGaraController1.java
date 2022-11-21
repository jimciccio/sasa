package com.intellij.bikerbikus.controllers1;

import com.intellij.bikerbikus.models.Gara;
import com.intellij.bikerbikus.models.Stagione;
import com.intellij.bikerbikus.queries.CampionatiQuery;
import com.intellij.bikerbikus.queries.InsertGaraQuery;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AggiungiGaraController1 {

    protected AggiungiGaraController1(){}

    protected static final List<LocalTime> orari = new ArrayList<>();
    protected static final List<Stagione> stagioni = new ArrayList<>();



    public static boolean onActionConferma(Gara gara) {
        boolean expression = false;
        if (InsertGaraQuery.execute(gara)) {
            expression = true;
            }else{
                expression = false;
            }
        return expression;
    }

    public static  List<LocalTime> loadOrari() {
        for (int h = 0; h < 24; h++) {
            for (int m = 0; m < 60; m += 15) {
                orari.add(LocalTime.of(h, m));
            }
        }
        return orari;
    }

    public static  List<Stagione> loadStagioni() {
        stagioni.clear();
        stagioni.addAll(CampionatiQuery.findCampionatiAperti());
        return stagioni;
    }
}
