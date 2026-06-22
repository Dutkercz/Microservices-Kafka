package cristian.db.pedidos.dto;

import cristian.db.pedidos.model.DadosPagamento;

public record NovoPagamentoRequestDto(Long codigoPedido, DadosPagamento dadosPagamento) {
}
