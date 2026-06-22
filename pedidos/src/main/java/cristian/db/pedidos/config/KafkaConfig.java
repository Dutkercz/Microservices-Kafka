package cristian.db.pedidos.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${icompras.config.kafka.server-url}")
    private String kafkaServerUrl;


    ///Gerencia a criação de instâncias do Kafka Producer
    /// BOOTSTRAP_SERVERS_CONFIG: Define o endereço do servidor (ou servidores) do cluster Kafka
    /// KEY_SERIALIZER_CLASS_CONFIG: Define como a chave da mensagem será convertida em bytes
    /// VALUE_SERIALIZER_CLASS_CONFIG: Define como o conteúdo (corpo) da mensagem será convertido em bytes.
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    ///pega a fábrica de produtores (producerFactory) que configurei acima e fornece métodos simples para publicar
    /// dados em tópicos, escondendo toda a complexidade da API nativa do Kafka.
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
