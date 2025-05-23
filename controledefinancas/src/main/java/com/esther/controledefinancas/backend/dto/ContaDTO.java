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
}
