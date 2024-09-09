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

import app.controller.ProdutoController;
import app.entity.Produto;
import app.service.ProdutoService;

@SpringBootTest
public class ProdutoControllerTest {

    @Autowired
    ProdutoController produtoController;

    @MockBean
    ProdutoService produtoService;

    @BeforeEach
    void setup() {
        Produto produto1 = new Produto(1L, "Produto 1", "Descrição 1", 100.0, Collections.emptyList());
        Produto produto2 = new Produto(2L, "Produto 2", "Descrição 2", 200.0, Collections.emptyList());

        Mockito.when(produtoService.findAll()).thenReturn(Arrays.asList(produto1, produto2));
        Mockito.when(produtoService.findById(1L)).thenReturn(produto1);
        Mockito.when(produtoService.findById(2L)).thenReturn(produto2);
        Mockito.when(produtoService.save(produto1)).thenReturn("Produto salvo com sucesso!");
        Mockito.when(produtoService.update(produto1, 1L)).thenReturn("Produto atualizado com sucesso!");
        Mockito.when(produtoService.delete(1L)).thenReturn("Deletado com sucesso!");
        Mockito.when(produtoService.buscarTop10ProdutosMaisCaros()).thenReturn(Arrays.asList(produto1, produto2));
    }

    @Test
    @DisplayName("INTEGRAÇÃO - listar todos os produtos")
    void testFindAllSuccess() {
        ResponseEntity<List<Produto>> retorno = produtoController.findAll();
        List<Produto> produtos = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(2, produtos.size());
        assertEquals("Produto 1", produtos.get(0).getNome());
        assertEquals("Produto 2", produtos.get(1).getNome());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - buscar produto por ID")
    void testFindByIdSuccess() {
        ResponseEntity<Produto> retorno = produtoController.findById(1L);
        Produto produto = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Produto 1", produto.getNome());
        assertEquals(100.0, produto.getPreco());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - salvar novo produto")
    void testSaveProdutoSuccess() {
        Produto produto = new Produto(3L, "Produto 3", "Descrição 3", 300.0, Collections.emptyList());
        Mockito.when(produtoService.save(produto)).thenReturn("Produto salvo com sucesso!");

        ResponseEntity<String> retorno = produtoController.save(produto);
        String mensagem = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Produto salvo com sucesso!", mensagem);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - atualizar produto")
    void testUpdateProdutoSuccess() {
        Produto produto = new Produto(1L, "Produto Atualizado", "Descrição Atualizada", 150.0, Collections.emptyList());
        Mockito.when(produtoService.update(produto, 1L)).thenReturn("Produto atualizado com sucesso!");

        ResponseEntity<String> retorno = produtoController.update(produto, 1L);
        String mensagem = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Produto atualizado com sucesso!", mensagem);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - deletar produto")
    void testDeleteProdutoSuccess() {
        Mockito.when(produtoService.delete(1L)).thenReturn("Deletado com sucesso!");

        ResponseEntity<String> retorno = produtoController.delete(1L);

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Deletado com sucesso!", retorno.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - falha ao deletar produto")
    void testDeleteProdutoNotFound() {
        Mockito.when(produtoService.delete(1L)).thenThrow(new RuntimeException("Produto não encontrado"));

        ResponseEntity<String> retorno = produtoController.delete(1L);

        assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
        assertEquals("Não encontrado", retorno.getBody());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - listar top 10 produtos mais caros")
    void testListarTop10ProdutosMaisCaros() {
        // Preparar os produtos esperados para o teste
        Produto produto1 = new Produto(1L, "Produto 1", "Descrição 1", 100.0, Collections.emptyList());
        Produto produto2 = new Produto(2L, "Produto 2", "Descrição 2", 200.0, Collections.emptyList());

        // Configurar o comportamento do mock
        Mockito.when(produtoService.buscarTop10ProdutosMaisCaros()).thenReturn(Arrays.asList(produto1, produto2));

        // Chamar o método do controlador
        List<Produto> produtos = produtoController.listarTop10ProdutosMaisCaros();

        // Verificar se a resposta está correta
        assertEquals(2, produtos.size());
        assertEquals("Produto 1", produtos.get(0).getNome());
        assertEquals("Produto 2", produtos.get(1).getNome());
    }

}
