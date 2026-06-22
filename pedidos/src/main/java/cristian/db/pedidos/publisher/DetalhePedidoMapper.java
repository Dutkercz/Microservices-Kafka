package cristian.db.pedidos.publisher;

import cristian.db.pedidos.model.ItemPedido;
import cristian.db.pedidos.model.Pedido;
import cristian.db.pedidos.publisher.representation.DetalheItemPedidoRepDto;
import cristian.db.pedidos.publisher.representation.DetalhePedidoRepDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetalhePedidoMapper {

    @Mapping(target = "nome", source = "cliente.nome" )
    @Mapping(target = "cpf", source = "cliente.cpf" )
    @Mapping(target = "logradouro", source = "cliente.logradouro" )
    @Mapping(target = "numero", source = "cliente.numero" )
    @Mapping(target = "bairro", source = "cliente.bairro" )
    @Mapping(target = "email", source = "cliente.email" )
    @Mapping(target = "telefone", source = "cliente.telefone" )
    @Mapping(target = "dataPedido", source = "dataPedido", dateFormat = "dd/MM/yyyy")
    DetalhePedidoRepDto toDetalheDto(Pedido pedido);

    List<DetalheItemPedidoRepDto> toDetalheItemPedidoRepDto(List<ItemPedido> itens);
}
