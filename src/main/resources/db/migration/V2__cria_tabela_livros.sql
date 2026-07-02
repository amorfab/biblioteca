CREATE TABLE livros (
                        id BIGSERIAL PRIMARY KEY,
                        titulo VARCHAR(255) NOT NULL,
                        isbn VARCHAR(255) UNIQUE,
                        ano_de_lancamento INTEGER,
                        preco DOUBLE PRECISION,
                        autor_id BIGINT REFERENCES autores(id)
);