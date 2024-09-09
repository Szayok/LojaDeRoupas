package app.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import app.controller.VendaController;
import app.entity.Venda;
import app.service.VendaService;

@SpringBootTest
public class VendaControllerTest {

    @Autowired
    VendaController vendaController;

    @MockBean
    VendaService vendaService;

    @BeforeEach
    void setup() {
        Venda venda1 = new Venda(1L, "Observação 1", 100.0, Collections.emptyList(), null, null);
        Venda venda2 = new Venda(2L, "Observação 2", 200.0, Collections.emptyList(), null, null);

        Mockito.when(vendaService.findAll()).thenReturn(Arrays.asList(venda1, venda2));
        Mockito.when(vendaService.findById(1L)).thenReturn(venda1);
        Mockito.when(vendaService.findById(2L)).thenReturn(venda2);
        Mockito.when(vendaService.buscarVendasPorNomeCliente("João")).thenReturn(Arrays.asList(venda1));
        Mockito.when(vendaService.buscarVendasPorNomeFuncionario("Ana")).thenReturn(Arrays.asList(venda2));
        Mockito.when(vendaService.findAllByOrderByTotalDesc()).thenReturn(Arrays.asList(venda2, venda1));
    }

    @Test
    @DisplayName("INTEGRAÇÃO - listar todas as vendas")
    void testFindAllSuccess() {
        ResponseEntity<List<Venda>> retorno = vendaController.findAll();
        List<Venda> vendas = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(2, vendas.size());
        assertEquals("Observação 1", vendas.get(0).getObservacao());
        assertEquals("Observação 2", vendas.get(1).getObservacao());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - buscar venda por ID")
    void testFindByIdSuccess() {
        ResponseEntity<Venda> retorno = vendaController.findById(1L);
        Venda venda = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Observação 1", venda.getObservacao());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - salvar nova venda")
    void testSaveVendaSuccess() {
        Venda venda = new Venda(3L, "Observação 3", 300.0, Collections.emptyList(), null, null);
        Mockito.when(vendaService.save(venda)).thenReturn("Venda salva com sucesso!");

        ResponseEntity<String> retorno = vendaController.save(venda);
        String mensagem = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Venda salva com sucesso!", mensagem);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - atualizar venda")
    void testUpdateVendaSuccess() {
        Venda venda = new Venda(1L, "Observação Atualizada", 150.0, Collections.emptyList(), null, null);
        Mockito.when(vendaService.update(venda, 1L)).thenReturn("Venda atualizada com sucesso!");

        ResponseEntity<String> retorno = vendaController.update(venda, 1L);
        String mensagem = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Venda atualizada com sucesso!", mensagem);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - deletar venda")
    void testDeleteVendaSuccess() {
        Mockito.when(vendaService.delete(1L)).thenReturn("Venda deletada com sucesso");

        ResponseEntity<String> retorno = vendaController.delete(1L);

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Venda deletada com sucesso", retorno.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - falha ao deletar venda")
    void testDeleteVendaNotFound() {
        Mockito.doThrow(new RuntimeException("Venda não encontrada")).when(vendaService).delete(1L);

        ResponseEntity<String> retorno = vendaController.delete(1L);

        assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
        assertEquals("Não encontrado", retorno.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - falha ao salvar venda")
    void testSaveVendaFailure() {
        Venda venda = new Venda(4L, "Observação Falha", 400.0, Collections.emptyList(), null, null);
        Mockito.when(vendaService.save(venda)).thenThrow(new RuntimeException("Erro ao salvar venda"));

        ResponseEntity<String> retorno = vendaController.save(venda);
        String mensagem = retorno.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
        assertEquals("Erro ao salvar venda", mensagem);
    }


    @Test
    @DisplayName("INTEGRAÇÃO - buscar vendas por nome do cliente")
    void testFindVendasByClienteNomeSuccess() {
        ResponseEntity<List<Venda>> retorno = vendaController.findVendasByClienteNome("João");
        List<Venda> vendas = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(1, vendas.size());
        assertEquals("Observação 1", vendas.get(0).getObservacao());
    }
    @Test
    @DisplayName("INTEGRAÇÃO - falha ao buscar vendas por nome do cliente")
    void testFindVendasByClienteNomeFailure() {
        Mockito.when(vendaService.buscarVendasPorNomeCliente("João")).thenThrow(new RuntimeException("Erro ao buscar vendas"));

        ResponseEntity<List<Venda>> retorno = vendaController.findVendasByClienteNome("João");
        List<Venda> vendas = retorno.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
        assertEquals(null, vendas);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - buscar vendas por nome do funcionário")
    void testFindVendasByFuncionarioNomeSuccess() {
        ResponseEntity<List<Venda>> retorno = vendaController.findVendasByFuncionarioNome("Ana");
        List<Venda> vendas = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(1, vendas.size());
        assertEquals("Observação 2", vendas.get(0).getObservacao());
    }
    @Test
    @DisplayName("INTEGRAÇÃO - falha ao buscar vendas por nome do funcionário")
    void testFindVendasByFuncionarioNomeFailure() {
        Mockito.when(vendaService.buscarVendasPorNomeFuncionario("Ana")).thenThrow(new RuntimeException("Erro ao buscar vendas"));

        ResponseEntity<List<Venda>> retorno = vendaController.findVendasByFuncionarioNome("Ana");
        List<Venda> vendas = retorno.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
        assertEquals(null, vendas);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - listar top 10 vendas")
    void testFindTop10VendasSuccess() {
        ResponseEntity<List<Venda>> retorno = vendaController.findTop10Vendas();
        List<Venda> vendas = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(2, vendas.size());
        assertEquals("Observação 2", vendas.get(0).getObservacao());
        assertEquals("Observação 1", vendas.get(1).getObservacao());
    }
    @Test
    @DisplayName("INTEGRAÇÃO - falha ao listar top 10 vendas")
    void testFindTop10VendasFailure() {
        Mockito.when(vendaService.findAllByOrderByTotalDesc()).thenThrow(new RuntimeException("Erro ao listar vendas"));

        ResponseEntity<List<Venda>> retorno = vendaController.findTop10Vendas();
        List<Venda> vendas = retorno.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
        assertEquals(null, vendas);
    }

}
