package com.esther.controledefinancas.backend.dto;

import lombok.Data;

@Data
public class CartaoDeCreditoDTO {

    private Long id;
    private String nome;
    private Double limite;
    private Double gastoAtual;

    public CartaoDeCreditoDTO() {
    }

    public CartaoDeCreditoDTO(Long id, String nome, Double limite, Double gastoAtual) {
        this.id = id;
        this.nome = nome;
        this.limite = limite;
        this.gastoAtual = gastoAtual;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public Double getGastoAtual() {
        return gastoAtual;
    }

    public void setGastoAtual(Double gastoAtual) {
        this.gastoAtual = gastoAtual;
    }
}
