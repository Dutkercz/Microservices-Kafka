package cristian.db.pedidos.client.representation;

public record ClienteRepresentantion(
        Long codigo,
         String nome,
         String cpf,
         String logradouro,
         String numero,
         String bairro,
         String email,
         String telefone) {
}
