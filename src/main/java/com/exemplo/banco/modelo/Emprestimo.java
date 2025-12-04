package com.exemplo.banco.modelo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @Column(nullable = false)
    private Double valorTotal;

    @Column(nullable = false)
    private Double valorParcela;

    @Column(nullable = false)
    private Integer numeroParcelas;

    @Column(nullable = false)
    private Integer parcelasPagas;

    @Column(nullable = false)
    private Double taxaJuros; // percentual ao mês

    @Column(nullable = false)
    private LocalDateTime dataContratacao;

    @Column
    private LocalDateTime dataQuitacao;

    @Column(nullable = false)
    private String status; // ATIVO, QUITADO

    public Emprestimo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(Double valorParcela) {
        this.valorParcela = valorParcela;
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public Integer getParcelasPagas() {
        return parcelasPagas;
    }

    public void setParcelasPagas(Integer parcelasPagas) {
        this.parcelasPagas = parcelasPagas;
    }

    public Double getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(Double taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public LocalDateTime getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDateTime dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public LocalDateTime getDataQuitacao() {
        return dataQuitacao;
    }

    public void setDataQuitacao(LocalDateTime dataQuitacao) {
        this.dataQuitacao = dataQuitacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}