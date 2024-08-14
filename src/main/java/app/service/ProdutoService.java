package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Produto;
import app.repository.ProdutoRepository;



@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository produtoRepository;
	
	public String save(Produto produto) {
		this.produtoRepository.save(produto);
		
		return "Produto criado com sucesso";
	}
	
	public String update(Produto produto, Long id) {
		produto.setId(id);
		this.produtoRepository.save(produto);
		return "Produto alterado com sucesso";
	}
	
	public String delete(Long Id) {
		
		this.produtoRepository.deleteById(Id);
		
		
		return "Produto deletado com sucesso";
		
	}
	
	public Produto findById(Long id) {
		
		Optional<Produto> optional = this.produtoRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
		
	public List<Produto> findAll() {
		
		return this.produtoRepository.findAll();
	}
	 public List<Produto> buscarTop10ProdutosMaisCaros() {
	        return produtoRepository.findTop10ByOrderByPrecoDesc();
	    }
	
}
