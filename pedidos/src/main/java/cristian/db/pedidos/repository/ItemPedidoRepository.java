package cristian.db.pedidos.repository;


import cristian.db.pedidos.model.ItemPedido;
import cristian.db.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedido(Pedido pedido);
}
