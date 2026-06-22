package cristian.db.pedidos.service;

import cristian.db.pedidos.client.ServiceBancario;
import cristian.db.pedidos.dto.CallbackPagamentoResponseDto;
import cristian.db.pedidos.dto.NovoPagamentoRequestDto;
import cristian.db.pedidos.dto.PedidoRequestDto;
import cristian.db.pedidos.dto.PedidoResponseDto;
import cristian.db.pedidos.exception.BusinessException;
import cristian.db.pedidos.mapper.PedidoMapper;
import cristian.db.pedidos.model.Pedido;
import cristian.db.pedidos.model.enums.StatusPedido;
import cristian.db.pedidos.repository.ItemPedidoRepository;
import cristian.db.pedidos.repository.PedidoRepository;
import cristian.db.pedidos.service.validador.Validator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
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

        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());

        serviceBancario.enviaSolicitacaoPagamento(pedido);
        return pedidoMapper.toResponseDto(pedido);
    }

    @Transactional
    public void updateStatusPagamento(CallbackPagamentoResponseDto requestDto) {
        Pedido pedido = pedidoRepository.findByCodigoAndChavePagamento(requestDto.codigo(), requestDto.chavePagamento())
            .orElseThrow(() -> new BusinessException("Pedido não encontrado para o ID e chave de pagamento"));

        validator.validarSucessoPagamentoESetNovoStatus(requestDto, pedido);
    }

    @Transactional
    public void addNovoPagamento(NovoPagamentoRequestDto requestDto) {
       Pedido pedido = pedidoRepository.findById(requestDto.codigoPedido())
               .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
       pedido.setDadosPagamento(requestDto.dadosPagamento());
       pedido.setStatus(StatusPedido.REALIZADO);
       pedido.setObservacoes("Aguardando Processamento");
       serviceBancario.enviaSolicitacaoPagamento(pedido);
    }
}
