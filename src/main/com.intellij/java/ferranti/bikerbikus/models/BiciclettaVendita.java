package ferranti.bikerbikus.models;

import java.time.LocalDateTime;

public class BiciclettaVendita extends Bicicletta{

    private int idAcquisto;
    private int idUtente;
    private LocalDateTime dataAcquisto;
    private int prezzo;

    private int disponibili;


    public BiciclettaVendita() {
    }

    public BiciclettaVendita(int idAcquisto, String modello, String caratteristiche, int prezzo) {
        this.idAcquisto = idAcquisto;
        this.prezzo = prezzo;
    }

    public int getIdAcquisto() {
        return idAcquisto;
    }

    public void setIdAcquisto(int idAcquisto) {
        this.idAcquisto = idAcquisto;
    }


    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public LocalDateTime getDataAcquisto() {
        return dataAcquisto;
    }

    public void setDataAcquisto(LocalDateTime dataAcquisto) {
        this.dataAcquisto = dataAcquisto;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public int getDisponibili() {
        return disponibili;
    }

    public void setDisponibili(int disponibili) {
        this.disponibili = disponibili;
    }

}
