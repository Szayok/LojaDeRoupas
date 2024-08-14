package app.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import app.entity.Funcionario;
import app.service.FuncionarioService;

@RestController
@RequestMapping("/api/funcionario")
public class FuncionarioController {

	
	@Autowired
	FuncionarioService funcionarioService;

	@PostMapping("/save")
	public ResponseEntity<String>save(@RequestBody Funcionario funcionario){
		try {
			String msg = this.funcionarioService.save(funcionario);
			
			return new ResponseEntity<String>(msg, HttpStatus.OK);
			
		}catch(Exception e){
			
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<String>update(@RequestBody Funcionario funcionario, @PathVariable Long id){
		try {
			String msg = this.funcionarioService.update(funcionario, id);
			
			return new ResponseEntity<String>(msg, HttpStatus.OK);
			
		} catch (Exception e) {
			
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String>delete(@PathVariable Long id){
		
		try {
			this.funcionarioService.delete(id);
			
			return new ResponseEntity<String>("Deletado com sucesso!", HttpStatus.OK);
		} catch (Exception e) {
			
			return new ResponseEntity<>("Não encontrado", HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/findById/{id}")
	public ResponseEntity<Funcionario>findById(@PathVariable Long id){
		
		try {
			Funcionario funcionario = this.funcionarioService.findById(id);
			
			return new ResponseEntity<Funcionario>(funcionario, HttpStatus.OK);
		} catch (Exception e) {
			
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		
	}
	@GetMapping("/findAll")
	public ResponseEntity<List<Funcionario>>findAll(){
		try {
			List<Funcionario> lista = funcionarioService.findAll();
			
			return new ResponseEntity<List<Funcionario>>(lista, HttpStatus.OK);
		}catch(Exception e) {
			
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
}
