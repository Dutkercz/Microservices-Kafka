package cristian.db.pedidos.publisher.representation;

import cristian.db.pedidos.model.enums.StatusPedido;

import java.math.BigDecimal;
import java.util.List;

public record DetalhePedidoRepDto(Long codigo,
                                  Long codigoCliente,
                                  String nome,
                                  String cpf,
                                  String logradouro,
                                  String numero,
                                  String bairro,
                                  String email,
                                  String telefone,
                                  String dataPedido,
                                  BigDecimal total,
                                  StatusPedido status,
                                  List<DetalheItemPedidoRepDto> itens) {
}
