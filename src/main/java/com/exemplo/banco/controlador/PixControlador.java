package com.exemplo.banco.controlador;

import com.exemplo.banco.dto.PixDTO;
import com.exemplo.banco.modelo.Pix;
import com.exemplo.banco.modelo.Conta;
import com.exemplo.banco.repositorio.PixRepositorio;
import com.exemplo.banco.repositorio.ContaRepositorio;
import com.exemplo.banco.servico.QRCodeServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pix")
public class PixControlador {

    @Autowired
    private PixRepositorio pixRepo;

    @Autowired
    private ContaRepositorio contaRepo;

    @Autowired
    private QRCodeServico qrCodeServico;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @GetMapping
    public List<PixDTO> listar() {
        return pixRepo.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    @PostMapping("/gerar")
    public ResponseEntity<?> gerarPix(@RequestBody Map<String, Object> payload) {
        try {
            Long contaDestinoId = Long.valueOf(payload.get("contaDestinoId").toString());
            Conta contaDestino = contaRepo.findById(contaDestinoId)
                .orElseThrow(() -> new RuntimeException("Conta destino não encontrada"));

            String chaveDestino = payload.get("chaveDestino").toString();
            Double valor = Double.parseDouble(payload.get("valor").toString());

            // Gera código único do PIX
            String codigoPix = UUID.randomUUID().toString().substring(0, 13).toUpperCase();

            // Monta o payload PIX (formato simplificado)
            String pixPayload = String.format(
                "00020126360014br.gov.bcb.pix0114%s520400005303986540%s5802BR5925%s6014BR6304",
                chaveDestino,
                String.format("%.2f", valor),
                contaDestino.getCliente().getNome()
            ) + codigoPix;

            // Gera QR Code
            String qrCodeBase64 = qrCodeServico.gerarQRCodeBase64(pixPayload);

            Pix pix = new Pix();
            pix.setContaDestino(contaDestino);
            pix.setChaveDestino(chaveDestino);
            pix.setValor(valor);
            pix.setCodigoPix(codigoPix);
            pix.setQrCodeBase64(qrCodeBase64);
            pix.setDataGeracao(LocalDateTime.now());
            pix.setStatus("PENDENTE");

            Pix salvo = pixRepo.save(pix);

            return ResponseEntity.ok(Map.of(
                "mensagem", "PIX gerado com sucesso",
                "pix", toDTO(salvo)
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<?> pagarPix(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            Pix pix = pixRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("PIX não encontrado"));

            if (!"PENDENTE".equals(pix.getStatus())) {
                return ResponseEntity.badRequest()
                    .body(Map.of("erro", "PIX já foi pago ou cancelado"));
            }

            Long contaOrigemId = Long.valueOf(payload.get("contaOrigemId").toString());
            Conta contaOrigem = contaRepo.findById(contaOrigemId)
                .orElseThrow(() -> new RuntimeException("Conta origem não encontrada"));

            if (contaOrigem.getSaldo() < pix.getValor()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Saldo insuficiente"));
            }

            // Realiza a transferência
            contaOrigem.setSaldo(contaOrigem.getSaldo() - pix.getValor());
            pix.getContaDestino().setSaldo(pix.getContaDestino().getSaldo() + pix.getValor());

            contaRepo.save(contaOrigem);
            contaRepo.save(pix.getContaDestino());

            // Atualiza PIX
            pix.setContaOrigem(contaOrigem);
            pix.setStatus("PAGO");
            pix.setDataPagamento(LocalDateTime.now());
            pixRepo.save(pix);

            return ResponseEntity.ok(Map.of(
                "mensagem", "PIX pago com sucesso",
                "valorPago", pix.getValor(),
                "novoSaldoOrigem", contaOrigem.getSaldo(),
                "novoSaldoDestino", pix.getContaDestino().getSaldo()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/pagar-por-codigo")
    public ResponseEntity<?> pagarPixPorCodigo(@RequestBody Map<String, Object> payload) {
        try {
            String codigoPix = payload.get("codigoPix").toString();
            Pix pix = pixRepo.findByCodigoPix(codigoPix)
                .orElseThrow(() -> new RuntimeException("PIX não encontrado"));

            if (!"PENDENTE".equals(pix.getStatus())) {
                return ResponseEntity.badRequest()
                    .body(Map.of("erro", "PIX já foi pago ou cancelado"));
            }

            Long contaOrigemId = Long.valueOf(payload.get("contaOrigemId").toString());
            Conta contaOrigem = contaRepo.findById(contaOrigemId)
                .orElseThrow(() -> new RuntimeException("Conta origem não encontrada"));

            if (contaOrigem.getSaldo() < pix.getValor()) {
                return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Saldo insuficiente"));
            }

            // Realiza a transferência
            contaOrigem.setSaldo(contaOrigem.getSaldo() - pix.getValor());
            pix.getContaDestino().setSaldo(pix.getContaDestino().getSaldo() + pix.getValor());

            contaRepo.save(contaOrigem);
            contaRepo.save(pix.getContaDestino());

            // Atualiza PIX
            pix.setContaOrigem(contaOrigem);
            pix.setStatus("PAGO");
            pix.setDataPagamento(LocalDateTime.now());
            pixRepo.save(pix);

            return ResponseEntity.ok(Map.of(
                "mensagem", "PIX pago com sucesso",
                "valorPago", pix.getValor(),
                "destinatario", pix.getContaDestino().getCliente().getNome(),
                "novoSaldo", contaOrigem.getSaldo()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {
        try {
            Pix pix = pixRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("PIX não encontrado"));

            if ("PAGO".equals(pix.getStatus())) {
                return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Não é possível cancelar PIX já pago"));
            }

            pix.setStatus("CANCELADO");
            pixRepo.save(pix);

            return ResponseEntity.ok(Map.of("mensagem", "PIX cancelado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", e.getMessage()));
        }
    }

    private PixDTO toDTO(Pix p) {
        return new PixDTO(
            p.getId(),
            p.getContaOrigem() != null ? p.getContaOrigem().getId() : null,
            p.getContaDestino() != null ? p.getContaDestino().getId() : null,
            p.getContaOrigem() != null ? p.getContaOrigem().getNumeroConta() : null,
            p.getContaDestino() != null ? p.getContaDestino().getNumeroConta() : null,
            p.getContaOrigem() != null ? p.getContaOrigem().getCliente().getNome() : null,
            p.getContaDestino() != null ? p.getContaDestino().getCliente().getNome() : null,
            p.getChaveDestino(),
            p.getValor(),
            p.getCodigoPix(),
            p.getQrCodeBase64(),
            p.getDataGeracao().format(formatter),
            p.getDataPagamento() != null ? p.getDataPagamento().format(formatter) : null,
            p.getStatus()
        );
    }
}