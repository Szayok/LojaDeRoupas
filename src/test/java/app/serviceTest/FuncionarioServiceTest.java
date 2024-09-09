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

import app.entity.Funcionario;
import app.repository.FuncionarioRepository;
import app.service.FuncionarioService;

@SpringBootTest
public class FuncionarioServiceTest {

    @Autowired
    private FuncionarioService funcionarioService;

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    @Test
    public void testSalvarFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("João");

        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);

        String result = funcionarioService.save(funcionario);

        assertEquals("Funcionario criado com sucesso", result);
        verify(funcionarioRepository, times(1)).save(funcionario);
    }

    @Test
    public void testAtualizarFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("João");

        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionario);

        String result = funcionarioService.update(funcionario, 1L);

        assertEquals("Funcionario alterado com sucesso", result);
        verify(funcionarioRepository, times(1)).save(funcionario);
        assertEquals(1L, funcionario.getId());
    }

    @Test
    public void testDeletarFuncionario() {
        Long funcionarioId = 1L;

        String result = funcionarioService.delete(funcionarioId);

        assertEquals("Funcionario deletado com sucesso", result);
        verify(funcionarioRepository, times(1)).deleteById(funcionarioId);
    }

    @Test
    public void testFindByIdFuncionarioExistente() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("João");

        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));

        Funcionario result = funcionarioService.findById(1L);

        assertEquals(1L, result.getId());
        assertEquals("João", result.getNome());
        verify(funcionarioRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdFuncionarioInexistente() {
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.empty());

        Funcionario result = funcionarioService.findById(1L);

        assertNull(result);
        verify(funcionarioRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAllFuncionarios() {
        Funcionario funcionario1 = new Funcionario();
        funcionario1.setNome("João");

        Funcionario funcionario2 = new Funcionario();
        funcionario2.setNome("Maria");

        when(funcionarioRepository.findAll()).thenReturn(Arrays.asList(funcionario1, funcionario2));

        List<Funcionario> result = funcionarioService.findAll();

        assertEquals(2, result.size());
        assertEquals("João", result.get(0).getNome());
        verify(funcionarioRepository, times(1)).findAll();
    }
}
