package com.esther.controledefinancas.backend.dto;

import lombok.Data;

@Data
public class ContaDTO {

    private Long id;
    private String nome;
    private Double saldo;
    private double limite;
    private Double valeAlimentacao;

    public ContaDTO() {
    }

    public ContaDTO(Long id, String nome, Double saldo, double limite, Double valeAlimentacao) {
        this.id = id;
        this.nome = nome;
        this.saldo = saldo;
        this.limite = limite;
        this.valeAlimentacao = valeAlimentacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getValeAlimentacao() {
        return valeAlimentacao;
    }

    public void setValeAlimentacao(Double valeAlimentacao) {
        this.valeAlimentacao = valeAlimentacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }
}
