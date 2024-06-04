import React, { useState, useEffect } from 'react';
import './Pedidos.css';

function Pedidos() {
  const [cartItems, setCartItems] = useState([]);
  const [totalPrice, setTotalPrice] = useState(0);

  const fetchCartItems = async () => {
    try {
      const response = await fetch('http://localhost:8080/pedido');
      if (response.ok) {
        const data = await response.json();
        setCartItems(data);
        calculateTotalPrice(data);
      }
    } catch (error) {
      console.log('Carrinho vazio');
    }
  };

  useEffect(() => {
    fetchCartItems();
  }, []);

  const calculateTotalPrice = (items) => {
    const total = items.reduce((acc, item) => acc + item.valorTotal, 0);
    setTotalPrice(total);
  };

  const removeFromCart = async (product) => {
    try {
      const response = await fetch(`http://localhost:8080/pedido/${product.id}`, {
        method: 'DELETE',
      });

      if (response.ok) {
        fetchCartItems();
      } else {
        console.error('Você não pode deixar o carrinho vazio');
      }
    } catch (error) {
      console.error('Erro ao remover item do carrinho:', error);
    }
  };

  const updateQuantity = async (product, newQuantity) => {
    try {
      const response = await fetch('http://localhost:8080/pedido', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ idPedido: product.id, quantidade: newQuantity }),
      });

      if (response.ok) {
        fetchCartItems();
      } else {
        console.error('Erro ao atualizar quantidade do item no carrinho.');
      }
    } catch (error) {
      console.error('Erro ao atualizar quantidade do item no carrinho:', error);
    }
  };

  const [codigoAcesso, setCodigoAcesso] = useState('');
  const finalizarPedido = async () => {
    try {

      if (cartItems.length === 0) {
        console.error('O carrinho está vazio. Adicione itens antes de finalizar o pedido.');
        return;
      }

      const nome = prompt('Digite seu nome:');
      const endereco = prompt('Digite seu endereço:');
      const telefone = prompt('Digite seu telefone:');

      if (!nome || !endereco || !telefone) {
        alert('Por favor, preencha todos os campos obrigatórios.');
        return;
      }

      const dadosPedido = {
        nome,
        endereco,
        telefone,
      };

      const response = await fetch('http://localhost:8080/cliente', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(dadosPedido),
      });

      const responseText = await response.text();
      const codigoAcessoMatch = responseText.match(/\d+/);

      if (codigoAcessoMatch) {
        const codigoAcesso = codigoAcessoMatch[0];
        setCodigoAcesso(codigoAcesso);
        alert('Pedido finalizado com sucesso! Seu código de acesso é: ' + codigoAcesso);
        setTimeout(() => {
          window.location.reload();
        }, 3000);
      } else {
        console.error('Formato de código de acesso inválido na resposta do servidor.');
      }
    } catch (error) {
      alert('Erro ao finalizar o pedido:', error);
    }
  };

  return (
    <div>
      <h1>Carrinho de Compras</h1>
      <ul>
        {cartItems.map((item) => (
          <li key={item.id}>
            <h2>{item.produto.nome}</h2>
            <p>Quantidade: {item.quantidade}</p>
            <p>Valor Total: R$ {item.valorTotal.toFixed(2)}</p>
            <button onClick={() => updateQuantity(item, item.quantidade + 1)}>+</button>
            <button onClick={() => updateQuantity(item, item.quantidade - 1)}>-</button>
            <button onClick={() => removeFromCart(item)}>Remover do Carrinho</button>
          </li>
        ))}
      </ul>
      <div className='valorTotal'>
        <h2>Soma Total: R$ {totalPrice.toFixed(2)}</h2>
      </div>
      {codigoAcesso && (
        <p className='titulo'>Seu código de acesso é: {codigoAcesso}</p>
      )}
      <button className="botao" onClick={finalizarPedido}>Finalizar Pedido</button>
    </div>
  );
}

export default Pedidos;
