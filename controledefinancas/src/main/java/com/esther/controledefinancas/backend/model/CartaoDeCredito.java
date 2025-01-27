package com.esther.controledefinancas.backend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Override
    public String toString() {
        return "CartaoDeCredito{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
