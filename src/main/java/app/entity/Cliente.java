package app.entity;

import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Insira um nome")
	@Pattern(regexp = "^(\\S+\\s+\\S+.*)$", message = "O nome deve conter pelo menos duas palavras")
	private String nome;
	
	@Email(message = "Insira um email válido")
	@NotBlank(message = "Insira um email")
	private String email; 
	
	@Pattern(regexp = "^(\\(\\d{2}\\) \\d{5}-\\d{4}|\\d{2} \\d{5}-\\d{4})$", 
			message = "Telefone inválido. "
			+ "Deve seguir o formato (XX) XXXXX-XXXX ou XX XXXXX-XXXX.")
	private String telefone; 
	
	@Min(value  = 10, message = "A idade mínima é 10 anos")
	@Max(value = 110, message = "A idade máxima é 110 anos")
	private int idade;
	
	@CPF
	private String cpf;
	
	@NotBlank
	private String endereco;
	
	@Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve seguir o formato 99999-999")
    private String cep;

	@OneToMany(mappedBy = "cliente")
	private List <Venda> venda;
}
