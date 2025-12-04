package com.exemplo.banco.controlador;

import com.exemplo.banco.dto.InvestimentoDTO;
import com.exemplo.banco.modelo.Investimento;
import com.exemplo.banco.modelo.Conta;
import com.exemplo.banco.repositorio.InvestimentoRepositorio;
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
@RequestMapping("/api/investimentos")
public class InvestimentoControlador {

    @Autowired
    private InvestimentoRepositorio investimentoRepo;

    @Autowired
    private ContaRepositorio contaRepo;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @GetMapping
    public List<InvestimentoDTO> listar() {
        return investimentoRepo.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Map<String, Object> payload) {
        try {
            Long contaId = Long.valueOf(payload.get("contaId").toString());
            Conta conta = contaRepo.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

            Double valor = Double.parseDouble(payload.get("valor").toString());
            
            if (conta.getSaldo() < valor) {
                return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Saldo insuficiente para investir"));
            }

            // Deduz o valor da conta
            conta.setSaldo(conta.getSaldo() - valor);
            contaRepo.save(conta);

            Investimento investimento = new Investimento();
            investimento.setConta(conta);
            investimento.setTipo(payload.get("tipo").toString());
            investimento.setValor(valor);
            investimento.setRentabilidade(Double.parseDouble(payload.get("rentabilidade").toString()));
            investimento.setDataInicio(LocalDateTime.now());
            investimento.setStatus("ATIVO");

            Investimento salvo = investimentoRepo.save(investimento);

            return ResponseEntity.ok(toDTO(salvo));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/{id}/resgatar")
    public ResponseEntity<?> resgatar(@PathVariable Long id) {
        try {
            Investimento investimento = investimentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Investimento não encontrado"));

            if (!"ATIVO".equals(investimento.getStatus())) {
                return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Investimento já foi resgatado"));
            }

            // Calcula rendimento (simplificado)
            Double valorFinal = investimento.getValor() * (1 + investimento.getRentabilidade() / 100);

            // Devolve para a conta
            Conta conta = investimento.getConta();
            conta.setSaldo(conta.getSaldo() + valorFinal);
            contaRepo.save(conta);

            // Atualiza investimento
            investimento.setStatus("RESGATADO");
            investimento.setDataResgate(LocalDateTime.now());
            investimentoRepo.save(investimento);

            return ResponseEntity.ok(Map.of(
                "mensagem", "Investimento resgatado com sucesso",
                "valorInvestido", investimento.getValor(),
                "valorResgatado", valorFinal,
                "rendimento", valorFinal - investimento.getValor()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            Investimento investimento = investimentoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Investimento não encontrado"));

            if ("ATIVO".equals(investimento.getStatus())) {
                return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Não é possível deletar investimento ativo. Resgate primeiro."));
            }

            investimentoRepo.deleteById(id);
            return ResponseEntity.ok(Map.of("mensagem", "Investimento deletado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }

    private InvestimentoDTO toDTO(Investimento i) {
        return new InvestimentoDTO(
            i.getId(),
            i.getConta().getId(),
            i.getConta().getNumeroConta(),
            i.getConta().getCliente().getNome(),
            i.getTipo(),
            i.getValor(),
            i.getRentabilidade(),
            i.getDataInicio().format(formatter),
            i.getDataResgate() != null ? i.getDataResgate().format(formatter) : null,
            i.getStatus()
        );
    }
}