package cristian.db.pedidos.dto;

import java.util.List;

public record PedidoRequestDto(Long codigoCliente, DadosPagamentoDto dadosPagamentoDto,
                               List<ItemPedidoRequestDto> itens) {
}
