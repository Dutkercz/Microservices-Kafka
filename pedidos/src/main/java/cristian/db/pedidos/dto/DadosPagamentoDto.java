package cristian.db.pedidos.dto;

import cristian.db.pedidos.model.enums.TipoPagamento;

public record DadosPagamentoDto(String dados, TipoPagamento tipoPagamento) {
}
