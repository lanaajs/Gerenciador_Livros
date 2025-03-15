--criando tabelas
CREATE TABLE autor (
    id_autor SERIAL PRIMARY KEY,
    nome_autor VARCHAR(200)
);

CREATE TABLE livro (
    id_livro SERIAL PRIMARY KEY,
    nome_livro VARCHAR(200),
    ano_publicacao INTEGER,
    num_pag INTEGER,
    sinopse TEXT,
    id_autor INTEGER,
    FOREIGN KEY (id_autor) REFERENCES autor(id_autor)
);

--join para consultar livros e autores
SELECT livro.nome_livro, autor.nome_autor 
FROM livro
JOIN autor ON livro.id_autor = autor.id_autor;

--view para simplificar consulta 
CREATE VIEW view_livros_autores AS
SELECT livro.nome_livro, autor.nome_autor
FROM livro
JOIN autor ON livro.id_autor = autor.id_autor;

--função para contar livros de um autor
CREATE FUNCTION total_livro() RETURNS INTEGER AS $$
DECLARE
    livro_total INTEGER;
BEGIN
    SELECT COUNT(*) INTO livro_total FROM livro;
    RETURN livro_total;
END;
$$ LANGUAGE plpgsql;

--função para ordenar os autores pelo ID
CREATE OR REPLACE FUNCTION autores_ordem()
	RETURNS TABLE(id_autor INT, nome_autor VARCHAR) AS $$
BEGIN
    RETURN QUERY
    SELECT autor.id_autor, autor.nome_autor
    FROM autor
    ORDER BY autor.id_autor;
END;
$$ LANGUAGE plpgsql;

--função para ordenar os livros pelo ID
CREATE OR REPLACE FUNCTION livros_ordem()
	RETURNS TABLE(id_livro INT, nome_livro VARCHAR) AS $$
BEGIN
    RETURN QUERY
    SELECT livro.id_livro, livro.nome_livro
    FROM livro
    ORDER BY livro.id_livro;
END;
$$ LANGUAGE plpgsql;

--selects para consultar
SELECT * FROM autor;
SELECT * FROM livro;
SELECT * FROM view_livros_autores;
SELECT total_livro();
SELECT * FROM autores_ordem();
SELECT * FROM livros_ordem();