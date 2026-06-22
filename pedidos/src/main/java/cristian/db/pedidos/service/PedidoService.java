package cristian.db.pedidos.service;

import cristian.db.pedidos.client.ClientesClient;
import cristian.db.pedidos.client.ProdutosClient;
import cristian.db.pedidos.client.ServiceBancario;
import cristian.db.pedidos.client.representation.ClienteRepresentantion;
import cristian.db.pedidos.client.representation.ProdutoRepresentation;
import cristian.db.pedidos.dto.CallbackPagamentoResponseDto;
import cristian.db.pedidos.dto.NovoPagamentoRequestDto;
import cristian.db.pedidos.dto.PedidoRequestDto;
import cristian.db.pedidos.dto.PedidoResponseDto;
import cristian.db.pedidos.exception.BusinessException;
import cristian.db.pedidos.mapper.PedidoMapper;
import cristian.db.pedidos.model.ItemPedido;
import cristian.db.pedidos.model.Pedido;
import cristian.db.pedidos.model.enums.StatusPedido;
import cristian.db.pedidos.publisher.DetalhePedidoMapper;
import cristian.db.pedidos.publisher.representation.DetalhePedidoRepDto;
import cristian.db.pedidos.repository.ItemPedidoRepository;
import cristian.db.pedidos.repository.PedidoRepository;
import cristian.db.pedidos.service.validador.Validator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final Validator validator;
    private final PedidoMapper pedidoMapper;
    private final ServiceBancario serviceBancario;
    private final ClientesClient apiClientes;
    private final ProdutosClient apiProdutos;
    private final DetalhePedidoMapper detalhePedidoMapper;


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

    public DetalhePedidoRepDto carregarDadosCompletos(Long codigo) {
        Pedido pedido = pedidoRepository.findById(codigo)
                        .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
        carregarDadosCliente(pedido);
        carregarItensPedido(pedido);
        return detalhePedidoMapper.toDetalheDto(pedido);
    }

    private void carregarDadosCliente(Pedido pedido) {
        Long codigoCliente = pedido.getCodigoCliente();
        ResponseEntity<ClienteRepresentantion> details = apiClientes.details(codigoCliente);
        pedido.setCliente(details.getBody());
    }

    private void carregarItensPedido(Pedido pedido) {
        List<ItemPedido> pedidoItens = itemPedidoRepository.findByPedido(pedido);
        pedido.setItens(pedidoItens);
        pedido.getItens().forEach(this::carregarDadoProduto);
    }

    private void carregarDadoProduto(ItemPedido itemPedido) {
        ResponseEntity<ProdutoRepresentation> details = apiProdutos.details(itemPedido.getCodigoProduto());
        if (details.getBody() != null) {
            itemPedido.setNome(details.getBody().nome());
        }
    }


}
