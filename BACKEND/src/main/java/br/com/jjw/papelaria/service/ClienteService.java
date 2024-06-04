package br.com.jjw.papelaria.service;

import br.com.jjw.papelaria.model.Cliente;
import br.com.jjw.papelaria.model.dto.ClienteDTO;

public interface ClienteService {
    public Cliente cadastrar(ClienteDTO cliente);

    public Cliente buscarPorCodigo(int id);
}
