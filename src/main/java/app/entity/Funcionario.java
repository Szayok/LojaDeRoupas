package app.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class Funcionario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nome;
	@Email
	private String email;

	@Pattern(regexp = "^(\\(\\d{2}\\) \\d{5}-\\d{4}|\\d{2} \\d{5}-\\d{4})$", 
			message = "Telefone inválido. "
			+ "Deve seguir o formato (XX) XXXXX-XXXX ou XX XXXXX-XXXX.")
	private String telefone;
	
	@Min(value  = 10, message = "A idade mínima é 10 anos")
	@Max(value = 110, message = "A idade máxima é 110 anos")
	private int idade;
	
	@NotBlank
	private String endereco;
	private String funcao;

	@OneToMany(mappedBy = "funcionario")
	private List<Venda> venda;

}
