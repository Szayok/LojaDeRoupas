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

import app.controller.FuncionarioController;
import app.entity.Funcionario;
import app.service.FuncionarioService;

@SpringBootTest
public class FuncionarioControllerTest {

    @Autowired
    FuncionarioController funcionarioController;

    @MockBean
    FuncionarioService funcionarioService;

    @BeforeEach
    void setup() {
        Funcionario funcionario1 = new Funcionario(1L, "Ana Souza", "ana.souza@example.com", "(11) 12345-6789", 30, "Rua X, 123", "Gerente", Collections.emptyList());
        Funcionario funcionario2 = new Funcionario(2L, "Carlos Oliveira", "carlos.oliveira@example.com", "(21) 98765-4321", 40, "Rua Y, 456", "Analista", Collections.emptyList());

        Mockito.when(funcionarioService.findAll()).thenReturn(Arrays.asList(funcionario1, funcionario2));
        Mockito.when(funcionarioService.findById(1L)).thenReturn(funcionario1);
        Mockito.when(funcionarioService.findById(2L)).thenReturn(funcionario2);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - listar todos os funcionários")
    void testFindAllSuccess() {
        ResponseEntity<List<Funcionario>> retorno = funcionarioController.findAll();
        List<Funcionario> funcionarios = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals(2, funcionarios.size());
        assertEquals("Ana Souza", funcionarios.get(0).getNome());
        assertEquals("Carlos Oliveira", funcionarios.get(1).getNome());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - buscar funcionário por ID")
    void testFindByIdSuccess() {
        ResponseEntity<Funcionario> retorno = funcionarioController.findById(1L);
        Funcionario funcionario = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Ana Souza", funcionario.getNome());
        assertEquals(30, funcionario.getIdade());
    }

    @Test
    @DisplayName("INTEGRAÇÃO - salvar novo funcionário")
    void testSaveFuncionarioSuccess() {
        Funcionario funcionario = new Funcionario(3L, "Julia Pereira", "julia.pereira@example.com", "(31) 98765-4321", 28, "Rua Z, 789", "Assistente", Collections.emptyList());
        Mockito.when(funcionarioService.save(funcionario)).thenReturn("Funcionário salvo com sucesso!");

        ResponseEntity<String> retorno = funcionarioController.save(funcionario);
        String mensagem = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Funcionário salvo com sucesso!", mensagem);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - atualizar funcionário")
    void testUpdateFuncionarioSuccess() {
        Funcionario funcionario = new Funcionario(1L, "Ana Souza", "ana.souza@example.com", "(11) 12345-6789", 31, "Rua X, 123", "Gerente", Collections.emptyList());
        Mockito.when(funcionarioService.update(funcionario, 1L)).thenReturn("Funcionário atualizado com sucesso!");

        ResponseEntity<String> retorno = funcionarioController.update(funcionario, 1L);
        String mensagem = retorno.getBody();

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Funcionário atualizado com sucesso!", mensagem);
    }

    @Test
    @DisplayName("INTEGRAÇÃO - deletar funcionário")
    void testDeleteFuncionarioSuccess() {
        Mockito.when(funcionarioService.delete(1L)).thenReturn("Deletado com sucesso!");

        ResponseEntity<String> retorno = funcionarioController.delete(1L);

        assertEquals(HttpStatus.OK, retorno.getStatusCode());
        assertEquals("Deletado com sucesso!", retorno.getBody());
    }

    
    @Test
    @DisplayName("INTEGRAÇÃO - falha ao deletar funcionário")
    void testDeleteFuncionarioNotFound() {
        Mockito.doThrow(new RuntimeException("Funcionário não encontrado")).when(funcionarioService).delete(1L);

        ResponseEntity<String> retorno = funcionarioController.delete(1L);

        assertEquals(HttpStatus.BAD_REQUEST, retorno.getStatusCode());
        assertEquals("Não encontrado", retorno.getBody());
    }
}
