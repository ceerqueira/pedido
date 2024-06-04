import React, { useState, useEffect } from 'react';
import './ListarProdutos.css';
import axios from 'axios';

function ListarProdutos() {
  const [produtos, setProdutos] = useState([]);
  const [produtoSelecionado, setProdutoSelecionado] = useState(null);
  const [quantidadeSelecionada, setQuantidadeSelecionada] = useState(1);
  const [mostrarModalQuantidade, setMostrarModalQuantidade] = useState(false);

  const buscarProdutos = async () => {
    try {
      const response = await axios.get('http://localhost:8080/produtos');
      setProdutos(response.data);
    } catch (error) {
      console.error('Erro ao buscar produtos:', error);
    }
  };

  useEffect(() => {
    buscarProdutos();
  }, []);

  const abrirModalQuantidade = (produto) => {
    setProdutoSelecionado(produto);
    setMostrarModalQuantidade(true);
  };

  const fecharModalQuantidade = () => {
    setProdutoSelecionado(null);
    setMostrarModalQuantidade(false);
  };

  const handleAddToCart = async () => {
    try {
      if (!produtoSelecionado) {
        console.error('Nenhum produto selecionado.');
        return;
      }

      const pedido = {
        idProduto: produtoSelecionado.id,
        quantidade: quantidadeSelecionada,
      };

      await axios.post('http://localhost:8080/pedido', pedido);

      setTimeout(() => {
        window.location.reload();
      }, 10);

      fecharModalQuantidade();
    } catch (error) {
      console.error('Erro ao adicionar ao carrinho:', error);
    }
  };

  return (
    <div>
      <h1>Lista de Produtos</h1>
      <ul>
        <div className='grid'>
        {produtos.map((produto) => (
          <li key={produto.id}>
            <h2>{produto.nome}</h2>
            <p>{produto.descricao}</p>
            <p>Fornecedor: {produto.nomeFornecedor}</p>
            <p>Preço: R$ {produto.preco.toFixed(2)}</p>
            <p>Quantidade em estoque: {produto.quantidade}</p>
            <button onClick={() => abrirModalQuantidade(produto)}>Adicionar ao Carrinho</button>
          </li>
        ))}
        </div>
      </ul>

      {mostrarModalQuantidade && (
        <div className="modal-quantidade">
          <h2>Escolha a quantidade desejada:</h2>
          <input
            type="number"
            value={quantidadeSelecionada}
            onChange={(e) => setQuantidadeSelecionada(parseInt(e.target.value))}
            min={1}
          />
          <div className="button-container">
            <button onClick={fecharModalQuantidade}>Cancelar</button>
            <button onClick={handleAddToCart}>Adicionar ao Carrinho</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default ListarProdutos;
