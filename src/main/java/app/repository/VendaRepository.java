package app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    // Lista as 10 vendas com maiores totais
    List<Venda> findAllByOrderByTotalDesc();

    // Busca vendas por parte do nome do cliente
    List<Venda> findByClienteNomeContainingIgnoreCase(String nome);

    // Busca vendas por parte do nome do funcionário
    List<Venda> findByFuncionarioNomeContainingIgnoreCase(String nome);
}
