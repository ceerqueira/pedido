package br.com.jjw.papelaria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.jjw.papelaria.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT p FROM Pedido p WHERE p.numeroPedido = 0")
    List<Pedido> findByNumeroPedidoIsNull();

    @Query("SELECT COUNT(p) FROM Pedido p")
    long countPedidos();

    Optional<List<Pedido>> findByNumeroPedido(Long numeroPedido);

    @Query("SELECT p FROM Pedido p WHERE p.status = 'Concluido'")
    Optional<List<Pedido>> findByStatus();
}
