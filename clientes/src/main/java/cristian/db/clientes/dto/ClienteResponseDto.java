package cristian.db.clientes.dto;

import cristian.db.clientes.model.Cliente;

public record ClienteResponseDto(Long codigo, String nome, String cpf, String logradouro, String numero, String bairro,
                                 String email, String telefone) {

    public ClienteResponseDto(Cliente cliente) {
        this(cliente.getCodigo(), cliente.getNome(), cliente.getCpf(), cliente.getLogradouro(), cliente.getNumero(),
             cliente.getBairro(), cliente.getEmail(), cliente.getTelefone());
    }
}
