package gerenciadorLivros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutorDB {

	// metodo para inserir autor
	public void inserirAutor(Autor autor) throws ClassNotFoundException, SQLException {

		// SQL para inserir um novo registro de autor com o retorno do ID gerado
		String sql = "INSERT INTO autor (nome_autor) VALUES (?) RETURNING id_autor";

		// garantir que a conexão e o PreparedStatement sejam fechados automaticamente
		try (Connection conexao = Conexao.criarConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) {

			//preenchendo o parâmetro do sql com os dados do autor
			statement.setString(1, autor.getNome());
			
			// executando a query e obtendo o ID gerado
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				int generatedId = resultSet.getInt("id_autor");
				autor.setId(generatedId); // atribuir o ID gerado ao objeto Autor
			}
		}
	}

	// deletar um autor pelo ID
	public void deletarAutor(int idAutor) throws ClassNotFoundException, SQLException {
		String sql = "DELETE FROM autor WHERE id_autor = ?";

		try (Connection conexao = Conexao.criarConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) {
			statement.setInt(1, idAutor); //definindo o ID do livro a ser deletado
			statement.executeUpdate(); //executa o delete
		}
	}

	// consultar autor por ID
	public Autor consultarAutor(int idAutor) throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM autor WHERE id_autor = ?";

		try (Connection conexao = Conexao.criarConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) {
			statement.setInt(1, idAutor);
			
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return new Autor(resultSet.getInt("id_autor"), resultSet.getString("nome_autor")); //retorna o autor encontrado
			} else {
				return null; // retorna null se o autor não for encontrado
			}
		}
	}

	// atualizar as informações de um autor
	public void atualizarAutor(Autor autor) throws ClassNotFoundException, SQLException {
		String sql = "UPDATE autor SET nome_autor = ? WHERE id_autor = ?";

		try (Connection conexao = Conexao.criarConexao(); PreparedStatement statement = conexao.prepareStatement(sql)) {

			//preenchendo os parâmetros do sql com os novos dados do autor
			statement.setString(1, autor.getNome());
			statement.setInt(2, autor.getId()); //define o ID do autor a ser atualizado
			
			//executando a atualização
			statement.executeUpdate(); 
		}
	}

	//listar autores em ordem pelo ID usando o function
	public void listarAutores() throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM autores_ordem()";

		try (Connection conexao = Conexao.criarConexao();
				PreparedStatement statement = conexao.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {

			System.out.println("ID \t NOME DO AUTOR");
			System.out.println("-----------------------");

			while (resultSet.next()) {
				int idAutor = resultSet.getInt("id_autor");
				String nomeAutor = resultSet.getString("nome_autor");
				System.out.println(idAutor + "\t" + nomeAutor);
			}
		}
	}
}
