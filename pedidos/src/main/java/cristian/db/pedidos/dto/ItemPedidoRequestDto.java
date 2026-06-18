package cristian.db.pedidos.dto;

import java.math.BigDecimal;

public record ItemPedidoRequestDto(Long codigoProduto,
                                   Integer quantidade,
                                   BigDecimal valorUnitario) {
}
