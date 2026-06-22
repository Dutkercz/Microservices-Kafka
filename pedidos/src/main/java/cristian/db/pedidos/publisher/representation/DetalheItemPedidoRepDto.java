package cristian.db.pedidos.publisher.representation;

import java.math.BigDecimal;

public record DetalheItemPedidoRepDto(
        Long codigoProduto,
        String nome,
        Integer quantidade,
        BigDecimal valorUnitario
) {
    public BigDecimal getTotal(){
        return valorUnitario.multiply(new BigDecimal(quantidade));
    }
}
