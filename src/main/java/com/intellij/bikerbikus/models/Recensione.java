package com.intellij.bikerbikus.models;

import java.time.LocalDate;

public class Recensione {

    private int id;
    private Luoghi luogo;
    private int idLuogo;
    private int idUtente;
    private String recensioneString;
    private LocalDate data;
    private Double valutazione;
    private Utente utente;

    public Recensione() {
    }

    public Recensione(int id, int idLuogo,int idUtente, String recensioneString, Double valutazione) {
        this.id = id;
        this.idLuogo = idLuogo;
        this.idUtente = idUtente;
        this.recensioneString = recensioneString;
        this.valutazione = valutazione;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLuogo() {
        return idLuogo;
    }

    public void setIdLuogo(int idLuogo) {
        this.idLuogo = idLuogo;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public String getRecensioneString() {
        return recensioneString;
    }

    public void setRecensioneString(String recensioneString) {
        this.recensioneString = recensioneString;
    }

    public LocalDate getData() { return data; }

    public void setData(LocalDate data) { this.data = data; }

    public Double getValutazione() {
        return valutazione;
    }

    public void setValutazione(Double valutazione) {
        this.valutazione = valutazione;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Luoghi getLuogo() {
        return luogo;
    }

    public void setLuogo(Luoghi luogo) {
        this.luogo = luogo;
    }
}