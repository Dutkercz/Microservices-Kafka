package cristian.db.clientes.services;

import cristian.db.clientes.dto.ClienteRequestDto;
import cristian.db.clientes.dto.ClienteResponseDto;
import cristian.db.clientes.model.Cliente;
import cristian.db.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteResponseDto save(ClienteRequestDto requestDto){
        return new ClienteResponseDto(clienteRepository.save(new Cliente(requestDto)));
    }

    public Optional<Cliente> findByCodigo(Long codigo){
        return clienteRepository.findById(codigo);
    }
}
