package cristian.db.pedidos.client;

import cristian.db.pedidos.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j // libera uma instacia de log
public class ServiceBancario {

    public String solicitarPagamento(Pedido pedido){
        log.info("Solicitando pagamento para o pedido codigo -> {}", pedido.getCodigo());
        return UUID.randomUUID().toString();
    }
}
