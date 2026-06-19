package cristian.db.pedidos.client;

import cristian.db.pedidos.client.representation.ClienteRepresentantion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente", url = "${icompras.pedidos.clients.clientes.url}")
public interface ClientesClient {

    @GetMapping("/{codigo}")
    ResponseEntity<ClienteRepresentantion> details(@PathVariable Long codigo);
}
