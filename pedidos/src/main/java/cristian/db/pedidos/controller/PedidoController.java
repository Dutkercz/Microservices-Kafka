package cristian.db.pedidos.controller;

import cristian.db.pedidos.dto.NovoPagamentoRequestDto;
import cristian.db.pedidos.dto.PedidoRequestDto;
import cristian.db.pedidos.dto.PedidoResponseDto;
import cristian.db.pedidos.publisher.representation.DetalhePedidoRepDto;
import cristian.db.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDto> criarPedido(@RequestBody PedidoRequestDto requestDto) {
        return ResponseEntity.ok(pedidoService.criarPedido(requestDto));
    }

    @PostMapping("/pagamentos")
    public ResponseEntity<Void> atualizarPedido(@RequestBody NovoPagamentoRequestDto requestDto) {
        pedidoService.addNovoPagamento(requestDto);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<DetalhePedidoRepDto> detalhesPedido(@PathVariable Long codigo) {
        return ResponseEntity.ok(pedidoService.carregarDadosCompletos(codigo));
    }
}
