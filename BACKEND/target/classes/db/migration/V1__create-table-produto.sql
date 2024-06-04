CREATE TABLE produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    nome_fornecedor VARCHAR(100),
    descricao VARCHAR(255),
    preco DOUBLE,
    quantidade INT
);