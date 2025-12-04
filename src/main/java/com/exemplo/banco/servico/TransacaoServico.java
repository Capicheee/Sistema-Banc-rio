package com.exemplo.banco.servico;

import com.exemplo.banco.modelo.Transacao;
import com.exemplo.banco.repositorio.TransacaoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoServico {

    @Autowired
    private TransacaoRepositorio transacaoRepositorio;

    public Transacao criarTransacao(Transacao transacao) {
        return transacaoRepositorio.save(transacao);
    }

    public List<Transacao> listarTransacoes() {
        return transacaoRepositorio.findAll();
    }

    public Transacao buscarTransacaoPorId(Long id) {
        return transacaoRepositorio.findById(id).orElse(null);
    }
}