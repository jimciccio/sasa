package ferranti.bikerbikus.models;

import java.time.LocalDateTime;

public class Escursione {

	private int id;
	private LocalDateTime data;
	private Luoghi luogo;
	private Utente accompagnatore;
	
	public Escursione() {}

	public Escursione(int id, LocalDateTime data, Luoghi luogo, Utente accompagnatore) {
		super();
		this.id = id;
		this.data = data;
		this.luogo = luogo;
		this.accompagnatore = accompagnatore;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Luoghi getLuogo() {
		return luogo;
	}

	public void setLuogo(Luoghi luogo) {
		this.luogo = luogo;
	}

	public Utente getAccompagnatore() {
		return accompagnatore;
	}

	public void setAccompagnatore(Utente accompagnatore) {
		this.accompagnatore = accompagnatore;
	}
}