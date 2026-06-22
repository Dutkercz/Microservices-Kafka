package cristian.db.pedidos.service.validador;

import cristian.db.pedidos.client.ClientesClient;
import cristian.db.pedidos.client.ProdutosClient;
import cristian.db.pedidos.client.representation.ClienteRepresentantion;
import cristian.db.pedidos.client.representation.ProdutoRepresentation;
import cristian.db.pedidos.dto.CallbackPagamentoResponseDto;
import cristian.db.pedidos.exception.ValidationException;
import cristian.db.pedidos.model.ItemPedido;
import cristian.db.pedidos.model.Pedido;
import cristian.db.pedidos.model.enums.StatusPedido;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Validator {

    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validar(Pedido pedido) {
        validarCliente(pedido.getCodigoCliente());
        pedido.getItens().forEach(this::validarItem);
    }

    private void validarCliente(Long codigoCliente) {
        try {
            ResponseEntity<ClienteRepresentantion> response = clientesClient.details(codigoCliente);
            if (response.getBody() != null) {
                log.info("Cliente: {}", response.getBody().codigo());
            }
        } catch (FeignException.NotFound e) {
            throw new ValidationException("Cliente de codigo " + codigoCliente + " não encontrado");
        }
    }


    private void validarItem(ItemPedido itemPedido) {
        try {
            ResponseEntity<ProdutoRepresentation> response = produtosClient.details(itemPedido.getCodigoProduto());
            ProdutoRepresentation produto = response.getBody();
            log.info("Item pedido do produto: {}", produto.codigo());
        } catch (FeignException.NotFound e) {
            throw new ValidationException("Item de codigo " + itemPedido.getCodigoProduto() + " não encontrado");
        }
    }

    public void validarSucessoPagamentoESetNovoStatus(CallbackPagamentoResponseDto requestDto, Pedido pedido) {
        boolean sucesso = requestDto.status();
        if (sucesso) {
            pedido.setObservacoes(requestDto.observacoes());
            pedido.setStatus(StatusPedido.PAGO);
        }
        else {
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(requestDto.observacoes());
        }
    }
}
