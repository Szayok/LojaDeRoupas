package app.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entity.Cliente;
import app.repository.ClienteRepository;
@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;
	
	public String save(Cliente cliente) {
		this.clienteRepository.save(cliente);
		
		return "Cliente criado com sucesso";
	}
	
	public String update(Cliente cliente, Long id) {
		cliente.setId(id);
		this.clienteRepository.save(cliente);
		return "Cliente alterado com sucesso";
	}
	
	public String delete(Long Id) {
		
		this.clienteRepository.deleteById(Id);
		
		
		return "Cliente deletado com sucesso";
		
	}
	
	public Cliente findById(Long id) {
		
		Optional<Cliente> optional = this.clienteRepository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}
		
	public List<Cliente> findAll() {
		
		return this.clienteRepository.findAll();
	}
	
	 public List<Cliente> listarClientesComIdadeEntre18e35() {
	        return clienteRepository.findByIdadeBetween(18, 35);
	    }
		
}
	

