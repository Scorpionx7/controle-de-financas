package com.esther.controledefinancas.backend.dto;

import lombok.Data;

@Data
public class ContaDTO {

    private Long id;
    private Double saldo;
    private Double valeAlimentacao;

    public ContaDTO() {
    }

    public ContaDTO(Long id, Double saldo, Double valeAlimentacao) {
        this.id = id;
        this.saldo = saldo;
        this.valeAlimentacao = valeAlimentacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getValeAlimentacao() {
        return valeAlimentacao;
    }

    public void setValeAlimentacao(Double valeAlimentacao) {
        this.valeAlimentacao = valeAlimentacao;
    }
}
