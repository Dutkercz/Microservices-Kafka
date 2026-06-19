package cristian.db.pedidos.repository;

import cristian.db.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query("""
        SELECT pe FROM Pedido pe
        WHERE pe.codigo = :pedidoCodigo
        AND pe.chavePagamento = :chavePagamento
        """)
    Optional<Pedido> findByCodigoAndChavePagamento(@Param("pedidoCodigo") Long pedidoCodigo,
                                                   @Param("chavePagamento") String chavePagamento);
}
