package com.exemplo.banco.controlador;

import com.exemplo.banco.dto.EmprestimoDTO;
import com.exemplo.banco.modelo.Emprestimo;
import com.exemplo.banco.modelo.Conta;
import com.exemplo.banco.repositorio.EmprestimoRepositorio;
import com.exemplo.banco.repositorio.ContaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoControlador {

    @Autowired
    private EmprestimoRepositorio emprestimoRepo;

    @Autowired
    private ContaRepositorio contaRepo;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @GetMapping
    public List<EmprestimoDTO> listar() {
        return emprestimoRepo.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> contratar(@RequestBody Map<String, Object> payload) {
        try {
            Long contaId = Long.valueOf(payload.get("contaId").toString());
            Conta conta = contaRepo.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

            Double valorSolicitado = Double.parseDouble(payload.get("valorSolicitado").toString());
            Integer numeroParcelas = Integer.parseInt(payload.get("numeroParcelas").toString());
            Double taxaJuros = Double.parseDouble(payload.get("taxaJuros").toString());

            // Calcula valor total com juros compostos
            Double valorTotal = valorSolicitado * Math.pow(1 + taxaJuros / 100, numeroParcelas);
            Double valorParcela = valorTotal / numeroParcelas;

            // Credita o valor na conta
            conta.setSaldo(conta.getSaldo() + valorSolicitado);
            contaRepo.save(conta);

            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setConta(conta);
            emprestimo.setValorTotal(valorTotal);
            emprestimo.setValorParcela(valorParcela);
            emprestimo.setNumeroParcelas(numeroParcelas);
            emprestimo.setParcelasPagas(0);
            emprestimo.setTaxaJuros(taxaJuros);
            emprestimo.setDataContratacao(LocalDateTime.now());
            emprestimo.setStatus("ATIVO");

            Emprestimo salvo = emprestimoRepo.save(emprestimo);

            return ResponseEntity.ok(Map.of(
                "mensagem", "Empréstimo contratado com sucesso",
                "valorSolicitado", valorSolicitado,
                "valorTotal", valorTotal,
                "valorParcela", valorParcela,
                "emprestimo", toDTO(salvo)
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/{id}/pagar-parcela")
    public ResponseEntity<?> pagarParcela(@PathVariable Long id) {
        try {
            Emprestimo emprestimo = emprestimoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

            if (!"ATIVO".equals(emprestimo.getStatus())) {
                return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Empréstimo já foi quitado"));
            }

            Conta conta = emprestimo.getConta();
            
            if (conta.getSaldo() < emprestimo.getValorParcela()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Saldo insuficiente para pagar a parcela"));
            }

            // Deduz o valor da parcela
            conta.setSaldo(conta.getSaldo() - emprestimo.getValorParcela());
            contaRepo.save(conta);

            // Atualiza empréstimo
            emprestimo.setParcelasPagas(emprestimo.getParcelasPagas() + 1);

            // Verifica se quitou
            if (emprestimo.getParcelasPagas().equals(emprestimo.getNumeroParcelas())) {
                emprestimo.setStatus("QUITADO");
                emprestimo.setDataQuitacao(LocalDateTime.now());
            }

            emprestimoRepo.save(emprestimo);

            Integer parcelasRestantes = emprestimo.getNumeroParcelas() - emprestimo.getParcelasPagas();

            return ResponseEntity.ok(Map.of(
                "mensagem", "Parcela paga com sucesso",
                "parcelasPagas", emprestimo.getParcelasPagas(),
                "parcelasRestantes", parcelasRestantes,
                "valorPago", emprestimo.getValorParcela(),
                "novoSaldo", conta.getSaldo(),
                "quitado", "QUITADO".equals(emprestimo.getStatus())
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            Emprestimo emprestimo = emprestimoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

            if ("ATIVO".equals(emprestimo.getStatus())) {
                return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Não é possível deletar empréstimo ativo. Quite primeiro."));
            }

            emprestimoRepo.deleteById(id);
            return ResponseEntity.ok(Map.of("mensagem", "Empréstimo deletado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }

    private EmprestimoDTO toDTO(Emprestimo e) {
        return new EmprestimoDTO(
            e.getId(),
            e.getConta().getId(),
            e.getConta().getNumeroConta(),
            e.getConta().getCliente().getNome(),
            e.getValorTotal(),
            e.getValorParcela(),
            e.getNumeroParcelas(),
            e.getParcelasPagas(),
            e.getTaxaJuros(),
            e.getDataContratacao().format(formatter),
            e.getDataQuitacao() != null ? e.getDataQuitacao().format(formatter) : null,
            e.getStatus()
        );
    }
}