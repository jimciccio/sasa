package ferranti.bikerbikus.models;

public class UtenteExtended {

    private int id;
    private String nome;
    private String cognome;
    private int punteggio;
    private int gare;
    private int posizioneFinale;
    private int posizioneGara;
    private String ps1;
    private String ps2;
    private String ps3;
    private String tempo;

    public UtenteExtended() {
    }

    public UtenteExtended(int id, String nome, String cognome) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

    public int getGare() {
        return gare;
    }

    public void setGare(int gare) {
        this.gare = gare;
    }

    public int getPosizioneFinale() {
        return posizioneFinale;
    }

    public void setPosizioneFinale(int posizioneFinale) {
        this.posizioneFinale = posizioneFinale;
    }

    public String getPs1() {
        return ps1;
    }

    public void setPs1(String ps1) {
        this.ps1 = ps1;
    }

    public String getPs2() {
        return ps2;
    }

    public void setPs2(String ps2) {
        this.ps2 = ps2;
    }

    public String getPs3() {
        return ps3;
    }

    public void setPs3(String ps3) {
        this.ps3 = ps3;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public int getPosizioneGara() {
        return posizioneGara;
    }

    public void setPosizioneGara(int posizioneGara) {
        this.posizioneGara = posizioneGara;
    }
}