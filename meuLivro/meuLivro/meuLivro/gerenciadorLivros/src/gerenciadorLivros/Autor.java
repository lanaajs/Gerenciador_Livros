package gerenciadorLivros;

public class Autor {

	//atributos
	private int id;
	private String nome;

	//construtores com e sem ID porque do banco de dados (SERIAL)
	public Autor(String nome) {
		this.nome = nome;
	}

	public Autor(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	//get e set
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

}
