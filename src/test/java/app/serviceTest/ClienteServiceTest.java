package app.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import app.entity.Cliente;
import app.repository.ClienteRepository;
import app.service.ClienteService;

@SpringBootTest
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @MockBean
    private ClienteRepository clienteRepository;

    @Test
    public void testSalvarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Carlos");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        String result = clienteService.save(cliente);

        assertEquals("Cliente criado com sucesso", result);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    public void testAtualizarCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Carlos");

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        String result = clienteService.update(cliente, 1L);

        assertEquals("Cliente alterado com sucesso", result);
        verify(clienteRepository, times(1)).save(cliente);
        assertEquals(1L, cliente.getId());
    }

    @Test
    public void testDeletarCliente() {
        Long clienteId = 1L;

        String result = clienteService.delete(clienteId);

        assertEquals("Cliente deletado com sucesso", result);
        verify(clienteRepository, times(1)).deleteById(clienteId);
    }

    @Test
    public void testFindByIdClienteExistente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Carlos");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Carlos", result.getNome());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdClienteInexistente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        Cliente result = clienteService.findById(1L);

        assertNull(result);
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAllClientes() {
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Carlos");

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Ana");

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        List<Cliente> result = clienteService.findAll();

        assertEquals(2, result.size());
        assertEquals("Carlos", result.get(0).getNome());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    public void testListarClientesComIdadeEntre18e35() {
        Cliente cliente1 = new Cliente();
        cliente1.setIdade(20);

        Cliente cliente2 = new Cliente();
        cliente2.setIdade(30);

        when(clienteRepository.findByIdadeBetween(18, 35)).thenReturn(Arrays.asList(cliente1, cliente2));

        List<Cliente> result = clienteService.listarClientesComIdadeEntre18e35();

        assertEquals(2, result.size());
        assertEquals(20, result.get(0).getIdade());
        verify(clienteRepository, times(1)).findByIdadeBetween(18, 35);
    }
}
