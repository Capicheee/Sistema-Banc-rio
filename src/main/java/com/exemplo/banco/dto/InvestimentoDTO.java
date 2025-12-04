package com.exemplo.banco.dto;

public class InvestimentoDTO {
    private Long id;
    private Long contaId;
    private String numeroConta;
    private String clienteNome;
    private String tipo;
    private Double valor;
    private Double rentabilidade;
    private String dataInicio;
    private String dataResgate;
    private String status;

    public InvestimentoDTO() {
    }

    public InvestimentoDTO(Long id, Long contaId, String numeroConta, String clienteNome, 
                          String tipo, Double valor, Double rentabilidade, 
                          String dataInicio, String dataResgate, String status) {
        this.id = id;
        this.contaId = contaId;
        this.numeroConta = numeroConta;
        this.clienteNome = clienteNome;
        this.tipo = tipo;
        this.valor = valor;
        this.rentabilidade = rentabilidade;
        this.dataInicio = dataInicio;
        this.dataResgate = dataResgate;
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
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    
    public Double getRentabilidade() { return rentabilidade; }
    public void setRentabilidade(Double rentabilidade) { this.rentabilidade = rentabilidade; }
    
    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }
    
    public String getDataResgate() { return dataResgate; }
    public void setDataResgate(String dataResgate) { this.dataResgate = dataResgate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
