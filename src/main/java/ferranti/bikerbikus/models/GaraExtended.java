package ferranti.bikerbikus.models;

import javafx.scene.control.Button;

public class GaraExtended extends Gara {

	private int partecipanti;
	private String nomeVincitore;
	private String cognomeVincitore;
	private Button button;


	public GaraExtended() {
		
	}

	public GaraExtended(int partecipanti, String vincitore) {
		super();
		this.partecipanti = partecipanti;
		this.nomeVincitore = vincitore;
	}

	public int getPartecipanti() {
		return partecipanti;
	}

	public void setPartecipanti(int partecipanti) {
		this.partecipanti = partecipanti;
	}

	public String getNomeVincitore() {
		return nomeVincitore;
	}

	public void setNomeVincitore(String nomeVincitore) {
		this.nomeVincitore = nomeVincitore;
	}
	
	public String getCognomeVincitore() {
		return cognomeVincitore;
	}

	public void setCognomeVincitore(String cognomeVincitore) {
		this.cognomeVincitore = cognomeVincitore;
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}
}
