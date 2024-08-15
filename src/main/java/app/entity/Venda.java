package app.entity;




import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotBlank
    private String observacao;

    private Double total;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("venda")
    @JoinTable(name="Venda_Produto")
    @NotEmpty
    private List<Produto> produto;

    @ManyToOne(cascade = CascadeType.MERGE, optional = true)
    @JsonIgnoreProperties("venda")
    private Funcionario funcionario;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JsonIgnoreProperties("venda")
    private Cliente cliente;
	
}
