package gerenciadorLivros;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Gerenciador {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		// instâncias
		Scanner scanner = new Scanner(System.in);
		AutorDB autorDB = new AutorDB();
		LivroDB livroDB = new LivroDB();

		while (true) {

			System.out.println("\n╔══════════════════════════════════════════════════════════╗");
			System.out.println("║              MEU LIVRO - GERENCIADOR DE LIVRO            ║");
			System.out.println("╠══════════════════════ INSERIR ═══════════════════════════╣");
			System.out.println("║ 1. INSERIR AUTOR                                         ║");
			System.out.println("║ 2. INSERIR LIVRO                                         ║");
			System.out.println("╠═════════════════════ CONSULTAR ══════════════════════════╣");
			System.out.println("║ 3. CONSULTAR AUTOR                                       ║");
			System.out.println("║ 4. CONSULTAR LIVRO                                       ║");
			System.out.println("║ 5. CONSULTAR LIVRO E AUTORES                             ║");
			System.out.println("╠══════════════════════ DELETAR ═══════════════════════════╣");
			System.out.println("║ 6. DELETAR AUTOR                                         ║");
			System.out.println("║ 7. DELETAR LIVRO                                         ║");
			System.out.println("╠═════════════════ EDITAR / ATUALIZAR ═════════════════════╣");
			System.out.println("║ 8. EDITAR AUTOR                                          ║");
			System.out.println("║ 9. EDITAR LIVRO                                          ║");
			System.out.println("╠══════════════════ OUTRAS OPÇÕES ═════════════════════════╣");
			System.out.println("║ 10. QUANTIDADE DE LIVROS                                 ║");
			System.out.println("║ 11. LISTAR AUTORES PELO ID                               ║");
			System.out.println("║ 12. LISTAR LIVROS PELO ID                                ║");
			System.out.println("║ 13. SAIR DO SISTEMA                                      ║");
			System.out.println("╚══════════════════════════════════════════════════════════╝");
			System.out.print("\nESCOLHA UMA OPÇÃO ACIMA: ");

			int escolha = scanner.nextInt();

			switch (escolha) {

			// inserir um novo autor
			case 1:
				scanner.nextLine(); // limpar o buff
				System.out.print("NOME DO AUTOR: ");
				String nomeAutor = scanner.nextLine();

				Autor autor = new Autor(nomeAutor);
				autorDB.inserirAutor(autor);

				// verifica se autor foi inserido
				Autor autorInserido = autorDB.consultarAutor(autor.getId()); // ID gerado pelo db

				if (autorInserido != null) {
					System.out.println("AUTOR INSERIDO: " + autorInserido.getNome());
				} else {
					System.out.println("ERRO AO INSERIR AUTOR!");
				}
				break;

			// inserir um novo livro
			case 2:
				scanner.nextLine();
				System.out.print("NOME DO LIVRO: ");
				String nomeLivro = scanner.nextLine();

				System.out.print("ANO DE PUBLICAÇÃO: ");
				int ano = scanner.nextInt();

				System.out.print("NÚMERO DE PÁGINAS: ");
				int numPag = scanner.nextInt();
				scanner.nextLine();

				System.out.print("SINOPSE: ");
				String sinopse = scanner.nextLine();

				System.out.print("ID DO AUTOR: ");
				int idAutor = scanner.nextInt();

				Livro livro = new Livro(nomeLivro, ano, numPag, sinopse, idAutor);
				livroDB.inserirLivro(livro);

				// verifica se o livro foi inserido
				Livro livroInserido = livroDB.consultarLivro(livro.getId());

				if (livroInserido != null) {
					System.out.println("LIVRO INSERIDO: " + livroInserido.getNome());
				} else {
					System.out.println("ERRO AO INSERIR LIVRO!");
				}
				break;

			// consultar um autor pelo seu ID
			case 3:
				System.out.print("INSIRA O ID DO AUTOR: ");

				int idAutorConsulta = scanner.nextInt();
				Autor autorConsulta = autorDB.consultarAutor(idAutorConsulta);

				if (autorConsulta != null) {
					System.out.println(autorConsulta.getNome());
				} else {
					System.out.println("AUTOR NÃO ENCONTRADO.");
				}
				break;

			// consultar um livro pelo seu ID
			case 4:
				System.out.print("INSIRA O ID DO LIVRO: ");

				int idLivroConsulta = scanner.nextInt();
				Livro livroConsulta = livroDB.consultarLivro(idLivroConsulta);

				if (livroConsulta != null) {
					System.out.println(livroConsulta.getNome());
				} else {
					System.out.println("LIVRO NÃO ENCONTRADO.");
				}
				break;

			// listar os autores e seus livros
			case 5:
				try {
					List<String> livrosComAutores = livroDB.consultarLivrosComAutores();

					for (String livroComAutor : livrosComAutores) {
						System.out.println(livroComAutor);
					}

				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;

			// deletar um autor pelo ID
			case 6:
				System.out.print("INSIRA O ID DO AUTOR PARA REMOVER: ");

				int idAutorDeletar = scanner.nextInt();
				autorDB.deletarAutor(idAutorDeletar);

				System.out.println("AUTOR DELETADO.");
				break;

			// deletar um livro pelo ID
			case 7:
				System.out.print("INSIRA O ID DO LIVRO PARA REMOVER: ");

				int idLivroDeletar = scanner.nextInt();
				livroDB.deletarLivro(idLivroDeletar);

				System.out.println("LIVRO DELETADO.");
				break;

			// Atualizar autor
			case 8:
				System.out.print("INSIRA O ID DO AUTOR PARA EDITAR: ");
				int idAutorAtualizar = scanner.nextInt();
				scanner.nextLine();

				System.out.print("NOME DO AUTOR: ");
				String novoNomeAutor = scanner.nextLine();
				Autor autorAtualizar = new Autor(idAutorAtualizar, novoNomeAutor);
				autorDB.atualizarAutor(autorAtualizar);
				System.out.println("Autor atualizado com sucesso.");
				break;

			// atualizar um livro pelo ID
			case 9:
				System.out.print("INSIRA O ID DO AUTOR PARA EDITAR: ");
				int idLivroAtualizar = scanner.nextInt();
				scanner.nextLine();

				System.out.print("\n -NOME DO LIVRO: ");
				String novoNomeLivro = scanner.nextLine();

				System.out.print(" -ANO DE PUBLICAÇÃO: ");
				int novoAno = scanner.nextInt();

				System.out.print(" -NÚMERO DE PÁGINAS: ");
				int novoNumPag = scanner.nextInt();
				scanner.nextLine();

				System.out.print(" -SINOPSE: ");
				String novaSinopse = scanner.nextLine();
				scanner.nextLine();

				System.out.print(" -ID DO AUTOR: ");
				int novoIdAutor = scanner.nextInt();

				Livro livroAtualizar = new Livro(idLivroAtualizar, novoNomeLivro, novoAno, novoNumPag, novaSinopse,
						novoIdAutor);
				livroDB.atualizarLivro(livroAtualizar);
				System.out.println("LIVRO ATUALIZADO.");
				break;

			// listar a quantidade de livros cadastrados
			case 10:
				try {
					int totalLivros = livroDB.contarLivros();

					System.out.println("TOTAL DE LIVROS NO SISTEMA: " + totalLivros);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;

			// listar os autores em ordem crescente pelo seu ID
			case 11:
				try {
					autorDB.listarAutores();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;

			// listar os livros em ordem crescente pelo seu ID
			case 12:
				try {
					livroDB.listarLivros();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				break;

			// sair do loop (sistema)
			case 13:
				System.out.println("FECHANDO...");
				scanner.close();
				return;

			// caso escollha um numero diferente do menu
			default:
				System.out.println("TENTE NOVAMENTE!!");
				break;
			}
		}
	}
}
