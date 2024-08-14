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

import app.entity.Venda;
import app.service.VendaService;

@RestController
@RequestMapping("/api/venda")
public class VendaController {

	@Autowired
	VendaService vendaService;

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Venda venda) {

		try {

			String msg = this.vendaService.save(venda);

			return new ResponseEntity<String>(msg, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@RequestBody Venda venda, @PathVariable Long id) {
		try {
			String msg = this.vendaService.update(venda, id);

			return new ResponseEntity<String>(msg, HttpStatus.OK);

		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {

		try {
			this.vendaService.delete(id);

			return new ResponseEntity<String>("Deletado com sucesso!", HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>("Não encontrado", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Venda>> findAll() {
		try {
			List<Venda> lista = this.vendaService.findAll();
			return new ResponseEntity<List<Venda>>(lista, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/findById/{id}")
	public ResponseEntity<Venda> findById(@PathVariable Long id) {

		try {
			Venda venda = this.vendaService.findById(id);

			return new ResponseEntity<Venda>(venda, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/cliente/{nome}")
	public ResponseEntity<List<Venda>> findVendasByClienteNome(@PathVariable String nome) {
		try {
			List<Venda> vendas = this.vendaService.buscarVendasPorNomeCliente(nome);
			return new ResponseEntity<>(vendas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
	 @GetMapping("/funcionario/{nome}")
	    public ResponseEntity<List<Venda>> findVendasByFuncionarioNome(@PathVariable String nome) {
	        try {
	            List<Venda> vendas = this.vendaService.buscarVendasPorNomeFuncionario(nome);
	            return new ResponseEntity<>(vendas, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	        }
	    }

	    
	    @GetMapping("/top10")
	    public ResponseEntity<List<Venda>> findTop10Vendas() {
	        try {
	            List<Venda> vendas = this.vendaService.findAllByOrderByTotalDesc();
	            return new ResponseEntity<>(vendas, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	        }
	    }
}