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

import app.entity.Produto;

import app.service.ProdutoService;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

	@Autowired
	ProdutoService produtoService;

	@PostMapping("/save")
	public ResponseEntity<String>save(@RequestBody Produto produto){
		try {
			String msg = this.produtoService.save(produto);
			
			return new ResponseEntity<String>(msg, HttpStatus.OK);
			
		}catch(Exception e){
			
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<String>update(@RequestBody Produto produto, @PathVariable Long id){
		try {
			String msg = this.produtoService.update(produto, id);
			
			return new ResponseEntity<String>(msg, HttpStatus.OK);
			
		} catch (Exception e) {
			
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String>delete(@PathVariable Long id){
		
		try {
			this.produtoService.delete(id);
			
			return new ResponseEntity<String>("Deletado com sucesso!", HttpStatus.OK);
		} catch (Exception e) {
			
			return new ResponseEntity<>("Não encontrado", HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/findById/{id}")
	public ResponseEntity<Produto>findById(@PathVariable Long id){
		
		try {
			Produto produto = this.produtoService.findById(id);
			
			return new ResponseEntity<Produto>(produto, HttpStatus.OK);
		} catch (Exception e) {
			
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		
	}
	@GetMapping("/findAll")
	public ResponseEntity<List<Produto>>findAll(){
		try {
			List<Produto> lista = produtoService.findAll();
			
			return new ResponseEntity<List<Produto>>(lista, HttpStatus.OK);
		}catch(Exception e) {
			
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("/10mais-caros")
    public List<Produto> listarTop10ProdutosMaisCaros() {
        return produtoService.buscarTop10ProdutosMaisCaros();
    }
}
