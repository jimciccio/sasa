package ferranti.bikerbikus.models;

public class TipoLezione {

	private int id;
	private String nome;

	public TipoLezione() {
	}

	public TipoLezione(int id, String nome) {
		this.id = id;
		this.nome = nome;
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

	@Override
	public String toString() {
		return nome;
	}
}