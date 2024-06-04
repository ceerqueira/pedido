package br.com.jjw.papelaria.service;

import java.util.List;

import br.com.jjw.papelaria.model.Pedido;
import br.com.jjw.papelaria.model.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar(PedidoDTO pedido);
    Pedido editar(PedidoDTO pedido);
    void deleter(Long id);
    List<Pedido> listar ();
    List<Pedido> buscarPorPedido(Long id);
    List<Pedido> listarPedidoConcluido();
}
