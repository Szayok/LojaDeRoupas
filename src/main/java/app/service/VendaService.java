package app.service;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Cliente;
import app.entity.Produto;
import app.entity.Venda;
import app.exception.ValorMaximoException;
import app.repository.VendaRepository;

@Service
public class VendaService {

	@Autowired
	private VendaRepository vendaRepository;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;

	public String save(Venda venda) {
		Double total = 0.0;
		
		Cliente cliente = clienteService.findById(venda.getCliente().getId());

		for (int i = 0; i < venda.getProduto().size(); i++) {
			if (venda.getProduto().get(i) != null) {
				Produto produto = this.produtoService.findById(venda.getProduto().get(i).getId());
				total += produto.getPreco();
			} else {
				total += venda.getProduto().get(i).getPreco();
			}
		}
		
		if (cliente.getIdade() < 18 && total > 500) {
		    System.out.println("Idade do cliente: " + venda.getCliente().getIdade());
		    throw new ValorMaximoException("Menor de idade não pode comprar acima de 500 reais");
		}else {
			venda.setTotal(total);

			this.vendaRepository.save(venda);
			return "Venda cadastrada com sucesso";
		}
	}

	public String update(Venda venda, Long id) {
		venda.setId(id);
		this.vendaRepository.save(venda);
		return "Venda alterada com sucesso";
	}

	public String delete(Long Id) {

		this.vendaRepository.deleteById(Id);

		return "Venda deletada com sucesso";

	}

	public Venda findById(Long id) {

		Optional<Venda> optional = this.vendaRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}

	public List<Venda> findAll() {

		return this.vendaRepository.findAll();
	}

	public List<Venda> buscarVendasPorNomeCliente(String nome) {
		return vendaRepository.findByClienteNomeContainingIgnoreCase(nome);
	}

	public List<Venda> buscarVendasPorNomeFuncionario(String nome) {
		return vendaRepository.findByFuncionarioNomeContainingIgnoreCase(nome);
	}

	public List<Venda> findAllByOrderByTotalDesc() {
		List<Venda> vendas = vendaRepository.findAllByOrderByTotalDesc();
		return vendas.stream().limit(10).collect(Collectors.toList());
	}
}
