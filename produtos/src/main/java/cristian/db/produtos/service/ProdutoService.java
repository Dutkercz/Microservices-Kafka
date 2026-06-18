package cristian.db.produtos.service;

import cristian.db.produtos.dto.ProdutoResquestDto;
import cristian.db.produtos.model.Produto;
import cristian.db.produtos.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Produto save(ProdutoResquestDto produto){
        return produtoRepository.save(new Produto(produto));
    }

    public Optional<Produto> findByCodigo(Long codigo){
        return produtoRepository.findById(codigo);
    }
}
