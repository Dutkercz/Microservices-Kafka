package cristian.db.produtos.model;

import cristian.db.produtos.dto.ProdutoResquestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal valorUnitario;


    public Produto(ProdutoResquestDto produto) {
        this.nome = produto.nome();
        this.valorUnitario = produto.valorUnitario();
    }
}
