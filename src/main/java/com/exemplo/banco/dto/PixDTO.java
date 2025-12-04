package com.exemplo.banco.dto;

public class PixDTO {
    private Long id;
    private Long contaOrigemId;
    private Long contaDestinoId;
    private String numeroContaOrigem;
    private String numeroContaDestino;
    private String clienteOrigemNome;
    private String clienteDestinoNome;
    private String chaveDestino;
    private Double valor;
    private String codigoPix;
    private String qrCodeBase64;
    private String dataGeracao;
    private String dataPagamento;
    private String status;

    public PixDTO() {
    }

    public PixDTO(Long id, Long contaOrigemId, Long contaDestinoId, 
                  String numeroContaOrigem, String numeroContaDestino,
                  String clienteOrigemNome, String clienteDestinoNome,
                  String chaveDestino, Double valor, String codigoPix,
                  String qrCodeBase64, String dataGeracao, String dataPagamento,
                  String status) {
        this.id = id;
        this.contaOrigemId = contaOrigemId;
        this.contaDestinoId = contaDestinoId;
        this.numeroContaOrigem = numeroContaOrigem;
        this.numeroContaDestino = numeroContaDestino;
        this.clienteOrigemNome = clienteOrigemNome;
        this.clienteDestinoNome = clienteDestinoNome;
        this.chaveDestino = chaveDestino;
        this.valor = valor;
        this.codigoPix = codigoPix;
        this.qrCodeBase64 = qrCodeBase64;
        this.dataGeracao = dataGeracao;
        this.dataPagamento = dataPagamento;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getContaOrigemId() { return contaOrigemId; }
    public void setContaOrigemId(Long contaOrigemId) { this.contaOrigemId = contaOrigemId; }

    public Long getContaDestinoId() { return contaDestinoId; }
    public void setContaDestinoId(Long contaDestinoId) { this.contaDestinoId = contaDestinoId; }

    public String getNumeroContaOrigem() { return numeroContaOrigem; }
    public void setNumeroContaOrigem(String numeroContaOrigem) { this.numeroContaOrigem = numeroContaOrigem; }

    public String getNumeroContaDestino() { return numeroContaDestino; }
    public void setNumeroContaDestino(String numeroContaDestino) { this.numeroContaDestino = numeroContaDestino; }

    public String getClienteOrigemNome() { return clienteOrigemNome; }
    public void setClienteOrigemNome(String clienteOrigemNome) { this.clienteOrigemNome = clienteOrigemNome; }

    public String getClienteDestinoNome() { return clienteDestinoNome; }
    public void setClienteDestinoNome(String clienteDestinoNome) { this.clienteDestinoNome = clienteDestinoNome; }

    public String getChaveDestino() { return chaveDestino; }
    public void setChaveDestino(String chaveDestino) { this.chaveDestino = chaveDestino; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public String getCodigoPix() { return codigoPix; }
    public void setCodigoPix(String codigoPix) { this.codigoPix = codigoPix; }

    public String getQrCodeBase64() { return qrCodeBase64; }
    public void setQrCodeBase64(String qrCodeBase64) { this.qrCodeBase64 = qrCodeBase64; }

    public String getDataGeracao() { return dataGeracao; }
    public void setDataGeracao(String dataGeracao) { this.dataGeracao = dataGeracao; }

    public String getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(String dataPagamento) { this.dataPagamento = dataPagamento; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}