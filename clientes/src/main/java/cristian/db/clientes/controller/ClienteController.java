package cristian.db.clientes.controller;

import cristian.db.clientes.dto.ClienteRequestDto;
import cristian.db.clientes.dto.ClienteResponseDto;
import cristian.db.clientes.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;



    @PostMapping
    public ResponseEntity<ClienteResponseDto> save(@RequestBody ClienteRequestDto requestDto){
        return ResponseEntity.ok(clienteService.save(requestDto));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ClienteResponseDto> datails(@PathVariable Long codigo){
        return clienteService.findByCodigo(codigo)
                             .map(ClienteResponseDto::new)
                             .map(ResponseEntity::ok)
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
