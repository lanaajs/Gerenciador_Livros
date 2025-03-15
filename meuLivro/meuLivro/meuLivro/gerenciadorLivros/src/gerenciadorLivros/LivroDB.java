package gerenciadorLivros;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroDB {

	//inserir um novo livro
	public void inserirLivro(Livro livro) throws ClassNotFoundException, SQLException {

		// sql para inserir um novo registro de livro com o retorno do ID gerado
		String sql = "INSERT INTO livro (nome_livro, ano_publicacao, num_pag, sinopse, id_autor) VALUES (?, ?, ?, ?, ?) RETURNING id_livro";

		// garantir que a conexão e o PreparedStatement sejam fechados automaticamente
		try (Connection conexao = Conexao.criarConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) {

			//preenchendo os parâmetros do sql com os dados do livro
			statement.setString(1, livro.getNome());
			statement.setInt(2, livro.getAno());
			statement.setInt(3, livro.getNumPag());
			statement.setString(4, livro.getSinopse());
			statement.setInt(5, livro.getIdAutor());

			//executando a query e obtendo o ID gerado
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				int generatedId = resultSet.getInt("id_livro");
				livro.setId(generatedId); //atribuir o ID gerado ao objeto Livro
			}
		}
	}

	//deletar um livro pelo ID
	public void deletarLivro(int idLivro) throws ClassNotFoundException, SQLException {
		String sql = "DELETE FROM livro WHERE id_livro = ?";

		try (Connection conexao = Conexao.criarConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) {
			statement.setInt(1, idLivro); //definindo o ID do livro a ser deletado
			statement.executeUpdate(); //executando o comando de exclusão
		}
	}

	//consultar livro pelo ID
	public Livro consultarLivro(int idLivro) throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM livro WHERE id_livro = ?";

		try (Connection conexao = Conexao.criarConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) {
			statement.setInt(1, idLivro);
			
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				Livro livro = new Livro(resultSet.getInt("id_livro"), resultSet.getString("nome_livro"),
						resultSet.getInt("ano_publicacao"), resultSet.getInt("num_pag"), 
						resultSet.getString("sinopse"),resultSet.getInt("id_autor"));
				
				return livro; //retorna o livro encontrado
			} else {
				return null; //retorna null se o livro não for encontrado
			}
		}
	}

	//atualizar as informações de um livro 
	public void atualizarLivro(Livro livro) throws ClassNotFoundException, SQLException {
		String sql = "UPDATE livro SET nome_livro = ?, ano_publicacao = ?, num_pag = ?, sinopse = ?, id_autor = ? WHERE id_livro = ?";

		try (Connection conexao = Conexao.criarConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) {

			//preenchendo os parâmetros do sql com os novos dados do livro
			statement.setString(1, livro.getNome());
			statement.setInt(2, livro.getAno());
			statement.setInt(3, livro.getNumPag());
			statement.setString(4, livro.getSinopse());
			statement.setInt(5, livro.getIdAutor());
			statement.setInt(6, livro.getId()); //define o ID do livro a ser atualizado

			//executando a atualização
			statement.executeUpdate();
		}
	}

	//listar livros e seus respectivos autores usando o view
	public List<String> consultarLivrosComAutores() throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM view_livros_autores";
		List<String> livrosComAutores = new ArrayList<>();

		try (Connection conexao = Conexao.criarConexao();
				PreparedStatement statement = conexao.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {

			System.out.println("TITULO\t NOME DO AUTOR");
			System.out.println("-----------------------");
			
			while (resultSet.next()) {
				String livro = resultSet.getString("nome_livro");
				String autor = resultSet.getString("nome_autor");
				livrosComAutores.add(livro + " - " + autor);
			}
		}
		
		return livrosComAutores;
	}

	//listar a quantidade de livros usando o function
	public int contarLivros() throws ClassNotFoundException, SQLException {
		String sql = "{ ? = CALL total_livro() }";

		try (Connection conexao = Conexao.criarConexao(); 
				CallableStatement callableStatement = conexao.prepareCall(sql)) {

			callableStatement.registerOutParameter(1, java.sql.Types.INTEGER); //registrar o tipo de retorno
			callableStatement.execute(); //executando
			return callableStatement.getInt(1); //retorna o valor da contagem
		}
	}

	//listar livros em ordem pelo ID usando o function
	public void listarLivros() throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM livros_ordem()";

		try (Connection conexao = Conexao.criarConexao();
				PreparedStatement statement = conexao.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {

			System.out.println("ID \t NOME DO LIVRO");
			System.out.println("-----------------------");

			while (resultSet.next()) {
				int idLivro = resultSet.getInt("id_livro");
				String nomeLivro = resultSet.getString("nome_livro");
				System.out.println(idLivro + "\t" + nomeLivro);
			}
		}
	}
}
