package com.exemplo.banco.repositorio;

import com.exemplo.banco.modelo.Investimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestimentoRepositorio extends JpaRepository<Investimento, Long> {
    List<Investimento> findByContaId(Long contaId);
    List<Investimento> findByStatus(String status);
}
