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

    @Override
    public String toString() {
        return nome;
    }
}
