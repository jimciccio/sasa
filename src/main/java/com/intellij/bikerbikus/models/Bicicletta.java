package com.intellij.bikerbikus.models;

import java.time.LocalDateTime;

public class Bicicletta {

    int id;
    String modello;
    String caratteristiche;



    public Bicicletta() {
    }

    public Bicicletta(int id, String modello, String caratteristiche, int prezzo) {
        this.id = id;
        this.modello = modello;
        this.caratteristiche = caratteristiche;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public String getCaratteristiche() {
        return caratteristiche;
    }

    public void setCaratteristiche(String caratteristiche) {
        this.caratteristiche = caratteristiche;
    }

}