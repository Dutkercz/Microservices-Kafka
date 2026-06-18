package cristian.db.clientes.dto;

public record ClienteRequestDto(String nome, String cpf, String logradouro, String numero, String bairro,
                                String email, String telefone) {
}
