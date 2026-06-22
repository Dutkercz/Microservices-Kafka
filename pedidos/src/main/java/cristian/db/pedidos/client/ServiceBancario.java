package cristian.db.pedidos.client;

import cristian.db.pedidos.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

///Essa classe representa a comunicação com o banco, onde seria validado o pagamento
@Component
@Slf4j // libera uma instacia de log
public class ServiceBancario {

    private String solicitarPagamento(Pedido pedido){
        log.info("Solicitando pagamento para o pedido codigo -> {}", pedido.getCodigo());
        return UUID.randomUUID().toString();
    }

    public void enviaSolicitacaoPagamento(Pedido pedido) {
        // try/catch fictício
        try {
            String chavePagamento = solicitarPagamento(pedido);
            if(chavePagamento != null){
                pedido.setChavePagamento(chavePagamento);
            }
        }catch (Exception e){
            throw new RuntimeException("Erro ao efetuar pagamento ", e);
        }
    }
}
