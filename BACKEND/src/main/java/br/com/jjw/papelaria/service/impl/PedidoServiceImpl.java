package br.com.jjw.papelaria.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.jjw.papelaria.exception.PapelariaException;
import br.com.jjw.papelaria.model.Pedido;
import br.com.jjw.papelaria.model.Produto;
import br.com.jjw.papelaria.model.dto.PedidoDTO;
import br.com.jjw.papelaria.repository.PedidoRepository;
import br.com.jjw.papelaria.repository.ProdutoRepository;
import br.com.jjw.papelaria.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public Pedido salvar(PedidoDTO pedido) {

        Produto produto = produtoRepository.getReferenceById(pedido.getIdProduto());

        validarEntregaSalvar(pedido, produto);

        Pedido item = Pedido.builder()
        .produto(produto)
        .quantidade(pedido.getQuantidade())
        .valorTotal(pedido.getQuantidade()*produto.getPreco())
        .build();

        produto.setQuantidade(produto.getQuantidade() - pedido.getQuantidade());

        return repository.save(item);

    }

    private void validarEntregaSalvar(PedidoDTO pedido, Produto produto) {
        if (pedido.getIdProduto() == null) {
            throw new PapelariaException("Adicione o ID do produto");
        }

        if (pedido.getQuantidade() <= 0) {
            throw new PapelariaException("Adicione uma quantidade superior a 0");
        }
        if ((produto.getQuantidade() - pedido.getQuantidade()) < 0) {
            throw new PapelariaException("Quantidade insuficiente no estoque");
        }

    }

    @Override
    public Pedido editar(PedidoDTO pedidoDTO) {

        Pedido pedido = repository.getReferenceById(pedidoDTO.getIdPedido());

        validarEntregaEditar(pedidoDTO, pedido);

        if (pedidoDTO.getIdProduto() == null) {

            int novaQuantidade = pedido.getProduto().getQuantidade() + pedido.getQuantidade() - pedidoDTO.getQuantidade();
            pedido.getProduto().setQuantidade(novaQuantidade);
            pedido.setQuantidade(pedidoDTO.getQuantidade());

        }else {

            int novaQuantidadeProdutoAntigo = pedido.getProduto().getQuantidade() + pedido.getQuantidade();
            pedido.getProduto().setQuantidade(novaQuantidadeProdutoAntigo);

            Produto item = produtoRepository.getReferenceById(pedidoDTO.getIdProduto());
            pedido.setQuantidade(pedidoDTO.getQuantidade());
            pedido.setProduto(item);

            int novaQuantidadeProdutoNovo = item.getQuantidade() - pedidoDTO.getQuantidade();
            item.setQuantidade(novaQuantidadeProdutoNovo);
        }
        double valorTotal = pedido.getQuantidade()*pedido.getProduto().getPreco();
        pedido.setValorTotal(valorTotal);

        pedido = repository.save(pedido);
        return pedido;

    }
    private void validarEntregaEditar(PedidoDTO pedidoDTO, Pedido pedido) {
        if ((pedido.getProduto().getQuantidade() + pedido.getQuantidade() - pedidoDTO.getQuantidade()) < 0) {
            throw new PapelariaException("Quantidade insuficiente no estoque");
        }
        if ( pedidoDTO.getQuantidade() <= 0) {
            throw new PapelariaException("Adicione a uma quantiade maior que 0 para editar");
        }

    }

    @Override
    public void deleter(Long id) {

        Pedido pedido = repository.findById(id).orElseThrow(() -> new PapelariaException("Erro ao encontrar o ID"));

        List<Pedido> quantidade = repository.findByNumeroPedido(0L).get();

        if (quantidade.size() == 1) {
            throw new PapelariaException("Você não pode deixar o carrinho de compras vazio");
        }

        Produto produto = produtoRepository.findById(pedido.getProduto().getId()).get();
        produto.setQuantidade(produto.getQuantidade() + pedido.getQuantidade());
        repository.deleteById(id);
    }

    @Override
    public List<Pedido> listar() {
        return repository.findByNumeroPedido(0L).get();
    }

    @Override
    public List<Pedido> buscarPorPedido(Long id) {

        return repository.findByNumeroPedido(id).orElseThrow(() -> new PapelariaException("Pedido não encontrado" ));
    }

    @Override
    public List<Pedido> listarPedidoConcluido() {
        return repository.findByStatus().orElseThrow(() -> new PapelariaException("Pedido não encontrado" ));

    }

}
