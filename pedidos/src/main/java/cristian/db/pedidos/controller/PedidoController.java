package cristian.db.pedidos.controller;

import cristian.db.pedidos.dto.PedidoRequestDto;
import cristian.db.pedidos.dto.PedidoResponseDto;
import cristian.db.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDto> criarPedido(@RequestBody PedidoRequestDto requestDto){
        return ResponseEntity.ok(pedidoService.criarPedido(requestDto));
    }
 }
