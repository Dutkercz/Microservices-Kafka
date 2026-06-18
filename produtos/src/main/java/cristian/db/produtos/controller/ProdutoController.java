package cristian.db.produtos.controller;

import cristian.db.produtos.dto.ProdutoResponseDto;
import cristian.db.produtos.dto.ProdutoResquestDto;
import cristian.db.produtos.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDto> save(@RequestBody ProdutoResquestDto produtoResquestDto){
        return ResponseEntity.ok(new ProdutoResponseDto(produtoService.save(produtoResquestDto)));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ProdutoResponseDto> datails(@PathVariable Long codigo){
        return produtoService.findByCodigo(codigo)
                             .map(ProdutoResponseDto::new)
                             .map(ResponseEntity::ok)
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
