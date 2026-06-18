package cristian.db.clientes.model;

import cristian.db.clientes.dto.ClienteRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 11)
    private String cpf;

    @Column(length = 100)
    private String logradouro;

    @Column(length = 10)
    private String numero;

    @Column(length = 100)
    private String bairro;

    @Column(length = 150)
    private String email;

    @Column(length = 20)
    private String telefone;

    public Cliente(ClienteRequestDto requestDto) {
        this.nome = requestDto.nome();
        this.cpf = requestDto.cpf();
        this.logradouro = requestDto.logradouro();
        this.numero = requestDto.numero();
        this.bairro = requestDto.bairro();
        this.email = requestDto.email();
        this.telefone = requestDto.telefone();
    }

}
