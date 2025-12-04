package com.exemplo.banco.repositorio;

import com.exemplo.banco.modelo.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepositorio extends JpaRepository<Conta, Long> {
    
}