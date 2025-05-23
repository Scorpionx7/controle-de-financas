package com.esther.controledefinancas.backend.model;


import com.esther.controledefinancas.backend.model.enuns.FormaPagamento;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private double valor;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;
    private boolean finalizada;
    private LocalDate data;

    private int parcelas; // 0 para à vista, >0 para parcelado
    private int parcelasRestantes;


    @ManyToOne
    private Conta conta;

    @ManyToOne
    private CartaoDeCredito cartao;

    public double calcularValorParcela() {
        return valor / parcelas;
    }

    public Compra() {
    }

    public Compra(Long id, String descricao, double valor, FormaPagamento formaPagamento, boolean finalizada, LocalDate data, int parcelas, int parcelasRestantes, Conta conta, CartaoDeCredito cartao) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.finalizada = finalizada;
        this.data = data;
        this.parcelas = parcelas;
        this.parcelasRestantes = parcelasRestantes;
        this.conta = conta;
        this.cartao = cartao;
    }
}
