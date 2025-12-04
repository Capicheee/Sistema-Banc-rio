package com.exemplo.banco.servico.impl;

import com.exemplo.banco.modelo.Conta;
import com.exemplo.banco.repositorio.ContaRepositorio;
import com.exemplo.banco.servico.ContaServico;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaServicoImpl implements ContaServico {
    private final ContaRepositorio contaRepositorio;

    public ContaServicoImpl(ContaRepositorio contaRepositorio) {
        this.contaRepositorio = contaRepositorio;
    }

    @Override
    public Conta criar(Conta conta) {
        return contaRepositorio.save(conta);
    }

    @Override
    public Optional<Conta> obterPorId(Long id) {
        return contaRepositorio.findById(id);
    }

    @Override
    public List<Conta> listarTodas() {
        return contaRepositorio.findAll();
    }

    @Override
    public Conta atualizar(Long id, Conta conta) {
        return contaRepositorio.findById(id)
            .map(c -> {
                c.setNumeroConta(conta.getNumeroConta());
                c.setSaldo(conta.getSaldo());
                c.setTipoConta(conta.getTipoConta());
                if (conta.getCliente() != null) {
                    c.setCliente(conta.getCliente());
                }
                return contaRepositorio.save(c);
            })
            .orElse(null);
    }

    @Override
    public boolean deletar(Long id) {
        if (!contaRepositorio.existsById(id)) return false;
        contaRepositorio.deleteById(id);
        return true;
    }
}
