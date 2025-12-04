package com.exemplo.banco.dto;

public class ContaDTO {

    private Long id;
    private String numeroConta;
    private Double saldo;
    private String tipoConta;
    private Long clienteId;
    private String clienteNome;

    public ContaDTO() {
    }

    public ContaDTO(Long id, String numeroConta, Double saldo, String tipoConta, Long clienteId, String clienteNome) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.tipoConta = tipoConta;
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }
}