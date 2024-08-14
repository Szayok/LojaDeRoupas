package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import app.entity.Funcionario;
import app.repository.FuncionarioRepository;


@Service
public class FuncionarioService {

	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	public String save(Funcionario funcionario) {
		this.funcionarioRepository.save(funcionario);
		
		return "Funcionario criado com sucesso";
	}
	
	public String update(Funcionario funcionario, Long id) {
		funcionario.setId(id);
		this.funcionarioRepository.save(funcionario);
		return "Funcionario alterado com sucesso";
	}
	
	public String delete(Long Id) {
		
		this.funcionarioRepository.deleteById(Id);
		
		
		return "Funcionario deletado com sucesso";
		
	}
	
	public Funcionario findById(Long id) {
		
		Optional<Funcionario> optional = this.funcionarioRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
		
	public List<Funcionario> findAll() {
		
		return this.funcionarioRepository.findAll();
	}
	
	
		

}
