package com.exemplo.banco.modelo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Pix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conta_origem_id")
    private Conta contaOrigem;

    @ManyToOne
    @JoinColumn(name = "conta_destino_id")
    private Conta contaDestino;

    @Column(nullable = false)
    private String chaveDestino; // CPF, email, telefone, ou chave aleatória

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false, unique = true)
    private String codigoPix; // Código único do PIX

    @Column(length = 5000)
    private String qrCodeBase64; // QR Code em Base64

    @Column(nullable = false)
    private LocalDateTime dataGeracao;

    @Column
    private LocalDateTime dataPagamento;

    @Column(nullable = false)
    private String status; // PENDENTE, PAGO, EXPIRADO, CANCELADO

    public Pix() {
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Conta getContaOrigem() { return contaOrigem; }
    public void setContaOrigem(Conta contaOrigem) { this.contaOrigem = contaOrigem; }

    public Conta getContaDestino() { return contaDestino; }
    public void setContaDestino(Conta contaDestino) { this.contaDestino = contaDestino; }

    public String getChaveDestino() { return chaveDestino; }
    public void setChaveDestino(String chaveDestino) { this.chaveDestino = chaveDestino; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public String getCodigoPix() { return codigoPix; }
    public void setCodigoPix(String codigoPix) { this.codigoPix = codigoPix; }

    public String getQrCodeBase64() { return qrCodeBase64; }
    public void setQrCodeBase64(String qrCodeBase64) { this.qrCodeBase64 = qrCodeBase64; }

    public LocalDateTime getDataGeracao() { return dataGeracao; }
    public void setDataGeracao(LocalDateTime dataGeracao) { this.dataGeracao = dataGeracao; }

    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}