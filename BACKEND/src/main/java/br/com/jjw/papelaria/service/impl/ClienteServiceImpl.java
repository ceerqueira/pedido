package br.com.jjw.papelaria.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.jjw.papelaria.exception.PapelariaException;
import br.com.jjw.papelaria.model.Cliente;
import br.com.jjw.papelaria.model.Pedido;
import br.com.jjw.papelaria.model.dto.ClienteDTO;
import br.com.jjw.papelaria.repository.ClienteRepository;
import br.com.jjw.papelaria.repository.PedidoRepository;
import br.com.jjw.papelaria.service.ClienteService;
import br.com.jjw.papelaria.util.ConstantesUtils;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public Cliente cadastrar(ClienteDTO cliente) {
        validarEntrega(cliente);

        gerarNumeroPedido(cliente);

        Cliente itemNovo = Cliente.builder()
                .endereco(cliente.getEndereco())
                .telefone(cliente.getTelefone())
                .numeroPedido(cliente.getNumeroPedido())
                .nome(cliente.getNome())
                .build();

        List<Pedido> pedidos = pedidoRepository.findByNumeroPedidoIsNull();
        for (Pedido item : pedidos) {
            item.setStatus(ConstantesUtils.CONCLUIDO);
            item.setNumeroPedido(cliente.getNumeroPedido());
        }

        return repository.save(itemNovo);
    }

    private void validarEntrega(ClienteDTO entrega) {
        if (entrega.getEndereco() == null) {
            throw new PapelariaException("Adicione um endereço");
        }
        if (entrega.getNome() == null) {
            throw new PapelariaException("Adicione um nome");
        }
        if (entrega.getTelefone() == null) {
            throw new PapelariaException("Adicione um telefone");
        }
    }

    private void gerarNumeroPedido(ClienteDTO entrega) {

        Random random = new Random();
        int verificaSeJaExiste = random.nextInt(10_000);
        while (repository.existsByNumeroPedido(verificaSeJaExiste)) {
            verificaSeJaExiste = random.nextInt(10_000);
        }
        int numero_pedido = random.nextInt(10_000); // gera um numero randomico de 4 digitos
        entrega.setNumeroPedido(numero_pedido);

    }

    @Override
    public Cliente buscarPorCodigo(int id) {
        return repository.findByNumeroPedido(id).orElseThrow( () -> new PapelariaException("Id não encontrado"));
    }

}
