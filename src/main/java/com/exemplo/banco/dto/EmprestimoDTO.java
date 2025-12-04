package com.exemplo.banco.dto;

public class EmprestimoDTO {
    private Long id;
    private Long contaId;
    private String numeroConta;
    private String clienteNome;
    private Double valorTotal;
    private Double valorParcela;
    private Integer numeroParcelas;
    private Integer parcelasPagas;
    private Double taxaJuros;
    private String dataContratacao;
    private String dataQuitacao;
    private String status;

    public EmprestimoDTO() {
    }

    public EmprestimoDTO(Long id, Long contaId, String numeroConta, String clienteNome,
                        Double valorTotal, Double valorParcela, Integer numeroParcelas,
                        Integer parcelasPagas, Double taxaJuros, String dataContratacao,
                        String dataQuitacao, String status) {
        this.id = id;
        this.contaId = contaId;
        this.numeroConta = numeroConta;
        this.clienteNome = clienteNome;
        this.valorTotal = valorTotal;
        this.valorParcela = valorParcela;
        this.numeroParcelas = numeroParcelas;
        this.parcelasPagas = parcelasPagas;
        this.taxaJuros = taxaJuros;
        this.dataContratacao = dataContratacao;
        this.dataQuitacao = dataQuitacao;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getContaId() { return contaId; }
    public void setContaId(Long contaId) { this.contaId = contaId; }

    public String getNumeroConta() { return numeroConta; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }

    public String getClienteNome() { return clienteNome; }
    public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }

    public Double getValorParcela() { return valorParcela; }
    public void setValorParcela(Double valorParcela) { this.valorParcela = valorParcela; }

    public Integer getNumeroParcelas() { return numeroParcelas; }
    public void setNumeroParcelas(Integer numeroParcelas) { this.numeroParcelas = numeroParcelas; }

    public Integer getParcelasPagas() { return parcelasPagas; }
    public void setParcelasPagas(Integer parcelasPagas) { this.parcelasPagas = parcelasPagas; }

    public Double getTaxaJuros() { return taxaJuros; }
    public void setTaxaJuros(Double taxaJuros) { this.taxaJuros = taxaJuros; }

    public String getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(String dataContratacao) { this.dataContratacao = dataContratacao; }

    public String getDataQuitacao() { return dataQuitacao; }
    public void setDataQuitacao(String dataQuitacao) { this.dataQuitacao = dataQuitacao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}