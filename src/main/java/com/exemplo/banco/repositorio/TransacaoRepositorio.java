package com.exemplo.banco.repositorio;

import com.exemplo.banco.modelo.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepositorio extends JpaRepository<Transacao, Long> {
}