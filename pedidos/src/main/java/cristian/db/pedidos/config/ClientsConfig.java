package cristian.db.pedidos.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "cristian.db.pedidos.client") //define o local onde estão o feign clients
public class ClientsConfig {



}
