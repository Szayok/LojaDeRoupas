package app.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import app.entity.Produto;
import app.entity.Venda;
import app.exception.ValorMaximoException;
import app.repository.VendaRepository;
import app.service.ClienteService;
import app.service.ProdutoService;
import app.service.VendaService;

@SpringBootTest
public class VendaServiceTest {

    @Autowired
    private VendaService vendaService;

    @MockBean
    private VendaRepository vendaRepository;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private ClienteService clienteService;

    @Test
    public void testSalvarVendaComSucesso() {
        // Criação de cliente e produtos simulados
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setIdade(25);

        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setPreco(200.0);

        Produto produto2 = new Produto();
        produto2.setId(2L);
        produto2.setPreco(300.0);

        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setProduto(Arrays.asList(produto1, produto2));

        // Simulando o comportamento dos serviços
        when(clienteService.findById(any(Long.class))).thenReturn(cliente);
        when(produtoService.findById(1L)).thenReturn(produto1);
        when(produtoService.findById(2L)).thenReturn(produto2);
        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);

        // Executando o teste
        String result = vendaService.save(venda);

        assertEquals("Venda cadastrada com sucesso", result);
        assertEquals(500.0, venda.getTotal()); // Verifica se o total foi calculado corretamente
        verify(vendaRepository, times(1)).save(venda);
    }

    @Test
    public void testSalvarVendaComClienteMenorDeIdade() {
        // Criação de cliente e produtos simulados
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setIdade(16);

        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setPreco(300.0);

        Produto produto2 = new Produto();
        produto2.setId(2L);
        produto2.setPreco(300.0);

        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setProduto(Arrays.asList(produto1, produto2));

        // Simulando o comportamento dos serviços
        when(clienteService.findById(any(Long.class))).thenReturn(cliente);
        when(produtoService.findById(1L)).thenReturn(produto1);
        when(produtoService.findById(2L)).thenReturn(produto2);

        // Executando o teste e verificando exceção
        assertThrows(ValorMaximoException.class, () -> vendaService.save(venda));

        verify(vendaRepository, times(0)).save(venda); // Verifica que não salvou a venda
    }

    @Test
    public void testUpdateVenda() {
        Venda venda = new Venda();
        venda.setId(1L);
        venda.setTotal(400.0);

        when(vendaRepository.save(any(Venda.class))).thenReturn(venda);

        String result = vendaService.update(venda, 1L);

        assertEquals("Venda alterada com sucesso", result);
        assertEquals(1L, venda.getId());
        verify(vendaRepository, times(1)).save(venda);
    }

    @Test
    public void testDeleteVenda() {
        Long vendaId = 1L;

        String result = vendaService.delete(vendaId);

        assertEquals("Venda deletada com sucesso", result);
        verify(vendaRepository, times(1)).deleteById(vendaId);
    }

    @Test
    public void testFindByIdVendaExistente() {
        Venda venda = new Venda();
        venda.setId(1L);
        venda.setTotal(500.0);

        when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));

        Venda result = vendaService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals(500.0, result.getTotal());
        verify(vendaRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdVendaInexistente() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.empty());

        Venda result = vendaService.findById(1L);

        assertNull(result);
        verify(vendaRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAllVendas() {
        Venda venda1 = new Venda();
        venda1.setTotal(300.0);

        Venda venda2 = new Venda();
        venda2.setTotal(400.0);

        when(vendaRepository.findAll()).thenReturn(Arrays.asList(venda1, venda2));

        List<Venda> result = vendaService.findAll();

        assertEquals(2, result.size());
        assertEquals(300.0, result.get(0).getTotal());
        verify(vendaRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarVendasPorNomeCliente() {
        Venda venda1 = new Venda();
        venda1.setTotal(300.0);

        when(vendaRepository.findByClienteNomeContainingIgnoreCase("Carlos")).thenReturn(Arrays.asList(venda1));

        List<Venda> result = vendaService.buscarVendasPorNomeCliente("Carlos");

        assertEquals(1, result.size());
        assertEquals(300.0, result.get(0).getTotal());
        verify(vendaRepository, times(1)).findByClienteNomeContainingIgnoreCase("Carlos");
    }

    @Test
    public void testBuscarTop10VendasPorTotal() {
        Venda venda1 = new Venda();
        venda1.setTotal(500.0);

        Venda venda2 = new Venda();
        venda2.setTotal(400.0);

        when(vendaRepository.findAllByOrderByTotalDesc()).thenReturn(Arrays.asList(venda1, venda2));

        List<Venda> result = vendaService.findAllByOrderByTotalDesc();

        assertEquals(2, result.size());
        assertEquals(500.0, result.get(0).getTotal());
        verify(vendaRepository, times(1)).findAllByOrderByTotalDesc();
    }
}
