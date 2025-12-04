package com.exemplo.banco.controlador;

import com.exemplo.banco.dto.ContaDTO;
import com.exemplo.banco.modelo.Conta;
import com.exemplo.banco.modelo.Cliente;
import com.exemplo.banco.modelo.TipoConta;
import com.exemplo.banco.repositorio.ContaRepositorio;
import com.exemplo.banco.repositorio.ClienteRepositorio;
import com.exemplo.banco.repositorio.InvestimentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contas")
public class ContaControlador {
    
    private final ContaRepositorio contaRepo;
    private final ClienteRepositorio clienteRepo;
    
    @Autowired
    private InvestimentoRepositorio investimentoRepo;

    public ContaControlador(ContaRepositorio contaRepo, ClienteRepositorio clienteRepo) {
        this.contaRepo = contaRepo;
        this.clienteRepo = clienteRepo;
    }

    @GetMapping
    public List<ContaDTO> listar() {
        return contaRepo.findAll().stream()
            .map(c -> new ContaDTO(
                c.getId(),
                c.getNumeroConta(),
                c.getSaldo(),
                c.getTipoConta() != null ? c.getTipoConta().name() : null,
                c.getCliente() != null ? c.getCliente().getId() : null,
                c.getCliente() != null ? c.getCliente().getNome() : null
            ))
            .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Map<String, Object> payload) {
        try {
            Conta conta = new Conta();
            conta.setNumeroConta(payload.get("numeroConta").toString());
            
            Object saldoObj = payload.get("saldo");
            conta.setSaldo(saldoObj != null ? Double.parseDouble(saldoObj.toString()) : 0.0);
            
            conta.setTipoConta(TipoConta.valueOf(payload.get("tipoConta").toString()));
            
            Long clienteId = Long.valueOf(payload.get("clienteId").toString());
            Cliente cliente = clienteRepo.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            
            conta.setCliente(cliente);
            Conta salva = contaRepo.save(conta);
            
            ContaDTO dto = new ContaDTO(
                salva.getId(),
                salva.getNumeroConta(),
                salva.getSaldo(),
                salva.getTipoConta().name(),
                salva.getCliente().getId(),
                salva.getCliente().getNome()
            );
            
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            Conta conta = contaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
            
            // Verifica se a conta tem saldo
            if (conta.getSaldo() != null && conta.getSaldo() > 0) {
                return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Não é possível deletar conta com saldo positivo"));
            }
            
            // Verifica se tem investimentos ativos
            long investimentosAtivos = investimentoRepo.findByContaId(id).stream()
                .filter(inv -> "ATIVO".equals(inv.getStatus()))
                .count();
            
            if (investimentosAtivos > 0) {
                return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Não é possível deletar conta com investimentos ativos"));
            }
            
            contaRepo.deleteById(id);
            return ResponseEntity.ok(Map.of("mensagem", "Conta deletada com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }
}