package com.exemplo.banco.modelo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Investimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @Column(nullable = false)
    private String tipo; // CDB, LCI, LCA, POUPANCA, etc

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private Double rentabilidade; // percentual ao ano

    @Column(nullable = false)
    private LocalDateTime dataInicio;

    @Column
    private LocalDateTime dataResgate;

    @Column(nullable = false)
    private String status; // ATIVO, RESGATADO

    public Investimento() {
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getRentabilidade() {
        return rentabilidade;
    }

    public void setRentabilidade(Double rentabilidade) {
        this.rentabilidade = rentabilidade;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataResgate() {
        return dataResgate;
    }

    public void setDataResgate(LocalDateTime dataResgate) {
        this.dataResgate = dataResgate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
