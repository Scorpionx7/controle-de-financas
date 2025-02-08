package com.esther.controledefinancas.backend.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private double saldo;
    private double limite;
    private double valeAlimentacao;

    @OneToMany(mappedBy = "conta",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartaoDeCredito> cartoes = new ArrayList<>();

    public Conta() {
    }

    public Conta(Long id) {
        this.id = id;
    }

    public Conta(Long id, String nome, double saldo, double limite, double valeAlimentacao) {
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

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getValeAlimentacao() {
        return valeAlimentacao;
    }

    public void setValeAlimentacao(double valeAlimentacao) {
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

    public List<CartaoDeCredito> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<CartaoDeCredito> cartoes) {
        this.cartoes = cartoes;
    }

    @Override
    public String toString() {
        return nome;
    }
}
