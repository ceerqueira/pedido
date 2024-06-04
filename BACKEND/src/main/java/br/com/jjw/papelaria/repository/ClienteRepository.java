package br.com.jjw.papelaria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.jjw.papelaria.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,Long>{

    boolean existsByNumeroPedido(int id);

    @Query("SELECT e FROM Cliente e WHERE e.numeroPedido = :numeroPedido")
    Optional <Cliente> findByNumeroPedido(@Param("numeroPedido") int numeroPedido);

    
}
