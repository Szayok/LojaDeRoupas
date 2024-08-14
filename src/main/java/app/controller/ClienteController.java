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

import app.entity.Cliente;
import app.service.ClienteService;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
	
	@Autowired
	ClienteService clienteService;

	@PostMapping("/save")
	public ResponseEntity<String>save(@RequestBody Cliente cliente){
		try {
			String msg = this.clienteService.save(cliente);
			
			return new ResponseEntity<String>(msg, HttpStatus.OK);
			
		}catch(Exception e){
			
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<String>update(@RequestBody Cliente cliente, @PathVariable Long id){
		try {
			String msg = this.clienteService.update(cliente, id);
			
			return new ResponseEntity<String>(msg, HttpStatus.OK);
			
		} catch (Exception e) {
			
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String>delete(@PathVariable Long id){
		
		try {
			this.clienteService.delete(id);
			
			return new ResponseEntity<String>("Deletado com sucesso!", HttpStatus.OK);
		} catch (Exception e) {
			
			return new ResponseEntity<>("Não encontrado", HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/findById/{id}")
	public ResponseEntity<Cliente>findById(@PathVariable Long id){
		
		try {
			Cliente cliente = this.clienteService.findById(id);
			
			return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		} catch (Exception e) {
			
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		
	}
	@GetMapping("/findAll")
	public ResponseEntity<List<Cliente>>findAll(){
		try {
			List<Cliente> lista = clienteService.findAll();
			
			return new ResponseEntity<List<Cliente>>(lista, HttpStatus.OK);
		}catch(Exception e) {
			
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/idade18a35")
    public ResponseEntity<List<Cliente>> listarClientesComIdadeEntre18e35() {
        try {
            List<Cliente> clientes = clienteService.listarClientesComIdadeEntre18e35();
            return new ResponseEntity<>(clientes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
	
}
