package cristian.db.produtos.dto;

import cristian.db.produtos.model.Produto;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public record ProdutoResponseDto(
        Long codigo,
        String nome,
        BigDecimal valorUnitario
) {
    public ProdutoResponseDto(Produto produto) {
        this(produto.getCodigo(), produto.getNome(), produto.getValorUnitario());
    }
}
