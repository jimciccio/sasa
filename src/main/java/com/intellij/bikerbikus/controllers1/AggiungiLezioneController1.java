package com.intellij.bikerbikus.controllers1;

import com.intellij.bikerbikus.models.Lezione;
import com.intellij.bikerbikus.models.TipoLezione;
import com.intellij.bikerbikus.models.Utente;
import com.intellij.bikerbikus.queries.InsertLezioneQuery;
import com.intellij.bikerbikus.queries.TipiLezioneQuery;
import com.intellij.bikerbikus.queries.UtentiQuery;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AggiungiLezioneController1 {

    protected AggiungiLezioneController1(){}

    protected static final List<LocalTime> orari = new ArrayList<>();
    protected static final List<TipoLezione> tipi = new ArrayList<>();
    protected static final List<Utente> maestri = new ArrayList<>();

    public static boolean onActionConferma(Lezione lezione) {
        boolean expression = false;

        if (InsertLezioneQuery.execute(lezione)) {
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

    public static List<TipoLezione> loadTipi() {
        tipi.clear();
        tipi.addAll(TipiLezioneQuery.execute());
        return tipi;
    }

    public static List<Utente> loadMaestri() {
        maestri.clear();
        maestri.addAll(UtentiQuery.findMaestri());
        return maestri;
    }
}
