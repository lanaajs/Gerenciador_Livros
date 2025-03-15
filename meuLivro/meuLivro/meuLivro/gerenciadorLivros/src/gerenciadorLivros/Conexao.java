package gerenciadorLivros;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	static final String URL = "jdbc:postgresql://localhost:5432/MeuLivro";
	static final String USER = "postgres";
	static final String PASS = "senha";

	public static Connection criarConexao() throws ClassNotFoundException, SQLException {
		
		// Carregar o driver JDBC do PostgreSQL
		Class.forName("org.postgresql.Driver");

		// Estabelecer a conexão com o banco de dados
		Connection conecta = DriverManager.getConnection(URL, USER, PASS);

		if (conecta != null) {
			System.out.println("Conexão efetuada com sucesso...");
			return conecta;
		}

		return null;
	}
}
