package br.com.jjw.papelaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jjw.papelaria.model.Cliente;
import br.com.jjw.papelaria.model.dto.ClienteDTO;
import br.com.jjw.papelaria.service.ClienteService;

@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping
    public ResponseEntity<String >cadastrarEndereco(@RequestBody ClienteDTO entrega){
        Cliente endereco = service.cadastrar(entrega);
        return ResponseEntity.ok().body("Pedido cadastrado com sucesso\nSeu codigo de acesso é "+endereco.getNumeroPedido());

    }

    @GetMapping ("/{id}")
    public ResponseEntity<Cliente> listarPorId ( @PathVariable ("id")int id){
        Cliente endereco = service.buscarPorCodigo(id);
        return ResponseEntity.ok().body(endereco);

    }
    
}
