package com.esther.controledefinancas.backend.dto;

import lombok.Data;

@Data
public class RelatorioGastoDTO {

    private String descricao;
    private double valor;
    private String formaPagamento;
    private String status;
    private double valorParcelaAtual; // Novo campo
    private int parcelasRestantes;

    public RelatorioGastoDTO() {
    }

    public RelatorioGastoDTO(String descricao, double valor, String formaPagamento, String status, double valorParcelaAtual, int parcelasRestantes) {
        this.descricao = descricao;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.status = status;
        this.valorParcelaAtual = valorParcelaAtual;
        this.parcelasRestantes = parcelasRestantes;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getValorParcelaAtual() {
        return valorParcelaAtual;
    }

    public void setValorParcelaAtual(double valorParcelaAtual) {
        this.valorParcelaAtual = valorParcelaAtual;
    }

    public int getParcelasRestantes() {
        return parcelasRestantes;
    }

    public void setParcelasRestantes(int parcelasRestantes) {
        this.parcelasRestantes = parcelasRestantes;
    }
}
