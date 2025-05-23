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
}
