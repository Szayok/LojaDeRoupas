package app.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import app.entity.Produto;
import app.repository.ProdutoRepository;
import app.service.ProdutoService;

@SpringBootTest
public class ProdutoServiceTest {

    @Autowired
    private ProdutoService produtoService;

    @MockBean
    private ProdutoRepository produtoRepository;

    @Test
    public void testSaveProduto() {
        Produto produto = new Produto();
        produto.setPreco(100.0);

        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        String result = produtoService.save(produto);

        assertEquals("Produto criado com sucesso", result);
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    public void testUpdateProduto() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setPreco(150.0);

        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        String result = produtoService.update(produto, 1L);

        assertEquals("Produto alterado com sucesso", result);
        assertEquals(1L, produto.getId());
        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    public void testDeleteProduto() {
        Long produtoId = 1L;

        String result = produtoService.delete(produtoId);

        assertEquals("Produto deletado com sucesso", result);
        verify(produtoRepository, times(1)).deleteById(produtoId);
    }

    @Test
    public void testFindByIdProdutoExistente() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setPreco(200.0);

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Produto result = produtoService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals(200.0, result.getPreco());
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdProdutoInexistente() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

        Produto result = produtoService.findById(1L);

        assertNull(result);
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAllProdutos() {
        Produto produto1 = new Produto();
        produto1.setPreco(100.0);

        Produto produto2 = new Produto();
        produto2.setPreco(200.0);

        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto1, produto2));

        var result = produtoService.findAll();

        assertEquals(2, result.size());
        assertEquals(100.0, result.get(0).getPreco());
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarTop10ProdutosMaisCaros() {
        Produto produto1 = new Produto();
        produto1.setPreco(300.0);

        Produto produto2 = new Produto();
        produto2.setPreco(250.0);

        when(produtoRepository.findTop10ByOrderByPrecoDesc()).thenReturn(Arrays.asList(produto1, produto2));

        var result = produtoService.buscarTop10ProdutosMaisCaros();

        assertEquals(2, result.size());
        assertEquals(300.0, result.get(0).getPreco());
        verify(produtoRepository, times(1)).findTop10ByOrderByPrecoDesc();
    }
}
