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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public CartaoDeCredito getCartao() {
        return cartao;
    }

    public void setCartao(CartaoDeCredito cartao) {
        this.cartao = cartao;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getParcelasRestantes() {
        return parcelasRestantes;
    }

    public void setParcelasRestantes(int parcelasRestantes) {
        this.parcelasRestantes = parcelasRestantes;
    }

}
