package br.com.jjw.papelaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jjw.papelaria.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto,Long>{
    
}
