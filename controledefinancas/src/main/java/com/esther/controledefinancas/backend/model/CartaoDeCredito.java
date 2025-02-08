package com.esther.controledefinancas.backend.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CartaoDeCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private double limite; // Limite total do cartão
    private double gastoAtual; // Gasto atual do cartão

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    public double getLimiteDisponivel(){
        return limite - gastoAtual;
    }

    public boolean registrarCompra(double valor){
        if(valor <= getLimiteDisponivel()){
            gastoAtual += valor;
            return true;
        }
        return false;
    }

    public boolean quitarParcela(double valorParcela) {
        if (valorParcela <= this.gastoAtual) {
            this.gastoAtual -= valorParcela;
            return true;
        }
        return false;
    }

    public CartaoDeCredito() {
    }

    public CartaoDeCredito(Long id) {
        this.id = id;
    }

    public CartaoDeCredito(Long id, String nome, double limite, double gastoAtual) {
        this.id = id;
        this.nome = nome;
        this.limite = limite;
        this.gastoAtual = gastoAtual;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getGastoAtual() {
        return gastoAtual;
    }

    public void setGastoAtual(double gastoAtual) {
        this.gastoAtual = gastoAtual;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    @Override
    public String toString() {
        return nome;
    }
}
