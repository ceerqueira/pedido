ALTER TABLE pedido
ADD CONSTRAINT fk_produto_id FOREIGN KEY (id_produto) REFERENCES produto(id);
