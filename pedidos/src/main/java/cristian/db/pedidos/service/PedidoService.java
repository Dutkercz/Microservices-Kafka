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

    @Transactional
    public void updateStatusPagamento(CallbackPagamentoResponseDto requestDto) {
        Pedido pedido = pedidoRepository.findByCodigoAndChavePagamento(requestDto.codigo(), requestDto.chavePagamento())
            .orElseThrow(() -> new BusinessException("Pedido não encontrado para o ID e chave de pagamento"));

        validarSucessoPagamentoESetNovoStatus(requestDto, pedido);
    }

    private static void validarSucessoPagamentoESetNovoStatus(CallbackPagamentoResponseDto requestDto, Pedido pedido) {
        boolean sucesso = requestDto.status();
        if (sucesso) {
            pedido.setObservacoes(requestDto.observacoes());
            pedido.setStatus(StatusPedido.PAGO);
        }
        else {
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(requestDto.observacoes());
        }
    }

    @Transactional
    public void addNovoPagamento(NovoPagamentoRequestDto requestDto) {
       Pedido pedido = pedidoRepository.findById(requestDto.codigoPedido())
               .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
       pedido.setDadosPagamento(requestDto.dadosPagamento());
       pedido.setStatus(StatusPedido.REALIZADO);
       pedido.setObservacoes("Aguardando Processamento");
       enviaSolicitacaoPagamento(pedido);
    }
}
