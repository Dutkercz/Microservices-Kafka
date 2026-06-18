package cristian.db.pedidos.mapper;

import cristian.db.pedidos.dto.ItemPedidoRequestDto;
import cristian.db.pedidos.dto.PedidoRequestDto;
import cristian.db.pedidos.dto.PedidoResponseDto;
import cristian.db.pedidos.model.DadosPagamento;
import cristian.db.pedidos.model.ItemPedido;
import cristian.db.pedidos.model.Pedido;
import cristian.db.pedidos.model.enums.StatusPedido;
import org.mapstruct.*;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface PedidoMapper {


    Pedido toEntity(PedidoRequestDto pedidoRequestDto);

    PedidoResponseDto toResponseDto(Pedido pedido);

    ItemPedido toItemPedido(ItemPedidoRequestDto itemPedidoRequestDto);

    @AfterMapping
    default void afterMapping(@MappingTarget Pedido pedido){
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setTotal(calcularTotal(pedido));

        for (ItemPedido item :  pedido.getItens()) {
            item.setPedido(pedido);
        }
    }

    @NonNull
    private static BigDecimal calcularTotal(Pedido pedido) {
        var total = pedido.getItens().stream().map(x -> (x.getValorUnitario().multiply(
                BigDecimal.valueOf(x.getQuantidade()))))
                          .reduce(BigDecimal.ZERO, BigDecimal::add ).abs();
        return total;
    }
}
