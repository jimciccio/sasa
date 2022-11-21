package com.intellij.bikerbikus.models;

import com.intellij.bikerbikus.utils.Utils;

public class TipoUtente {

    private int id;
    private String nome;

    public TipoUtente() {
    }

    public TipoUtente(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return Utils.uppercase(nome);
    }
}