package com.esther.controledefinancas.backend.dto;

import lombok.Data;

@Data
public class CartaoDeCreditoDTO {

    private Long id;
    private String nome;
    private Double limite;
    private Double gastoAtual;
    private ContaDTO conta;

    public CartaoDeCreditoDTO() {
    }

    public CartaoDeCreditoDTO(Long id, String nome, Double limite, Double gastoAtual, ContaDTO conta) {
        this.id = id;
        this.nome = nome;
        this.limite = limite;
        this.gastoAtual = gastoAtual;
        this.conta = conta;
    }
}
