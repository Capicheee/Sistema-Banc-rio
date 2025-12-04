package com.exemplo.banco.controlador;

import com.exemplo.banco.dto.TransferenciaDTO;
import com.exemplo.banco.modelo.Transacao;
import com.exemplo.banco.modelo.Conta;
import com.exemplo.banco.repositorio.ContaRepositorio;
import com.exemplo.banco.servico.TransacaoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoControlador {

    @Autowired
    private TransacaoServico transacaoServico;

    @Autowired
    private ContaRepositorio contaRepositorio;

    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody TransferenciaDTO dto) {
        try {
            Conta origem = contaRepositorio.findById(dto.getContaOrigemId())
                .orElseThrow(() -> new RuntimeException("Conta origem não encontrada"));
            
            Conta destino = contaRepositorio.findById(dto.getContaDestinoId())
                .orElseThrow(() -> new RuntimeException("Conta destino não encontrada"));
            
            if (origem.getSaldo() < dto.getValor()) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Saldo insuficiente"));
            }
            
            // Atualiza saldos
            origem.setSaldo(origem.getSaldo() - dto.getValor());
            destino.setSaldo(destino.getSaldo() + dto.getValor());
            
            contaRepositorio.save(origem);
            contaRepositorio.save(destino);
            
            // Cria transação
            Transacao transacao = new Transacao();
            transacao.setContaOrigem(origem);
            transacao.setContaDestino(destino);
            transacao.setValor(dto.getValor());
            transacao.setData(LocalDateTime.now());
            
            Transacao salva = transacaoServico.criarTransacao(transacao);
            
            return ResponseEntity.ok(Map.of(
                "mensagem", "Transferência realizada com sucesso",
                "transacaoId", salva.getId(),
                "novoSaldoOrigem", origem.getSaldo(),
                "novoSaldoDestino", destino.getSaldo()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Transacao> criarTransacao(@RequestBody Transacao transacao) {
        Transacao novaTransacao = transacaoServico.criarTransacao(transacao);
        return ResponseEntity.ok(novaTransacao);
    }

    @GetMapping
    public ResponseEntity<List<Transacao>> listarTransacoes() {
        List<Transacao> transacoes = transacaoServico.listarTransacoes();
        return ResponseEntity.ok(transacoes);
    }
}