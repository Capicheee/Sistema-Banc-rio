package com.exemplo.banco.repositorio;

import com.exemplo.banco.modelo.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepositorio extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByContaId(Long contaId);
    List<Emprestimo> findByStatus(String status);
}