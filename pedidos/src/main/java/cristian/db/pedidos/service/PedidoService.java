package cristian.db.pedidos.service;

import cristian.db.pedidos.client.ServiceBancario;
import cristian.db.pedidos.dto.PedidoRequestDto;
import cristian.db.pedidos.dto.PedidoResponseDto;
import cristian.db.pedidos.mapper.PedidoMapper;
import cristian.db.pedidos.model.Pedido;
import cristian.db.pedidos.repository.ItemPedidoRepository;
import cristian.db.pedidos.repository.PedidoRepository;
import cristian.db.pedidos.service.validador.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final Validator validator;
    private final PedidoMapper pedidoMapper;
    private final ServiceBancario serviceBancario;


    @Transactional
    public PedidoResponseDto criarPedido(PedidoRequestDto pedidoRequestDto) {
        Pedido pedido = pedidoMapper.toEntity(pedidoRequestDto);
        validator.validar(pedido);
        realizaPersistencia(pedido);
        enviaSolicitacaoPagamento(pedido);
        return pedidoMapper.toResponseDto(pedido);
    }

    private void enviaSolicitacaoPagamento(Pedido pedido) {
        // try/catch fictício
        try {
            String chavePagamento = serviceBancario.solicitarPagamento(pedido);
            if(chavePagamento != null){
                pedido.setChavePagamento(chavePagamento);
            }
        }catch (Exception e){
            throw new RuntimeException("Erro ao efetuar pagamento ", e);
        }
    }

    private void realizaPersistencia(Pedido pedido) {
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }
}
