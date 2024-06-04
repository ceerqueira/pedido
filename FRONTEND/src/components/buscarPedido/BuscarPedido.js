import React, { useState } from 'react';
import './index.css';

function BuscarPedido() {
  const [numeroPedido, setNumeroPedido] = useState('');
  const [pedido, setPedido] = useState([]);
  const [cliente, setCliente] = useState({});
  const [codigoAcesso, setCodigoAcesso] = useState('');
  const [totalPrice, setTotalPrice] = useState(0);

  const buscarPedido = async () => {
    try {
      let pedidoData = [];
      let clienteData = {};

      if (numeroPedido === '') {

        const pedidoResponse = await fetch(`http://localhost:8080/pedido/listar`);
        if (pedidoResponse.ok) {
          pedidoData = await pedidoResponse.json();
          calculateTotalPrice(pedidoData);
        } else {
          console.error('Erro ao buscar os pedidos.');
        }
      } else {
        const pedidoResponse = await fetch(`http://localhost:8080/pedido/${numeroPedido}`);
        if (pedidoResponse.ok) {
          pedidoData = await pedidoResponse.json();
          calculateTotalPrice(pedidoData);
        } else {
          console.error('Erro ao buscar o pedido.');
        }

        const codigoAcessoResponse = await fetch(`http://localhost:8080/cliente/${numeroPedido}`);
        if (codigoAcessoResponse.ok) {
          const codigoAcessoData = await codigoAcessoResponse.text();
          setCodigoAcesso(codigoAcessoData);
          clienteData = JSON.parse(codigoAcessoData);
        } else {
          console.error('Erro ao buscar o código de acesso.');
        }
      }

      setPedido(pedidoData);
      setCliente(clienteData);
    } catch (error) {
      console.error('Erro ao buscar o pedido ou código de acesso:', error);
    }
  };

  const calculateTotalPrice = (items) => {
    const total = items.reduce((acc, item) => acc + item.valorTotal, 0);
    setTotalPrice(total);
  };

  return (
    <div>
      <h1>Buscar Pedido</h1>
      <label>Digite o código de acesso: </label>
      <input
        type="text"
        value={numeroPedido}
        onChange={(e) => setNumeroPedido(e.target.value)}
      />
      <button onClick={buscarPedido}>Buscar</button>

      {codigoAcesso && (
        <div className='titulo'>
          <h2 >Dados do cliente</h2>
          <p>Nome: {cliente.nome}</p>
          <p>Telefone: {cliente.telefone}</p>
          <p>Endereco: {cliente.endereco}</p>
        </div>
      )}

      {pedido.length > 0 && (
        <div>
          <h2 className='titulo'>Itens do Pedido: </h2>
          <ul>
            {pedido.map((item) => (
              <li key={item.id}>
                <p>Nome: {item.produto.nome}</p>
                <p>Quantidade: {item.quantidade}</p>
                <p>Valor Total: R$ {item.valorTotal.toFixed(2)}</p>
              </li>
            ))}
          </ul>
          <div className='valorTotal'>
            <h2>Soma Total: R$ {totalPrice.toFixed(2)}</h2>
          </div>
        </div>
      )}
    </div>
  );
}

export default BuscarPedido;
