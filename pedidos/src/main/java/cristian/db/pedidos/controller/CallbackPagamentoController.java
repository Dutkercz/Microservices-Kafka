package cristian.db.pedidos.controller;

import cristian.db.pedidos.dto.CallbackPagamentoResponseDto;
import cristian.db.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos/callback-pagamentos")
@RequiredArgsConstructor
public class CallbackPagamentoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<?> callbackWebHookPagamento(@RequestBody CallbackPagamentoResponseDto requestDto,
                                                      @RequestHeader(required = true, name = "apiKey") String apiKey) {
        //validar apiKey()
        pedidoService.updateStatusPagamento(requestDto);
        return ResponseEntity.ok().build();
    }
}
