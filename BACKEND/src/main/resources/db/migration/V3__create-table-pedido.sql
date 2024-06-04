CREATE TABLE pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_produto INT, 
    quantidade INT,
    numero_pedido INT,
    valor_total DOUBLE,
    status VARCHAR(50)
);