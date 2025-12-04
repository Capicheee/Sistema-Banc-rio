package com.exemplo.banco.servico;

import com.exemplo.banco.modelo.Conta;

import java.util.List;
import java.util.Optional;

public interface ContaServico {
    Conta criar(Conta conta);
    Optional<Conta> obterPorId(Long id);
    List<Conta> listarTodas();
    Conta atualizar(Long id, Conta conta);
    boolean deletar(Long id);
}