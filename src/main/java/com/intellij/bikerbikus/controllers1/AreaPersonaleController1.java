package com.intellij.bikerbikus.controllers1;

import com.intellij.bikerbikus.data.UserData;
import com.intellij.bikerbikus.models.*;
import com.intellij.bikerbikus.queries.AreaPersonaleQuery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AreaPersonaleController1 {

    protected AreaPersonaleController1(){}

    protected static final List<Lezione> lezioni = new ArrayList<>();
    protected static final List<Gara> gare = new ArrayList<>();
    protected static final List<Escursione> escursioni = new ArrayList<>();
    protected static final List<BiciclettaVendita> bicicletteComprate = new ArrayList<>();
    protected static final List<BiciclettaNoleggio> bicicletteNoleggiate = new ArrayList<>();

    public static List<Lezione> showLezioni(){
        lezioni.clear();
        lezioni.addAll(AreaPersonaleQuery.findLezioni(UserData.getInstance().getUser().getId()));
        return lezioni;
    }

    public static List<Gara> showGare(){
        gare.clear();
        gare.addAll(AreaPersonaleQuery.findGare(UserData.getInstance().getUser().getId()));
        return gare;
    }

    public static List<Escursione> showEscursioni(){
        escursioni.clear();
        escursioni.addAll(AreaPersonaleQuery.findEscursioni(UserData.getInstance().getUser().getId()));
        return escursioni;
    }

    public static List<BiciclettaVendita> showBiciComprate(){
        bicicletteComprate.clear();
        bicicletteComprate.addAll(AreaPersonaleQuery.findBicicletteComprate(UserData.getInstance().getUser().getId()));
        return bicicletteComprate;
    }

    public static List<BiciclettaNoleggio> showBiciNoleggiate(){
        bicicletteNoleggiate.clear();
        bicicletteNoleggiate.addAll(AreaPersonaleQuery.findBicicletteNoleggiate(UserData.getInstance().getUser().getId()));
        return bicicletteNoleggiate;
    }


    public static void disdiciLezione(int idLezione) {
            AreaPersonaleQuery.disdiciLezione(UserData.getInstance().getUser().getId(), idLezione);
            showLezioni();
    }

    public static void disdiciEscursione(int idEscursione) {
            AreaPersonaleQuery.disdiciEscursione(UserData.getInstance().getUser().getId(), idEscursione);
            showEscursioni();
    }
    public static void disdiciNoleggio(int idNoleggio) {
            AreaPersonaleQuery.disdiciNoleggio(UserData.getInstance().getUser().getId(), idNoleggio, LocalDateTime.now());
            AreaPersonaleQuery.ripristinaNoleggiabilita(idNoleggio);
            showBiciNoleggiate();
    }
}
