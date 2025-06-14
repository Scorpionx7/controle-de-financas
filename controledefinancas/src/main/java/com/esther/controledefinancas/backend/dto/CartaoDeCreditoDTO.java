package com.esther.controledefinancas.backend.dto;

import com.esther.controledefinancas.backend.model.Conta;
import lombok.Data;

@Data
public class CartaoDeCreditoDTO {

    private Long id;
    private String nome;
    private Double limite;
    private Double gastoAtual;
    private ContaDTO conta;


    public CartaoDeCreditoDTO(Long id, String nome, double limite, double gastoAtual, Conta conta) {

    }

    public CartaoDeCreditoDTO(Long id, String nome, Double limite, Double gastoAtual, ContaDTO conta) {
        this.id = id;
        this.nome = nome;
        this.limite = limite;
        this.gastoAtual = gastoAtual;
        this.conta = conta;
    }
}
