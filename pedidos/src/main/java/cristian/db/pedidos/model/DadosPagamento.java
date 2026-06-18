package cristian.db.pedidos.model;

import cristian.db.pedidos.model.enums.TipoPagamento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DadosPagamento {

    private String dados;
    private TipoPagamento tipoPagamento;

}