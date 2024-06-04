package br.com.jjw.papelaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.com.jjw.papelaria.model.Produto;
import br.com.jjw.papelaria.repository.ProdutoRepository;


@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "http://localhost:3000")
public class ProdutoControlle {

    @Autowired
    private ProdutoRepository repository;

    @GetMapping
    public ResponseEntity<List< Produto>> listar(){

        List<Produto> listaDeProdutos = repository.findAll();

        return ResponseEntity.ok().body(listaDeProdutos);

    }
    
}
