package ferranti.bikerbikus.models;

import javafx.scene.control.Button;

import java.time.LocalDateTime;

public class BiciclettaNoleggio extends Bicicletta{

    private int idNoleggio;
    private int idUtente;

    private LocalDateTime inizioNoleggio;
    private LocalDateTime fineNoleggio;
    private int prezzo;
    private int prezzoFinale;

    private int noleggiabile;
    private int manutenzione;
    private Button button;
    private String status;




    public BiciclettaNoleggio() {
    }

    public BiciclettaNoleggio(int idNoleggio, String modello, String caratteristiche, int prezzo) {
        this.idNoleggio = idNoleggio;
        this.modello = modello;
        this.caratteristiche = caratteristiche;
        this.prezzo = prezzo;
    }

    public int getIdNoleggio() {
        return idNoleggio;
    }

    public void setIdNoleggio(int idNoleggio) {
        this.idNoleggio = idNoleggio;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public int getIdUtente() {
        return idUtente;
    }


    public LocalDateTime getInizioNoleggio() {
        return inizioNoleggio;
    }

    public void setInizioNoleggio(LocalDateTime inizioNoleggio) {
        this.inizioNoleggio = inizioNoleggio;
    }

    public LocalDateTime getFineNoleggio() {
        return fineNoleggio;
    }

    public void setFineNoleggio(LocalDateTime fineNoleggio) {
        this.fineNoleggio = fineNoleggio;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public int getPrezzoFinale() {
        return prezzoFinale;
    }

    public void setPrezzoFinale(int prezzoFinale) {
        this.prezzoFinale = prezzoFinale;
    }

    public int getNoleggiabile() {
        return noleggiabile;
    }

    public void setNoleggiabile(int noleggiabile) {
        this.noleggiabile = noleggiabile;
    }

    public int getManutenzione() {
        return manutenzione;
    }

    public void setManutenzione(int manutenzione) {
        this.manutenzione = manutenzione;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }


    public String getStatus(){
        return status;
    }

    public void setStatus(String status1) {

        this.status = status1;
    }
}
