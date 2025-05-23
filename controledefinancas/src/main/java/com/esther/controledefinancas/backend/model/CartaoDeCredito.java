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

    @Override
    public String toString() {
        return nome;
    }
}
