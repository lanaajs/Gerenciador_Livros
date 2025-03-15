package gerenciadorLivros;

public class Livro {

	//atributos
	private int id;
    private String nome;
    private int ano;
    private int numPag;
    private String sinopse;
    private int idAutor;

    //construtores com e sem ID porque do banco de dados (SERIAL)
    public Livro(String nome, int ano, int numPag, String sinopse, int idAutor) {
        this.nome = nome;
        this.ano = ano;
        this.numPag = numPag;
        this.sinopse = sinopse;
        this.idAutor = idAutor;
    }

    public Livro(int id, String nome, int ano, int numPag, String sinopse, int idAutor) {
        this.id = id;
        this.nome = nome;
        this.ano = ano;
        this.numPag = numPag;
        this.sinopse = sinopse;
        this.idAutor = idAutor;
    }

    //get e set
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

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getNumPag() {
		return numPag;
	}

	public void setNumPag(int numPag) {
		this.numPag = numPag;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public int getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(int idAutor) {
		this.idAutor = idAutor;
	}

}
