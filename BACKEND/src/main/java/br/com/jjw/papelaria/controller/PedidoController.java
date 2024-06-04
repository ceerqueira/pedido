package br.com.jjw.papelaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jjw.papelaria.model.Pedido;
import br.com.jjw.papelaria.model.dto.PedidoDTO;
import br.com.jjw.papelaria.service.PedidoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedido")
@CrossOrigin(origins = "http://localhost:3000")
public class PedidoController {

    @Autowired
    private PedidoService service;
    
    @PostMapping
    public ResponseEntity<String> salvar (@RequestBody @Valid PedidoDTO pedido){
        service.salvar(pedido);
        return ResponseEntity.ok().body("item adicionado com sucesso");
    }

    @GetMapping
    public ResponseEntity<List< Pedido>> listar(){
        List<Pedido> listaDeProdutos = service.listar();
        if(listaDeProdutos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(listaDeProdutos);
    }

    @GetMapping ("/listar")
    public ResponseEntity<List< Pedido>> listarPedidos(){
        List<Pedido> listaDeProdutos = service.listarPedidoConcluido();
        if(listaDeProdutos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(listaDeProdutos);
    }

    @GetMapping ("/{id}")
    public List<Pedido> buscarPorPedido( @PathVariable ("id")Long id){
        return service.buscarPorPedido(id);
    }

    @PutMapping
    public ResponseEntity<String> atualizar(@RequestBody PedidoDTO pedido){
        Pedido pedidoResponse = service.editar(pedido);
        return ResponseEntity.ok().body("Pedido com ID:" +pedidoResponse.getId()+" atualizado com sucesso");
    }

    @DeleteMapping ("/{id}")
    public void deleter( @PathVariable ("id")Long id){
        service.deleter(id);
    }
}
