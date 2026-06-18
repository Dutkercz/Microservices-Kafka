package cristian.db.produtos.dto;

import java.math.BigDecimal;

public record ProdutoResquestDto(
        String nome,
        BigDecimal valorUnitario
) {
}
