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

import app.controller.ClienteController;
import app.entity.Cliente;
import app.service.ClienteService;

@SpringBootTest
public class ClienteControllerTest {

    @Autowired
    ClienteController clienteController;

    @MockBean
    ClienteService clienteService;

    @BeforeEach
    void setup() {
        Cliente cliente1 = new Cliente(1L, "João Silva", "joao.silva@example.com", "(11) 12345-6789", 25, "123.456.789-00", "Rua A, 123", "12345-678", Collections.emptyList());
        Cliente cliente2 = new Cliente(2L, "Maria Oliveira", "maria.oliveira@example.com", "(21) 98765-4321", 30, "234.567.890-12", "Rua B, 456", "23456-789", Collections.emptyList());

        Mockito.when(clienteService.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));
        Mockito.when(clienteService.findById(1L)).thenReturn(cliente1);
        Mockito.when(clienteService.findById(2L)).thenReturn(cliente2);
        Mockito.when(clienteService.save(Mockito.any(Cliente.class))).thenReturn("Cliente salvo com sucesso!");
        Mockito.when(clienteService.update(Mockito.any(Cliente.class), Mockito.anyLong())).thenReturn("Cliente atualizado com sucesso!");
        Mockito.when(clienteService.delete(1L)).thenReturn("Deletado com sucesso!");
        Mockito.when(clienteService.delete(2L)).thenReturn("Deletado com sucesso!");
        Mockito.when(clienteService.listarClientesComIdadeEntre18e35()).thenReturn(Arrays.asList(cliente1, cliente2));
    }

    @Test
    @DisplayName("INTEGRAÇÃO - listar todos os clientes")
    void testFindAllSuccess() {
        ResponseEntity<List<Cliente>> retorno = clienteController.findAll();
        List<Cliente> clientes = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(2, clientes.size());
        assertEquals("João Silva", clientes.get(0).getNome());
        assertEquals("Maria Oliveira", clientes.get(1).getNome());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - buscar cliente por ID")
    void testFindByIdSuccess() {
        ResponseEntity<Cliente> retorno = clienteController.findById(1L);
        Cliente cliente = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("João Silva", cliente.getNome());
        assertEquals(25, cliente.getIdade());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - salvar novo cliente")
    void testSaveClienteSuccess() {
        Cliente cliente = new Cliente(3L, "Julia Pereira", "julia.pereira@example.com", "(31) 98765-4321", 28, "345.678.901-23", "Rua C, 789", "34567-890", Collections.emptyList());
        Mockito.when(clienteService.save(cliente)).thenReturn("Cliente salvo com sucesso!");

        ResponseEntity<String> retorno = clienteController.save(cliente);
        String mensagem = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Cliente salvo com sucesso!", mensagem);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - atualizar cliente")
    void testUpdateClienteSuccess() {
        Cliente cliente = new Cliente(1L, "João Silva", "joao.silva@example.com", "(11) 12345-6789", 26, "123.456.789-00", "Rua A, 123", "12345-678", Collections.emptyList());
        Mockito.when(clienteService.update(cliente, 1L)).thenReturn("Cliente atualizado com sucesso!");

        ResponseEntity<String> retorno = clienteController.update(cliente, 1L);
        String mensagem = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Cliente atualizado com sucesso!", mensagem);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - deletar cliente")
    void testDeleteClienteSuccess() {
        Mockito.when(clienteService.delete(1L)).thenReturn("Deletado com sucesso!");

        ResponseEntity<String> retorno = clienteController.delete(1L);

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Deletado com sucesso!", retorno.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - falha ao deletar cliente")
    void testDeleteClienteNotFound() {
        Mockito.doThrow(new RuntimeException("Cliente não encontrado")).when(clienteService).delete(1L);

        ResponseEntity<String> retorno = clienteController.delete(1L);

        assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
        assertEquals("Não encontrado", retorno.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - listar clientes com idade entre 18 e 35")
    void testListarClientesComIdadeEntre18e35Success() {
        ResponseEntity<List<Cliente>> retorno = clienteController.listarClientesComIdadeEntre18e35();
        List<Cliente> clientes = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(2, clientes.size());
        assertEquals("João Silva", clientes.get(0).getNome());
        assertEquals(25, clientes.get(0).getIdade());
        assertEquals("Maria Oliveira", clientes.get(1).getNome());
        assertEquals(30, clientes.get(1).getIdade());
    }
}
