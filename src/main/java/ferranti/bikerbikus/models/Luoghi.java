package ferranti.bikerbikus.models;

import ferranti.bikerbikus.utils.Utils;

public class Luoghi {

    private int id;
    private String nome;
    private String descrizione;
    private int difficolta;
    private Double valutazioneMedia;

    public Luoghi() {
    }

    public Luoghi(int id, String nome, String descrizione, int difficolta, Double valutazioneMedia) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.difficolta = difficolta;
        this.valutazioneMedia = valutazioneMedia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getDifficolta() {
        return difficolta;
    }

    public void setDifficolta(int difficolta) {
        this.difficolta = difficolta;
    }

    public Double getValutazioneMedia() {
        return valutazioneMedia;
    }

    public void setValutazioneMedia(Double valutazioneMedia) {
        this.valutazioneMedia = valutazioneMedia;
    }

    @Override
    public String toString() {
        return Utils.uppercase(nome);
    }
}