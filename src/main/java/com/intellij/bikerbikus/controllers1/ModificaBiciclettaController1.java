package com.intellij.bikerbikus.controllers1;

import com.intellij.bikerbikus.models.BiciclettaNoleggio;
import com.intellij.bikerbikus.models.BiciclettaVendita;
import com.intellij.bikerbikus.queries.AggiungiBiciclettaQuery;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ModificaBiciclettaController1 {

    protected ModificaBiciclettaController1(){}

    protected static final List<LocalTime> orari = new ArrayList<>();
    protected static final List<BiciclettaVendita> bicicletteVendita = new ArrayList<>();
    protected static final List<BiciclettaNoleggio> bicicletteNoleggiate = new ArrayList<>();

    public static List<BiciclettaVendita> loadModelli() {
        bicicletteVendita.clear();
        bicicletteVendita.addAll(AggiungiBiciclettaQuery.findBicicletteComprabili());
        return bicicletteVendita;
    }

    public static List<BiciclettaNoleggio> loadModelliNoleggiabili() {
        bicicletteNoleggiate.clear();
        bicicletteNoleggiate.addAll(AggiungiBiciclettaQuery.findBicicletteNoleggiabili());
        return bicicletteNoleggiate;
    }

    public static void modBiciNuova(int id, String modello, String caratteristiche, int prezzo, int disponibili){
                AggiungiBiciclettaQuery.updateBiciclettaComprabile(id, modello, caratteristiche, prezzo, disponibili);
                loadModelli();
    }

    public static void modBiciNoleggiata(int id, String modello, String caratteristiche, int prezzo, boolean manutenzione){

                AggiungiBiciclettaQuery.updateBiciclettaNoleggiabile(id, modello, caratteristiche, prezzo, manutenzione);
                AggiungiBiciclettaQuery.updateBiciclettaNoleggiate(id, modello, caratteristiche);
                loadModelliNoleggiabili();
    }
}
