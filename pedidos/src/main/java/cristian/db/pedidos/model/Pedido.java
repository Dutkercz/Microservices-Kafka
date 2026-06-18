package cristian.db.pedidos.model;

import cristian.db.pedidos.model.enums.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(nullable = false)
    private Long codigoCliente;

    @Column
    private LocalDateTime dataPedido;

    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal total;

    private String chavePagamento;

    private String observacoes;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    private String codigoRastreio;

    private String urlNf;

    @Transient //não faz a persistência dos dados (indica ao JPA que não é uma coluna no banco)
    private DadosPagamento dadosPagamento;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;
}
