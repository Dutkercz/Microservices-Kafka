package cristian.db.pedidos.dto;

public record CallbackPagamentoResponseDto(
        Long codigo,
        String chavePagamento,
        boolean status,
        String observacoes) {
}
