package com.exemplo.banco.dto;

public class TransferenciaDTO {
    private Long contaOrigemId;
    private Long contaDestinoId;
    private Double valor;

    public TransferenciaDTO() {
    }

    public TransferenciaDTO(Long contaOrigemId, Long contaDestinoId, Double valor) {
        this.contaOrigemId = contaOrigemId;
        this.contaDestinoId = contaDestinoId;
        this.valor = valor;
    }

    public Long getContaOrigemId() {
        return contaOrigemId;
    }

    public void setContaOrigemId(Long contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    public Long getContaDestinoId() {
        return contaDestinoId;
    }

    public void setContaDestinoId(Long contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}