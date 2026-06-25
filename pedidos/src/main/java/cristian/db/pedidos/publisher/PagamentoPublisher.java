package cristian.db.pedidos.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cristian.db.pedidos.model.Pedido;
import cristian.db.pedidos.publisher.representation.DetalhePedidoRepDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PagamentoPublisher {

    private final DetalhePedidoMapper detalhePedidoMapper;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${icompras.config.kafka.topics.pedidos-pagos}")
    private String topico;

    public void publicar(Pedido pedido){
        log.info("Publicando pedido com o codigo {}", pedido.getCodigo());
        try {
            DetalhePedidoRepDto pedidoRepDto = detalhePedidoMapper.toDetalheDto(pedido);
            String json = objectMapper.writeValueAsString(pedidoRepDto);
            kafkaTemplate.send(topico, "dados", json);
            
        }catch (JsonProcessingException e){
            log.error("Erro ao serializar DetalhePedidoRepDto", e);
        }catch (RuntimeException e){
            log.error("Erro ao publicar nos tópicos", e);
        }
    }
}
