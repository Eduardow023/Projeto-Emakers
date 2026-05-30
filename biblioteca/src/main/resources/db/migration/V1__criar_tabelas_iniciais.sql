CREATE TABLE livro(
    id_livro BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    data_lancamento DATE
);
-- Flyway resolve assumindo o controle da evolução da estrutura do banco(schema)
CREATE TABLE pessoa(
    id_pessoa BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    cep VARCHAR(8) NOT NULL,
    endereco VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE emprestimo(
    id_pessoa BIGINT NOT NULL,
    id_livro BIGINT NOT NULL,
    PRIMARY KEY (id_pessoa, id_livro),
    CONSTRAINT fk_emprestimo_pessoa FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa) ON DELETE CASCADE,
    CONSTRAINT fk_emprestimo_livro FOREIGN KEY (id_livro) REFERENCES livro(id_livro) ON DELETE CASCADE
);